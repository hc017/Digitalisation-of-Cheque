package com.raven.model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Date;

public class ModelUser {
    private int accountNumber;
    private int bankId;
    private String accountHolderName;
    private String phoneNumber;
    private String username;
    private String password;
    private Date dateOfBirth;
    private BigDecimal balance;
    private String gender;
    private String email;

    public ModelUser(int accountNumber, int bankId, String accountHolderName, String phoneNumber, String username, String password, Date dateOfBirth, BigDecimal balance, String gender, String email) {
        this.accountNumber = accountNumber;
        this.bankId = bankId;
        this.accountHolderName = accountHolderName;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.balance = balance;
        this.gender = gender;
        this.email = email;
    }
    public ModelUser(int bankId,String username,String email,int accountNumber){
        this.bankId = bankId;
        this.username = username;
        this.email = email;
        this.accountNumber = accountNumber;
    }

    public ModelUser() {
        this.accountNumber = accountNumber;
        this.bankId = bankId;
        this.accountHolderName = accountHolderName;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.balance = balance;
        this.gender = gender;
        this.email = email;
    }

   

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
