const User = require('./User');
const Cheque = require('./Cheque');
const Transaction = require('./Transaction');

// Define associations
User.hasMany(Cheque, { as: 'SentCheques', foreignKey: 'senderId' });
User.hasMany(Cheque, { as: 'ReceivedCheques', foreignKey: 'receiverId' });

Cheque.belongsTo(User, { as: 'Sender', foreignKey: 'senderId' });
Cheque.belongsTo(User, { as: 'Receiver', foreignKey: 'receiverId' });

User.hasMany(Transaction, { as: 'SentTransactions', foreignKey: 'senderId' });
User.hasMany(Transaction, { as: 'ReceivedTransactions', foreignKey: 'receiverId' });

Transaction.belongsTo(User, { as: 'Sender', foreignKey: 'senderId' });
Transaction.belongsTo(User, { as: 'Receiver', foreignKey: 'receiverId' });
Transaction.belongsTo(Cheque, { foreignKey: 'chequeId' });

Cheque.hasMany(Transaction, { foreignKey: 'chequeId' });

module.exports = {
  User,
  Cheque,
  Transaction
};