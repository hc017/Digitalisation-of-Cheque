

package com.raven.createAccount;

import com.raven.main.Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import com.raven.model.ModelUser;
public class UserAcc extends javax.swing.JFrame {

    public UserAcc(ModelUser user) {
       
        initComponents();
        String linkAcc = "<html><u> LINK BANK ACCOUNT  </u></html>";
        linkac.setText(linkAcc);
    }

    public UserAcc(String password) {
    initComponents();
    String linkAcc = "<html><u> LINK BANK ACCOUNT  </u></html>";
    linkac.setText(linkAcc);
    accpassword.setText(password); // set the value of the password field to the password argument
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        linkac = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        accno = new javax.swing.JTextField();
        nameuser = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        accpassword = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        ifsc = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        accdob = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        accbankid = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        bankname = new javax.swing.JComboBox<>();
        accgender = new javax.swing.JComboBox<>();
        accsubmit = new com.raven.createAccount.MyButtonDesign();
        accreset = new com.raven.createAccount.MyButtonDesign();
        accback = new com.raven.createAccount.MyButtonDesign();
        jLabel11 = new javax.swing.JLabel();
        accmobnum = new javax.swing.JTextField();
        user = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/accbg (2).jpg"))); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Link Account");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 0, 0, 70));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        linkac.setFont(new java.awt.Font("Lucida Fax", 3, 20)); // NOI18N
        linkac.setForeground(new java.awt.Color(255, 255, 255));
        linkac.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        linkac.setText("LINK BANK ACCOUNT");

        jLabel8.setFont(new java.awt.Font("Myanmar Text", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Name:");

        jLabel3.setFont(new java.awt.Font("Myanmar Text", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Account Number:");

        accno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                accnoKeyPressed(evt);
            }
        });

        nameuser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nameuserKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Myanmar Text", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Password:");

        jLabel6.setFont(new java.awt.Font("Myanmar Text", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Gender:");

        jLabel5.setFont(new java.awt.Font("Myanmar Text", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("IFSC Code:");

        jLabel7.setFont(new java.awt.Font("Myanmar Text", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Date of Birth:");

        accbankid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                accbankidKeyPressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Myanmar Text", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Bank ID:");

        jLabel10.setFont(new java.awt.Font("Myanmar Text", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Bank Name:");

        bankname.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--SELECT BANK--", "SBI", "ICICI", "BOI", "HDFC", "PNB" }));

        accgender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--SELECT--", "Male", "Female", "Other" }));

        accsubmit.setBackground(new java.awt.Color(48, 122, 184));
        accsubmit.setBorder(null);
        accsubmit.setForeground(new java.awt.Color(255, 255, 255));
        accsubmit.setText("Submit");
        accsubmit.setFont(new java.awt.Font("Myanmar Text", 1, 16)); // NOI18N
        accsubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accsubmitActionPerformed(evt);
            }
        });

        accreset.setBackground(new java.awt.Color(48, 122, 184));
        accreset.setForeground(new java.awt.Color(255, 255, 255));
        accreset.setText("Reset");
        accreset.setFont(new java.awt.Font("Myanmar Text", 1, 16)); // NOI18N
        accreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accresetActionPerformed(evt);
            }
        });

        accback.setBackground(new java.awt.Color(48, 122, 184));
        accback.setForeground(new java.awt.Color(255, 255, 255));
        accback.setText("Back");
        accback.setFont(new java.awt.Font("Myanmar Text", 1, 16)); // NOI18N
        accback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accbackActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Myanmar Text", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Mobile Number:");

        accmobnum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                accmobnumKeyPressed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Myanmar Text", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Username:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(accsubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(91, 91, 91)
                                .addComponent(accreset, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(accpassword)
                                    .addComponent(bankname, 0, 234, Short.MAX_VALUE)
                                    .addComponent(accno, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                                    .addComponent(ifsc, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                                    .addComponent(accmobnum))
                                .addGap(91, 91, 91)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(accdob, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(accback, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(accgender, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nameuser, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(accbankid, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(user, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 35, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(linkac, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(301, 301, 301))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(linkac, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bankname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(accbankid, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(accno, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nameuser, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(accdob, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ifsc, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(accpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(accgender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(accmobnum, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)))
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(user, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(accreset, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(accsubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(accback, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 900, 470));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/accbg (2).jpg"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1090, 590));

        setSize(new java.awt.Dimension(1037, 598));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void accresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accresetActionPerformed
        // reset all data to null
        accno.setText(null);
        accmobnum.setText(null);
        user.setText(null);
        accpassword.setText(null);
        ifsc.setText(null);
        nameuser.setText(null);
        accbankid.setText(null);
        accgender.setSelectedIndex(0);
        bankname.setSelectedIndex(0);
        accdob.setCalendar(null);
    }//GEN-LAST:event_accresetActionPerformed

    private void accbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accbackActionPerformed
        // go back to signin/signup page
        Main m = new Main();
        m.show();           // display signin page
        
        dispose();          // close current frame after opening signin page
    }//GEN-LAST:event_accbackActionPerformed

    private void accsubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accsubmitActionPerformed
        // save data to the database
        try {
            String gender;
            String bank_name;
            String username;
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","harsh170");
            System.out.println("Connection is successful.");
            String query2 = "INSERT INTO accounts (account_number, bank_id, account_holder_name, phone_number,password, date_of_birth, gender,username) values(?,?,?,?,?,?,?,?)";

            PreparedStatement pst2 = con.prepareStatement(query2);
            
            bank_name = bankname.getSelectedItem().toString();
            pst2.setString(1, accno.getText());
            pst2.setString(2, accbankid.getText());
            pst2.setString(3, nameuser.getText());
            pst2.setString(4, accmobnum.getText());
            pst2.setString(5, new String(accpassword.getPassword()));
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            String date = sd.format(accdob.getDate());
            pst2.setString(6, date);
            gender = accgender.getSelectedItem().toString();
            pst2.setString(7,gender);
            username = user.getText();
            pst2.setString(8,username);
            
            pst2.executeUpdate();
            JOptionPane.showMessageDialog(null,"Inserted successfully");      // shows popup msg
        }
        catch(Exception e)
        {
            System.out.println("Connection is not successful.");
            JOptionPane.showMessageDialog(null,e);
        }
        
    }//GEN-LAST:event_accsubmitActionPerformed

    private void accnoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_accnoKeyPressed
        // no string or special characters can be entered
        char c = evt.getKeyChar();
            
        if(!Character.isDigit(c) && !Character.isWhitespace(c) && !Character.isISOControl(c)) {
            accno.setEditable(false);
            JOptionPane.showMessageDialog(null, "Alphabets and Special characters are not allowed.");
            accno.setEditable(true);
        }
    }//GEN-LAST:event_accnoKeyPressed

    private void accmobnumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_accmobnumKeyPressed
        // no string or special characters can be entered
        char c = evt.getKeyChar();
            
        if(!Character.isDigit(c) && !Character.isWhitespace(c) && !Character.isISOControl(c)) {
            accmobnum.setEditable(false);
            JOptionPane.showMessageDialog(null, "Alphabets and Special characters are not allowed.");
            accmobnum.setEditable(true);
        }
    }//GEN-LAST:event_accmobnumKeyPressed

    private void accbankidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_accbankidKeyPressed
// no string or special characters can be entered
        char c = evt.getKeyChar();
            
        if(!Character.isDigit(c) && !Character.isWhitespace(c) && !Character.isISOControl(c)) {
            accbankid.setEditable(false);
            JOptionPane.showMessageDialog(null, "Alphabets and Special characters are not allowed.");
            accbankid.setEditable(true);
        }    }//GEN-LAST:event_accbankidKeyPressed

    private void nameuserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameuserKeyPressed
// no number or special characters can be entered
        char c = evt.getKeyChar();
        if (!Character.isLetter(c) && !Character.isWhitespace(c) && !Character.isISOControl(c)) {
        nameuser.setEditable(false);
        JOptionPane.showMessageDialog(null, "Only alphabets and spaces are allowed.");
        nameuser.setEditable(true);
        }
    }//GEN-LAST:event_nameuserKeyPressed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserAcc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserAcc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserAcc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserAcc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            public void run() {
                ModelUser user = null;
                new UserAcc(user).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.createAccount.MyButtonDesign accback;
    private javax.swing.JTextField accbankid;
    private com.toedter.calendar.JDateChooser accdob;
    private javax.swing.JComboBox<String> accgender;
    private javax.swing.JTextField accmobnum;
    private javax.swing.JTextField accno;
    private javax.swing.JPasswordField accpassword;
    private com.raven.createAccount.MyButtonDesign accreset;
    private com.raven.createAccount.MyButtonDesign accsubmit;
    private javax.swing.JComboBox<String> bankname;
    private javax.swing.JTextField ifsc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel linkac;
    private javax.swing.JTextField nameuser;
    private javax.swing.JTextField user;
    // End of variables declaration//GEN-END:variables

}