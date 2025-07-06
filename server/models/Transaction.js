const { DataTypes } = require('sequelize');
const sequelize = require('../config/database');

const Transaction = sequelize.define('Transaction', {
  id: {
    type: DataTypes.INTEGER,
    primaryKey: true,
    autoIncrement: true
  },
  transactionId: {
    type: DataTypes.STRING,
    allowNull: false,
    unique: true
  },
  chequeId: {
    type: DataTypes.INTEGER,
    allowNull: false,
    references: {
      model: 'Cheques',
      key: 'id'
    }
  },
  senderId: {
    type: DataTypes.INTEGER,
    allowNull: false,
    references: {
      model: 'Users',
      key: 'id'
    }
  },
  receiverId: {
    type: DataTypes.INTEGER,
    allowNull: false,
    references: {
      model: 'Users',
      key: 'id'
    }
  },
  amount: {
    type: DataTypes.DECIMAL(15, 2),
    allowNull: false
  },
  type: {
    type: DataTypes.ENUM('debit', 'credit'),
    allowNull: false
  },
  status: {
    type: DataTypes.ENUM('pending', 'completed', 'failed', 'reversed'),
    defaultValue: 'pending'
  },
  balanceBefore: {
    type: DataTypes.DECIMAL(15, 2),
    allowNull: false
  },
  balanceAfter: {
    type: DataTypes.DECIMAL(15, 2),
    allowNull: false
  },
  description: {
    type: DataTypes.STRING,
    allowNull: true
  }
}, {
  timestamps: true,
  indexes: [
    {
      fields: ['transactionId']
    },
    {
      fields: ['senderId']
    },
    {
      fields: ['receiverId']
    },
    {
      fields: ['status']
    }
  ]
});

module.exports = Transaction;