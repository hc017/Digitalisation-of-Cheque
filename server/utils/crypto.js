const crypto = require('crypto');

class CryptoUtils {
  static generateKeyPair() {
    const { publicKey, privateKey } = crypto.generateKeyPairSync('rsa', {
      modulusLength: 2048,
      publicKeyEncoding: {
        type: 'spki',
        format: 'pem'
      },
      privateKeyEncoding: {
        type: 'pkcs8',
        format: 'pem'
      }
    });
    
    return { publicKey, privateKey };
  }

  static signData(data, privateKey) {
    const sign = crypto.createSign('SHA256');
    sign.update(data);
    sign.end();
    return sign.sign(privateKey, 'hex');
  }

  static verifySignature(data, signature, publicKey) {
    try {
      const verify = crypto.createVerify('SHA256');
      verify.update(data);
      verify.end();
      return verify.verify(publicKey, signature, 'hex');
    } catch (error) {
      return false;
    }
  }

  static generateHash(data) {
    return crypto.createHash('sha256').update(data).digest('hex');
  }

  static generateTransactionId() {
    return 'TXN_' + crypto.randomBytes(16).toString('hex').toUpperCase();
  }

  static generateChequeNumber() {
    const timestamp = Date.now().toString();
    const random = crypto.randomBytes(4).toString('hex').toUpperCase();
    return `CHQ_${timestamp}_${random}`;
  }
}

module.exports = CryptoUtils;