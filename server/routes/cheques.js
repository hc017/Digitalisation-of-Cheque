const express = require('express');
const { Op } = require('sequelize');
const { User, Cheque, Transaction } = require('../models');
const { auth } = require('../middleware/auth');
const CryptoUtils = require('../utils/crypto');
const sequelize = require('../config/database');

const router = express.Router();

// Create cheque
router.post('/create', auth, async (req, res) => {
  const transaction = await sequelize.transaction();
  
  try {
    const { receiverAccountNumber, amount, message, chequeDate } = req.body;

    // Find receiver
    const receiver = await User.findOne({
      where: { accountNumber: receiverAccountNumber }
    });

    if (!receiver) {
      await transaction.rollback();
      return res.status(404).json({ message: 'Receiver not found' });
    }

    if (receiver.id === req.user.id) {
      await transaction.rollback();
      return res.status(400).json({ message: 'Cannot send cheque to yourself' });
    }

    // Check sender balance
    const sender = await User.findByPk(req.user.id);
    if (parseFloat(sender.balance) < parseFloat(amount)) {
      await transaction.rollback();
      return res.status(400).json({ message: 'Insufficient balance' });
    }

    // Generate cheque data
    const chequeNumber = CryptoUtils.generateChequeNumber();
    const expiryDate = new Date(chequeDate);
    expiryDate.setDate(expiryDate.getDate() + 30); // 30 days validity

    // Create signature data
    const signatureData = `${chequeNumber}|${req.user.id}|${receiver.id}|${amount}|${chequeDate}`;
    const senderSignature = CryptoUtils.signData(signatureData, sender.privateKey);
    const signatureHash = CryptoUtils.generateHash(signatureData + senderSignature);

    // Create cheque
    const cheque = await Cheque.create({
      chequeNumber,
      senderId: req.user.id,
      receiverId: receiver.id,
      amount,
      message,
      chequeDate: new Date(chequeDate),
      expiryDate,
      senderSignature,
      signatureHash
    }, { transaction });

    await transaction.commit();

    // Send real-time notification
    req.io.to(`user_${receiver.id}`).emit('newCheque', {
      chequeId: cheque.id,
      senderName: sender.fullName,
      amount: amount,
      message: 'You have received a new cheque'
    });

    res.status(201).json({
      message: 'Cheque created successfully',
      cheque: {
        id: cheque.id,
        chequeNumber: cheque.chequeNumber,
        amount: cheque.amount,
        receiverName: receiver.fullName,
        status: cheque.status
      }
    });
  } catch (error) {
    await transaction.rollback();
    console.error('Create cheque error:', error);
    res.status(500).json({ message: 'Server error creating cheque' });
  }
});

// Get sent cheques
router.get('/sent', auth, async (req, res) => {
  try {
    const { page = 1, limit = 10, status } = req.query;
    const offset = (page - 1) * limit;

    const whereClause = { senderId: req.user.id };
    if (status) {
      whereClause.status = status;
    }

    const cheques = await Cheque.findAndCountAll({
      where: whereClause,
      include: [
        {
          model: User,
          as: 'Receiver',
          attributes: ['fullName', 'accountNumber']
        }
      ],
      order: [['createdAt', 'DESC']],
      limit: parseInt(limit),
      offset: parseInt(offset)
    });

    res.json({
      cheques: cheques.rows,
      totalPages: Math.ceil(cheques.count / limit),
      currentPage: parseInt(page),
      total: cheques.count
    });
  } catch (error) {
    res.status(500).json({ message: 'Server error fetching sent cheques' });
  }
});

// Get received cheques
router.get('/received', auth, async (req, res) => {
  try {
    const { page = 1, limit = 10, status } = req.query;
    const offset = (page - 1) * limit;

    const whereClause = { receiverId: req.user.id };
    if (status) {
      whereClause.status = status;
    }

    const cheques = await Cheque.findAndCountAll({
      where: whereClause,
      include: [
        {
          model: User,
          as: 'Sender',
          attributes: ['fullName', 'accountNumber']
        }
      ],
      order: [['createdAt', 'DESC']],
      limit: parseInt(limit),
      offset: parseInt(offset)
    });

    res.json({
      cheques: cheques.rows,
      totalPages: Math.ceil(cheques.count / limit),
      currentPage: parseInt(page),
      total: cheques.count
    });
  } catch (error) {
    res.status(500).json({ message: 'Server error fetching received cheques' });
  }
});

// Accept cheque
router.post('/:id/accept', auth, async (req, res) => {
  const dbTransaction = await sequelize.transaction();
  
  try {
    const cheque = await Cheque.findOne({
      where: {
        id: req.params.id,
        receiverId: req.user.id,
        status: 'pending'
      },
      include: [
        { model: User, as: 'Sender' },
        { model: User, as: 'Receiver' }
      ]
    });

    if (!cheque) {
      await dbTransaction.rollback();
      return res.status(404).json({ message: 'Cheque not found or already processed' });
    }

    // Check if cheque is expired
    if (new Date() > cheque.expiryDate) {
      await cheque.update({ status: 'expired' }, { transaction: dbTransaction });
      await dbTransaction.commit();
      return res.status(400).json({ message: 'Cheque has expired' });
    }

    // Verify signature
    const signatureData = `${cheque.chequeNumber}|${cheque.senderId}|${cheque.receiverId}|${cheque.amount}|${cheque.chequeDate.toISOString().split('T')[0]}`;
    const isValidSignature = CryptoUtils.verifySignature(
      signatureData,
      cheque.senderSignature,
      cheque.Sender.publicKey
    );

    if (!isValidSignature) {
      await dbTransaction.rollback();
      return res.status(400).json({ message: 'Invalid cheque signature' });
    }

    // Check sender balance again
    const currentSender = await User.findByPk(cheque.senderId);
    if (parseFloat(currentSender.balance) < parseFloat(cheque.amount)) {
      await dbTransaction.rollback();
      return res.status(400).json({ message: 'Sender has insufficient balance' });
    }

    // Generate transaction ID
    const transactionId = CryptoUtils.generateTransactionId();

    // Update balances
    const senderNewBalance = parseFloat(currentSender.balance) - parseFloat(cheque.amount);
    const receiverNewBalance = parseFloat(cheque.Receiver.balance) + parseFloat(cheque.amount);

    await User.update(
      { balance: senderNewBalance },
      { where: { id: cheque.senderId }, transaction: dbTransaction }
    );

    await User.update(
      { balance: receiverNewBalance },
      { where: { id: cheque.receiverId }, transaction: dbTransaction }
    );

    // Create transaction records
    await Transaction.create({
      transactionId,
      chequeId: cheque.id,
      senderId: cheque.senderId,
      receiverId: cheque.receiverId,
      amount: cheque.amount,
      type: 'debit',
      status: 'completed',
      balanceBefore: currentSender.balance,
      balanceAfter: senderNewBalance,
      description: `Cheque payment to ${cheque.Receiver.fullName}`
    }, { transaction: dbTransaction });

    await Transaction.create({
      transactionId,
      chequeId: cheque.id,
      senderId: cheque.senderId,
      receiverId: cheque.receiverId,
      amount: cheque.amount,
      type: 'credit',
      status: 'completed',
      balanceBefore: cheque.Receiver.balance,
      balanceAfter: receiverNewBalance,
      description: `Cheque received from ${cheque.Sender.fullName}`
    }, { transaction: dbTransaction });

    // Update cheque status
    await cheque.update({
      status: 'accepted',
      receiverAction: new Date(),
      transactionId
    }, { transaction: dbTransaction });

    await dbTransaction.commit();

    // Send real-time notifications
    req.io.to(`user_${cheque.senderId}`).emit('chequeAccepted', {
      chequeId: cheque.id,
      receiverName: cheque.Receiver.fullName,
      amount: cheque.amount,
      message: 'Your cheque has been accepted'
    });

    res.json({
      message: 'Cheque accepted successfully',
      transactionId,
      newBalance: receiverNewBalance
    });
  } catch (error) {
    await dbTransaction.rollback();
    console.error('Accept cheque error:', error);
    res.status(500).json({ message: 'Server error accepting cheque' });
  }
});

// Reject cheque
router.post('/:id/reject', auth, async (req, res) => {
  try {
    const { reason } = req.body;
    
    const cheque = await Cheque.findOne({
      where: {
        id: req.params.id,
        receiverId: req.user.id,
        status: 'pending'
      },
      include: [
        { model: User, as: 'Sender' },
        { model: User, as: 'Receiver' }
      ]
    });

    if (!cheque) {
      return res.status(404).json({ message: 'Cheque not found or already processed' });
    }

    await cheque.update({
      status: 'rejected',
      receiverAction: new Date(),
      message: reason || cheque.message
    });

    // Send real-time notification
    req.io.to(`user_${cheque.senderId}`).emit('chequeRejected', {
      chequeId: cheque.id,
      receiverName: cheque.Receiver.fullName,
      amount: cheque.amount,
      reason: reason,
      message: 'Your cheque has been rejected'
    });

    res.json({ message: 'Cheque rejected successfully' });
  } catch (error) {
    res.status(500).json({ message: 'Server error rejecting cheque' });
  }
});

// Cancel cheque (sender only)
router.post('/:id/cancel', auth, async (req, res) => {
  try {
    const cheque = await Cheque.findOne({
      where: {
        id: req.params.id,
        senderId: req.user.id,
        status: 'pending'
      },
      include: [{ model: User, as: 'Receiver' }]
    });

    if (!cheque) {
      return res.status(404).json({ message: 'Cheque not found or cannot be cancelled' });
    }

    await cheque.update({
      status: 'cancelled',
      receiverAction: new Date()
    });

    // Send real-time notification
    req.io.to(`user_${cheque.receiverId}`).emit('chequeCancelled', {
      chequeId: cheque.id,
      senderName: req.user.fullName,
      amount: cheque.amount,
      message: 'A cheque sent to you has been cancelled'
    });

    res.json({ message: 'Cheque cancelled successfully' });
  } catch (error) {
    res.status(500).json({ message: 'Server error cancelling cheque' });
  }
});

// Get cheque details
router.get('/:id', auth, async (req, res) => {
  try {
    const cheque = await Cheque.findOne({
      where: {
        id: req.params.id,
        [Op.or]: [
          { senderId: req.user.id },
          { receiverId: req.user.id }
        ]
      },
      include: [
        {
          model: User,
          as: 'Sender',
          attributes: ['fullName', 'accountNumber']
        },
        {
          model: User,
          as: 'Receiver',
          attributes: ['fullName', 'accountNumber']
        },
        {
          model: Transaction,
          attributes: ['transactionId', 'status', 'createdAt']
        }
      ]
    });

    if (!cheque) {
      return res.status(404).json({ message: 'Cheque not found' });
    }

    res.json(cheque);
  } catch (error) {
    res.status(500).json({ message: 'Server error fetching cheque details' });
  }
});

// Get dashboard stats
router.get('/dashboard/stats', auth, async (req, res) => {
  try {
    const [sentStats, receivedStats, recentActivity] = await Promise.all([
      // Sent cheques stats
      Cheque.findAll({
        where: { senderId: req.user.id },
        attributes: [
          'status',
          [sequelize.fn('COUNT', sequelize.col('id')), 'count'],
          [sequelize.fn('SUM', sequelize.col('amount')), 'total']
        ],
        group: ['status'],
        raw: true
      }),
      
      // Received cheques stats
      Cheque.findAll({
        where: { receiverId: req.user.id },
        attributes: [
          'status',
          [sequelize.fn('COUNT', sequelize.col('id')), 'count'],
          [sequelize.fn('SUM', sequelize.col('amount')), 'total']
        ],
        group: ['status'],
        raw: true
      }),
      
      // Recent activity
      Cheque.findAll({
        where: {
          [Op.or]: [
            { senderId: req.user.id },
            { receiverId: req.user.id }
          ]
        },
        include: [
          {
            model: User,
            as: 'Sender',
            attributes: ['fullName']
          },
          {
            model: User,
            as: 'Receiver',
            attributes: ['fullName']
          }
        ],
        order: [['updatedAt', 'DESC']],
        limit: 5
      })
    ]);

    res.json({
      sentStats,
      receivedStats,
      recentActivity
    });
  } catch (error) {
    res.status(500).json({ message: 'Server error fetching dashboard stats' });
  }
});

module.exports = router;