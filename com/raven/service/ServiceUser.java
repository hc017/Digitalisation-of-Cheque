
package com.raven.service;
        
import com.raven.connection.DatabaseConnection;
import com.raven.model.ModelLogin;
import com.raven.model.ModelSignUp;
import com.raven.model.ModelUser;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

public class ServiceUser {

    private final Connection con;
    private ModelUser currentUser;
    private ModelSignUp userUP;

    public ServiceUser() {
        con = DatabaseConnection.getInstance().getConnection();
    }
public ModelUser login(ModelLogin login) throws SQLException {
    
    try {
        PreparedStatement p = con.prepareStatement("SELECT * FROM accounts WHERE username = ? AND password = ?");
        p.setString(1, login.getUserName());
        p.setString(2, login.getPassword());
        ResultSet rs = p.executeQuery();
        
        if (rs.next()) {
            int bankId = rs.getInt("bank_id");
            String username = rs.getString("username");
            String email = rs.getString("email");
            long accountNumber = rs.getLong("account_number");
            String code = rs.getString("code");
            String password = rs.getString("password");
            BigDecimal balance = rs.getBigDecimal("balance");
            String gender = rs.getString("gender");
            String accountHolderName = rs.getString("account_holder_name");
            String phoneNumber = rs.getString("phone_number");
            Date dateOfBirth = rs.getDate("date_of_birth");

            // Initialize currentUser with the retrieved user data
            currentUser = new ModelUser((int) accountNumber,bankId,accountHolderName,  phoneNumber,  username,  password,  dateOfBirth,  balance,  gender,  email);
        }

        rs.close();
        p.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return currentUser;
}
    public void insertUser(ModelSignUp userUP) throws SQLException {
       
       PreparedStatement p = con.prepareStatement("insert into `userUP` (BankName,Bankid,UserName, Email, `Password`, VerifyCode) values (?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        String code = generateVerifyCode();
        p.setString(1, userUP.getBankName());
        p.setString(2, userUP.getBankid());
        p.setString(3, userUP.getUserName());
        p.setString(4, userUP.getEmail());
        p.setString(5, userUP.getPassword());
        p.setString(6, code);
        p.execute();
        ResultSet r = p.getGeneratedKeys();
        r.first();
        int userID = r.getInt(1);
        r.close();
        p.close();
        userUP.setUserID(userID);
        userUP.setVerifyCode(code);

    }

private String generateVerifyCode() throws SQLException {
        DecimalFormat df = new DecimalFormat("000000");
        Random ran = new Random();
        String code = df.format(ran.nextInt(1000000));  //  Random from 0 to 999999
        while (checkDuplicateCode(code)) {
            code = df.format(ran.nextInt(1000000));
        }
        return code;
    }

 private boolean checkDuplicateCode(String code) throws SQLException {
    boolean duplicate = false;
    try {
        PreparedStatement p = con.prepareStatement("select UserID from `userUP` where VerifyCode=? limit 1");
        p.setString(1, code);
        ResultSet r = p.executeQuery();
        if (r.first()) {
            duplicate = true;
        }
        r.close();
        p.close();
    } catch (SQLException ex) {
        System.out.println("SQL error code: " + ex.getErrorCode());
    }
    return duplicate;
}

 public boolean checkDuplicateUser(String user) throws SQLException {
    boolean duplicate = false;
    try {
        PreparedStatement p = con.prepareStatement("select UserID from `userUP` where UserName=? and `Status`='Verified' limit 1");
        p.setString(1, user);
        ResultSet r = p.executeQuery();
        if (r.first()) {
            duplicate = true;
        }
        r.close();
        p.close();
    } catch (SQLException ex) {
        System.out.println("SQL error code: " + ex.getErrorCode());
    }
    return duplicate;
}


  public boolean checkDuplicateEmail(String user) {
    boolean duplicate = false;
    try {
        PreparedStatement p = con.prepareStatement("select UserID from `userUP` where Email=? and `Status`='Verified' limit 1");
        p.setString(1, user);
        ResultSet r = p.executeQuery();
        if (r.first()) {
            duplicate = true;
        }
        r.close();
        p.close();
    } catch (SQLException e) {
        // Handle the exception here, e.g. log it or throw a custom exception
    }
    return duplicate;
}


    public void doneVerify(int userID) throws SQLException {
        PreparedStatement p = con.prepareStatement("update `userUP` set VerifyCode='', `Status`='Verified' where UserID=? limit 1");
        p.setInt(1, userID);
        p.execute();
        p.close();
    }

   public boolean verifyCodeWithUser(int userID, String code) throws SQLException {
    boolean verify = false;
try (PreparedStatement p = con.prepareStatement("SELECT UserID FROM userUP WHERE UserID=? AND VerifyCode=? LIMIT 1")) {
    p.setInt(1, userID);
    p.setString(2, code);
    try (ResultSet r = p.executeQuery()) {
        if (r.next()) {
            verify = true;
        }
    }
} catch (SQLException e) {
    e.printStackTrace();
}
return verify;

}

    // for change password
    public void changePassword(ModelUser user, String oldPassword, String newPassword) throws SQLException {
    PreparedStatement p = con.prepareStatement("SELECT * FROM accounts WHERE account_number = ? AND password = ?");
    p.setInt(1, (int) user.getAccountNumber());
    p.setString(2, oldPassword);
    ResultSet rs = p.executeQuery();

    if (rs.next()) {
        PreparedStatement p2 = con.prepareStatement("UPDATE accounts SET password = ? WHERE account_number = ?");
        p2.setString(1, newPassword);
        p2.setInt(2, (int) user.getAccountNumber());
        p2.executeUpdate();
        p2.close();
    } else {
        throw new SQLException("Invalid old password");
    }

    rs.close();
    p.close();
}

public double checkBalance(ModelUser user) throws SQLException {
    PreparedStatement p = con.prepareStatement("SELECT balance FROM accounts WHERE account_number = ?");
    p.setInt(1, (int) user.getAccountNumber());
    ResultSet rs = p.executeQuery();

    double balance;
    if (rs.next()) {
        balance = rs.getDouble("balance");
    } else {
        throw new SQLException("Account not found");
    }

    rs.close();
    p.close();

    return balance;
}

    public void deposit(ModelUser user, double amount) throws SQLException {
    PreparedStatement p = con.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_number = ?");
    p.setDouble(1, amount);
    p.setInt(2, (int) user.getAccountNumber());
    int rowsUpdated = p.executeUpdate();
    p.close();

    if (rowsUpdated == 0) {
        throw new SQLException("Account not found");
    }
}
    public void withdraw(ModelUser user, double amount) throws SQLException {
    PreparedStatement p = con.prepareStatement("SELECT balance FROM accounts WHERE account_number = ?");
    p.setInt(1, (int) user.getAccountNumber());
    ResultSet rs = p.executeQuery();

    double balance;
    if (rs.next()) {
        balance = rs.getDouble("balance");
        if (balance < amount) {
            throw new SQLException("Insufficient funds");
        } else {
            PreparedStatement p2 = con.prepareStatement("UPDATE accounts SET balance = ? WHERE account_number = ?");
            p2.setDouble(1, balance - amount);
            p2.setInt(2, (int) user.getAccountNumber());
            p2.executeUpdate();
            p2.close();
        }
    } else {
        throw new SQLException("Account not found");
    }

    rs.close();
    p.close();

    
    }
   public void transfer(ModelUser fromUser, ModelUser toUser, double amount) throws SQLException {
    // Check if fromUser has enough balance
    PreparedStatement p1 = con.prepareStatement("SELECT balance FROM accounts WHERE account_number = ?");
    p1.setLong(1, fromUser.getAccountNumber());
    ResultSet rs1 = p1.executeQuery();

    double fromUserBalance;
    if (rs1.next()) {
        fromUserBalance = rs1.getDouble("balance");
    } else {
        throw new SQLException("From account not found");
    }

    rs1.close();
    p1.close();

    if (fromUserBalance < amount) {
        throw new SQLException("Insufficient balance");
    }

    // Deduct amount from fromUser account
    PreparedStatement p2 = con.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE account_number = ?");
    p2.setDouble(1, amount);
    p2.setLong(2, fromUser.getAccountNumber());
    p2.executeUpdate();
    p2.close();

    // Add amount to toUser account
    PreparedStatement p3 = con.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_number = ?");
    p3.setDouble(1, amount);
    p3.setLong(2, toUser.getAccountNumber());
    p3.executeUpdate();
    p3.close();

    // Log the transaction
    PreparedStatement p4 = con.prepareStatement("INSERT INTO dropbox (sender_bank_id, receiver_bank_account_number, sender_account_number, amount) VALUES (?, ?, ?, ?)");
    p4.setInt(1, fromUser.getBankId());
    p4.setLong(2, toUser.getAccountNumber());
    p4.setLong(3, fromUser.getAccountNumber());
    p4.setDouble(4, amount);
    p4.executeUpdate();
    p4.close();
}


public ModelUser getUserByAccountNumber(int accountNumber) throws SQLException {
    PreparedStatement p = null;
    ResultSet rs = null;
    ModelUser user = null;

    try {
        p = con.prepareStatement("SELECT * FROM accounts WHERE account_number = ?");
        p.setLong(1, accountNumber);
        rs = p.executeQuery();

        if (rs.next()) {
            int bankId = rs.getInt("bank_id");
            String accountHolderName = rs.getString("account_holder_name");
            String phoneNumber = rs.getString("phone_number");
            String username = rs.getString("username");
            String password = rs.getString("password");
            Date dateOfBirth = rs.getDate("date_of_birth");
            BigDecimal balance = rs.getBigDecimal("balance");
            String gender = rs.getString("gender");
            String email = rs.getString("email");

            user = new ModelUser(accountNumber, bankId, accountHolderName, phoneNumber, username, password, dateOfBirth, balance, gender, email);
        } else {
            throw new SQLException("User not found");
        }
    } finally {
        if (rs != null) {
            rs.close();
        }
        if (p != null) {
            p.close();
        }
    }

    return user;
}



public void sendCheck(ModelUser sender, long receiverAccountNumber, double amount) throws SQLException {
    // Check if sender has enough balance
    PreparedStatement p1 = con.prepareStatement("SELECT balance FROM accounts WHERE account_number = ?");
    p1.setLong(1, sender.getAccountNumber());
    ResultSet rs1 = p1.executeQuery();

    double senderBalance;
    if (rs1.next()) {
        senderBalance = rs1.getDouble("balance");
    } else {
        throw new SQLException("Sender account not found");
    }

    rs1.close();
    p1.close();

    if (senderBalance < amount) {
        throw new SQLException("Insufficient balance");
    }

//    // Deduct amount from sender account
//    PreparedStatement p2 = con.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE account_number = ?");
//    p2.setDouble(1, amount);
//    p2.setLong(2, sender.getAccountNumber());
//    p2.executeUpdate();
//    p2.close();
//
//    // Add check to receiver's dropbox
//    PreparedStatement p3 = con.prepareStatement("INSERT INTO dropbox (sender_bank_id, receiver_bank_account_number, sender_account_number, amount) VALUES (?, ?, ?, ?)");
//    p3.setInt(1, sender.getBankId());
//    p3.setLong(2, receiverAccountNumber);
//    p3.setLong(3, sender.getAccountNumber());
//    p3.setDouble(4, amount);
//    p3.executeUpdate();
//    p3.close();

//    showMessage(Message.MessageType.INFO, String.format("$%.2f check sent successfully to account number %d", amount, receiverAccountNumber));
}
public void claimCheck(ModelUser receiver, int transactionId) throws SQLException {
    // Check if the check exists in the dropbox of the receiver
    PreparedStatement p1 = con.prepareStatement("SELECT * FROM dropbox WHERE transaction_id = ? AND receiver_bank_account_number = ?");
    p1.setInt(1, transactionId);
    p1.setLong(2, receiver.getAccountNumber());
    ResultSet rs1 = p1.executeQuery();

    if (!rs1.next()) {
        throw new SQLException("Invalid transaction ID or check not found in your dropbox");
    }

    double amount = rs1.getDouble("amount");
    long senderAccountNumber = rs1.getLong("sender_account_number");

    rs1.close();
    p1.close();

    // Add amount to receiver account
    PreparedStatement p2 = con.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_number = ?");
    p2.setDouble(1, amount);
    p2.setLong(2, receiver.getAccountNumber());
    p2.executeUpdate();
    p2.close();

    // Remove the check from dropbox
    PreparedStatement p3 = con.prepareStatement("DELETE FROM dropbox WHERE transaction_id = ?");
    p3.setInt(1, transactionId);
    p3.executeUpdate();
    p3.close();

    // Deduct amount from sender account
    PreparedStatement p4 = con.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE account_number = ?");
    p4.setDouble(1, amount);
    p4.setLong(2, senderAccountNumber);
    p4.executeUpdate();
    p4.close();
}
public void deleteAccount(ModelUser user) throws SQLException {
    // Check if the account exists
    PreparedStatement p = con.prepareStatement("SELECT account_number FROM accounts WHERE account_number = ?");
    p.setLong(1, user.getAccountNumber());
    ResultSet rs = p.executeQuery();

    if (!rs.next()) {
        throw new SQLException("Account not found");
    }

    // Delete the account
    p = con.prepareStatement("DELETE FROM accounts WHERE account_number = ?");
    p.setLong(1, user.getAccountNumber());
    p.executeUpdate();
    p.close();
}
public void changePasswordByAccountNumber(int accountNumber, String newPassword) throws SQLException {
    ModelUser user = getUserByAccountNumber(accountNumber);
    user.setPassword(newPassword);
    PreparedStatement p = null;
    try {
        p = con.prepareStatement("UPDATE accounts SET password = ? WHERE account_number = ?");
        p.setString(1, newPassword);
        p.setLong(2, accountNumber);
        p.executeUpdate();
    } finally {
        if (p != null) {
            p.close();
        }
    }
}



}



