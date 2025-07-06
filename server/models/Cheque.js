const { DataTypes } = require('sequelize');
const sequelize = require('../config/database');

const Cheque = sequelize.define('Cheque', {
  id: {
    type: DataTypes.INTEGER,
    primaryKey: true,
    autoIncrement: true
  },
  chequeNumber: {
    type: DataTypes.STRING,
    allowNull: false,
    unique: true
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
    allowNull: false,
    validate: {
      min: 0.01
    }
  },
  message: {
    type: DataTypes.TEXT,
    allowNull: true
  },
  chequeDate: {
    type: DataTypes.DATE,
    allowNull: false
  },
  expiryDate: {
    type: DataTypes.DATE,
    allowNull: false
  },
  status: {
    type: DataTypes.ENUM('pending', 'accepted', 'rejected', 'expired', 'cancelled'),
    defaultValue: 'pending'
  },
  senderSignature: {
    type: DataTypes.TEXT,
    allowNull: false
  },
  signatureHash: {
    type: DataTypes.STRING,
    allowNull: false
  },
  receiverAction: {
    type: DataTypes.DATE,
    allowNull: true
  },
  transactionId: {
    type: DataTypes.STRING,
    allowNull: true,
    unique: true
  }
}, {
  timestamps: true,
  indexes: [
    {
      fields: ['senderId']
    },
    {
      fields: ['receiverId']
    },
    {
      fields: ['status']
    },
    {
      fields: ['chequeDate']
    }
  ]
});

module.exports = Cheque;