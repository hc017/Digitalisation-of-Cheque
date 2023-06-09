
package netbanking;

import com.raven.connection.DatabaseConnection;
import com.raven.model.ModelUser;
import com.raven.service.ServiceUser;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author Admin
 */
public class transfer extends javax.swing.JFrame {

    /**
     * Creates new form transfer
     */
     private ModelUser currentUser;
     private ServiceUser service;
     private Connection con;
         
    public transfer(ModelUser user, Connection con, ServiceUser service) {
        initComponents();
          this.currentUser = user;
          this.con = con;
          this.service = service;
           BigDecimal Balance = currentUser.getBalance();
    int accountNumber = currentUser.getAccountNumber();
    }
     public ModelUser getCurrentUser()
    {
        return currentUser;
    }
     public transfer() {
         
     }
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        amt = new javax.swing.JTextField();
        rece_acc = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        send_acc = new javax.swing.JTextField();
        getotp = new javax.swing.JButton();
        back = new javax.swing.JButton();
        reset = new javax.swing.JButton();
        pass = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(39, 144, 191));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("TRANSFER FUNDS");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 290, 95));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Sender's Account Number:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, 250, 58));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Password:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, 250, 58));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Enter amount:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 330, 250, 59));

        amt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                amtKeyPressed(evt);
            }
        });
        jPanel1.add(amt, new org.netbeans.lib.awtextra.AbsoluteConstraints(432, 346, 281, 40));

        rece_acc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rece_accKeyPressed(evt);
            }
        });
        jPanel1.add(rece_acc, new org.netbeans.lib.awtextra.AbsoluteConstraints(432, 218, 281, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Reciever's Account Number:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, 280, 58));

        send_acc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                send_accKeyPressed(evt);
            }
        });
        jPanel1.add(send_acc, new org.netbeans.lib.awtextra.AbsoluteConstraints(432, 154, 281, 40));

        getotp.setBackground(new java.awt.Color(167, 216, 255));
        getotp.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        getotp.setText("SUBMIT");
        getotp.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(31, 73, 146), 3, true));
        getotp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getotpActionPerformed(evt);
            }
        });
        jPanel1.add(getotp, new org.netbeans.lib.awtextra.AbsoluteConstraints(141, 451, 103, 40));

        back.setBackground(new java.awt.Color(167, 216, 255));
        back.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        back.setText("BACK");
        back.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(31, 73, 146), 3, true));
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });
        jPanel1.add(back, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 450, 103, 40));

        reset.setBackground(new java.awt.Color(167, 216, 255));
        reset.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        reset.setText("RESET");
        reset.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(31, 73, 146), 3, true));
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });
        jPanel1.add(reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 450, 100, 40));
        jPanel1.add(pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 283, 280, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 933, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
       Menu1 back=new Menu1(currentUser,con,service);
       back.setVisible(true);
       this.toBack();
       dispose();
    }//GEN-LAST:event_backActionPerformed

    private void getotpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getotpActionPerformed
        // TODO add your handling code here:
        // Tranfer button
        if(send_acc.getText().length()<=0 || rece_acc.getText().length()<=0 || pass.getText().length()<=0 || amt.getText().length()<=0 ){
            javax.swing.JOptionPane.showMessageDialog(null,"Textboxs not filled");
        }
        else{
        String send_acc_str = send_acc.getText();
        long send_acc = Long.parseLong(send_acc_str);
        String rece_acc_str = rece_acc.getText();
        long rece_acc = Long.parseLong(rece_acc_str);
        String pass_str = pass.getText();
//        pass.setText(null);

//        amt.setText(null);
        String amt_String = amt.getText();
//         long amt = Long.parseLong(amt_String);
        ModelUser fromUser = getCurrentUser();
        ModelUser toUser = null;
        try {
             toUser = service.getUserByAccountNumber((int) rece_acc);
         } catch (SQLException ex) {
             Logger.getLogger(transfer.class.getName()).log(Level.SEVERE, null, ex);
         }
        double amount = Double.parseDouble(amt.getText());

        try {
            service.transfer(fromUser, toUser, amount);
            JOptionPane.showMessageDialog(null, String.format("$%.2f transferred successfully to account number %d", amount, rece_acc), "Transfer Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Not Transfered");
        }
        }

    }//GEN-LAST:event_getotpActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        // reset
        send_acc.setText(null);
        rece_acc.setText(null);
        pass.setText(null);
        amt.setText(null);
    }//GEN-LAST:event_resetActionPerformed

    private void send_accKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_send_accKeyPressed
        // no string or special characters can be entered
        char c = evt.getKeyChar();
        if(!Character.isDigit(c) && !Character.isWhitespace(c) && !Character.isISOControl(c)) {
            send_acc.setEditable(false);
            JOptionPane.showMessageDialog(null, "Alphabets and Special characters are not allowed.");
            send_acc.setEditable(true);
        }
    }//GEN-LAST:event_send_accKeyPressed

    private void amtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_amtKeyPressed
       // no string or special characters can be entered
        char c = evt.getKeyChar();
        if(!Character.isDigit(c) && !Character.isWhitespace(c) && !Character.isISOControl(c)) {
            amt.setEditable(false);
            JOptionPane.showMessageDialog(null, "Alphabets and Special characters are not allowed.");
            amt.setEditable(true);
        }
    }//GEN-LAST:event_amtKeyPressed

    private void rece_accKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rece_accKeyPressed
        // no string or special characters can be entered
        char c = evt.getKeyChar();
        if(!Character.isDigit(c) && !Character.isWhitespace(c) && !Character.isISOControl(c)) {
            rece_acc.setEditable(false);
            JOptionPane.showMessageDialog(null, "Alphabets and Special characters are not allowed.");
            rece_acc.setEditable(true);
        }
    }//GEN-LAST:event_rece_accKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws ClassNotFoundException {
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
            java.util.logging.Logger.getLogger(transfer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transfer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transfer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transfer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
 try {
            DatabaseConnection.getInstance().connectToDatabase();
            System.out.println("Connection Succesfully to main page");
        } catch (SQLException e) {
 
            e.printStackTrace();
            System.out.println("Connection not Succesfully to main page");
        }
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new transfer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField amt;
    private javax.swing.JButton back;
    private javax.swing.JButton getotp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField pass;
    private javax.swing.JTextField rece_acc;
    private javax.swing.JButton reset;
    private javax.swing.JTextField send_acc;
    // End of variables declaration//GEN-END:variables
}
