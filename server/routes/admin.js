const express = require('express');
const { Op } = require('sequelize');
const { User, Cheque, Transaction } = require('../models');
const { adminAuth } = require('../middleware/auth');
const sequelize = require('../config/database');

const router = express.Router();

// Get admin dashboard stats
router.get('/dashboard', adminAuth, async (req, res) => {
  try {
    const [
      totalUsers,
      totalCheques,
      totalTransactions,
      totalVolume,
      recentUsers,
      recentCheques,
      chequeStats,
      monthlyStats
    ] = await Promise.all([
      // Total users
      User.count({ where: { role: 'user' } }),
      
      // Total cheques
      Cheque.count(),
      
      // Total transactions
      Transaction.count(),
      
      // Total transaction volume
      Transaction.sum('amount', { where: { status: 'completed' } }),
      
      // Recent users
      User.findAll({
        where: { role: 'user' },
        order: [['createdAt', 'DESC']],
        limit: 5,
        attributes: ['id', 'fullName', 'email', 'accountNumber', 'createdAt', 'isActive']
      }),
      
      // Recent cheques
      Cheque.findAll({
        include: [
          { model: User, as: 'Sender', attributes: ['fullName'] },
          { model: User, as: 'Receiver', attributes: ['fullName'] }
        ],
        order: [['createdAt', 'DESC']],
        limit: 10
      }),
      
      // Cheque status stats
      Cheque.findAll({
        attributes: [
          'status',
          [sequelize.fn('COUNT', sequelize.col('id')), 'count']
        ],
        group: ['status'],
        raw: true
      }),
      
      // Monthly transaction stats
      sequelize.query(`
        SELECT 
          DATE_FORMAT(createdAt, '%Y-%m') as month,
          COUNT(*) as count,
          SUM(amount) as volume
        FROM Transactions 
        WHERE createdAt >= DATE_SUB(NOW(), INTERVAL 12 MONTH)
        GROUP BY DATE_FORMAT(createdAt, '%Y-%m')
        ORDER BY month DESC
      `, { type: sequelize.QueryTypes.SELECT })
    ]);

    res.json({
      stats: {
        totalUsers,
        totalCheques,
        totalTransactions,
        totalVolume: totalVolume || 0
      },
      recentUsers,
      recentCheques,
      chequeStats,
      monthlyStats
    });
  } catch (error) {
    console.error('Admin dashboard error:', error);
    res.status(500).json({ message: 'Server error fetching admin dashboard' });
  }
});

// Get all users with pagination
router.get('/users', adminAuth, async (req, res) => {
  try {
    const { page = 1, limit = 10, search, status } = req.query;
    const offset = (page - 1) * limit;

    const whereClause = { role: 'user' };
    
    if (search) {
      whereClause[Op.or] = [
        { fullName: { [Op.like]: `%${search}%` } },
        { email: { [Op.like]: `%${search}%` } },
        { accountNumber: { [Op.like]: `%${search}%` } }
      ];
    }
    
    if (status !== undefined) {
      whereClause.isActive = status === 'active';
    }

    const users = await User.findAndCountAll({
      where: whereClause,
      attributes: { exclude: ['password', 'privateKey'] },
      order: [['createdAt', 'DESC']],
      limit: parseInt(limit),
      offset: parseInt(offset)
    });

    res.json({
      users: users.rows,
      totalPages: Math.ceil(users.count / limit),
      currentPage: parseInt(page),
      total: users.count
    });
  } catch (error) {
    res.status(500).json({ message: 'Server error fetching users' });
  }
});

// Toggle user status
router.put('/users/:id/toggle-status', adminAuth, async (req, res) => {
  try {
    const user = await User.findByPk(req.params.id);
    
    if (!user || user.role === 'admin') {
      return res.status(404).json({ message: 'User not found' });
    }

    await user.update({ isActive: !user.isActive });
    
    res.json({
      message: `User ${user.isActive ? 'activated' : 'deactivated'} successfully`,
      user: {
        id: user.id,
        fullName: user.fullName,
        isActive: user.isActive
      }
    });
  } catch (error) {
    res.status(500).json({ message: 'Server error updating user status' });
  }
});

// Get all cheques with pagination
router.get('/cheques', adminAuth, async (req, res) => {
  try {
    const { page = 1, limit = 10, status, search } = req.query;
    const offset = (page - 1) * limit;

    const whereClause = {};
    
    if (status) {
      whereClause.status = status;
    }
    
    if (search) {
      whereClause.chequeNumber = { [Op.like]: `%${search}%` };
    }

    const cheques = await Cheque.findAndCountAll({
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
      cheques: cheques.rows,
      totalPages: Math.ceil(cheques.count / limit),
      currentPage: parseInt(page),
      total: cheques.count
    });
  } catch (error) {
    res.status(500).json({ message: 'Server error fetching cheques' });
  }
});

// Get all transactions with pagination
router.get('/transactions', adminAuth, async (req, res) => {
  try {
    const { page = 1, limit = 10, status, type } = req.query;
    const offset = (page - 1) * limit;

    const whereClause = {};
    
    if (status) {
      whereClause.status = status;
    }
    
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

// Get system logs (simplified)
router.get('/logs', adminAuth, async (req, res) => {
  try {
    const { page = 1, limit = 50 } = req.query;
    const offset = (page - 1) * limit;

    // This is a simplified log system - in production, you'd use proper logging
    const logs = await Transaction.findAndCountAll({
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
      logs: logs.rows.map(transaction => ({
        id: transaction.id,
        timestamp: transaction.createdAt,
        action: `${transaction.type.toUpperCase()} - ${transaction.status}`,
        user: transaction.type === 'debit' ? transaction.Sender.fullName : transaction.Receiver.fullName,
        details: `${transaction.description} - Amount: $${transaction.amount}`,
        status: transaction.status
      })),
      totalPages: Math.ceil(logs.count / limit),
      currentPage: parseInt(page),
      total: logs.count
    });
  } catch (error) {
    res.status(500).json({ message: 'Server error fetching logs' });
  }
});

module.exports = router;