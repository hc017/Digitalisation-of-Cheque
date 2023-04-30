
package com.raven.model;

/**
 *
 * @author hc
 */
public class ModelSignUp {

    
    
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    
    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public ModelSignUp(int userID,String bankName,String bankid ,String userName, String email, String password, String verifyCode) {
        this.userID = userID;
         this.bankName = bankName;
         this.bankid = bankid;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.verifyCode = verifyCode;
    }

    public ModelSignUp(int userID, String bankName, String bankid, String userName, String email, String password) {
        this.userID = userID;
        this.bankName = bankName;
        this.bankid = bankid;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public ModelSignUp() {
    }

    private int userID;
    private String bankName;
    private String bankid;
    private String userName;
    private String email;
    private String password;
    private String verifyCode;
}
