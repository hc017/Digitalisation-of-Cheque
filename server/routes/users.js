const express = require('express');
const { Op } = require('sequelize');
const { User, Transaction } = require('../models');
const { auth } = require('../middleware/auth');

const router = express.Router();

// Search users by account number or name
router.get('/search', auth, async (req, res) => {
  try {
    const { query } = req.query;
    
    if (!query || query.length < 3) {
      return res.status(400).json({ message: 'Search query must be at least 3 characters' });
    }

    const users = await User.findAll({
      where: {
        [Op.and]: [
          { id: { [Op.ne]: req.user.id } }, // Exclude current user
          { isActive: true },
          {
            [Op.or]: [
              { accountNumber: { [Op.like]: `%${query}%` } },
              { fullName: { [Op.like]: `%${query}%` } },
              { username: { [Op.like]: `%${query}%` } }
            ]
          }
        ]
      },
      attributes: ['id', 'fullName', 'accountNumber', 'username'],
      limit: 10
    });

    res.json(users);
  } catch (error) {
    res.status(500).json({ message: 'Server error searching users' });
  }
});

// Get user balance
router.get('/balance', auth, async (req, res) => {
  try {
    const user = await User.findByPk(req.user.id, {
      attributes: ['balance']
    });
    
    res.json({ balance: user.balance });
  } catch (error) {
    res.status(500).json({ message: 'Server error fetching balance' });
  }
});

// Get transaction history
router.get('/transactions', auth, async (req, res) => {
  try {
    const { page = 1, limit = 10, type } = req.query;
    const offset = (page - 1) * limit;

    const whereClause = {
      [Op.or]: [
        { senderId: req.user.id },
        { receiverId: req.user.id }
      ]
    };

    if (type) {
      whereClause.type = type;
    }

    const transactions = await Transaction.findAndCountAll({
      where: whereClause,
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
        }
      ],
      order: [['createdAt', 'DESC']],
      limit: parseInt(limit),
      offset: parseInt(offset)
    });

    res.json({
      transactions: transactions.rows,
      totalPages: Math.ceil(transactions.count / limit),
      currentPage: parseInt(page),
      total: transactions.count
    });
  } catch (error) {
    res.status(500).json({ message: 'Server error fetching transactions' });
  }
});

// Get user by account number
router.get('/account/:accountNumber', auth, async (req, res) => {
  try {
    const { accountNumber } = req.params;
    
    const user = await User.findOne({
      where: { 
        accountNumber,
        isActive: true,
        id: { [Op.ne]: req.user.id } // Exclude current user
      },
      attributes: ['id', 'fullName', 'accountNumber', 'username']
    });

    if (!user) {
      return res.status(404).json({ message: 'User not found' });
    }

    res.json(user);
  } catch (error) {
    res.status(500).json({ message: 'Server error fetching user' });
  }
});

module.exports = router;