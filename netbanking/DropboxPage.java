
package netbanking;

import com.raven.connection.DatabaseConnection;
import com.raven.model.ModelUser;
import com.raven.service.ServiceUser;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class DropboxPage extends JFrame {
    private JLabel titleLabel, messageLabel;
    private JList<String> checkList;
    private JButton claimButton;
    private DefaultListModel<String> checkListModel;
    private ModelUser user;
    private Connection con;
    private ModelUser currentUser;
    private ServiceUser service;
    
    
    public DropboxPage(){
        
    }
    
    public ModelUser getCurrentUser()
    {
        return currentUser;
    }

    public DropboxPage(ModelUser currentUser, Connection con,ServiceUser service) {
        this.currentUser = user;
        this.con = con;
        this.service = service;

        // Initialize UI components
        titleLabel = new JLabel("Dropbox");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        messageLabel = new JLabel("");
        checkListModel = new DefaultListModel<>();
        checkList = new JList<>(checkListModel);
        claimButton = new JButton("Claim");

        // Set layout and add components
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(titleLabel);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(checkList), BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(messageLabel);
        bottomPanel.add(claimButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Set frame properties
        setContentPane(mainPanel);
        setTitle("Dropbox" );
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        // Add listeners
        claimButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedCheck = checkList.getSelectedValue();
                if (selectedCheck == null) {
                    messageLabel.setText("Please select a check to claim");
                    return;
                }

//                try {
//                    // Parse selected check details
//                    String[] checkDetails = selectedCheck.split("\\|");
//                    int transactionId = Integer.parseInt(checkDetails[0].trim());
//                    double amount = Double.parseDouble(checkDetails[1].trim());
//
//                    // Claim the check
//                    PreparedStatement p = con.prepareStatement("UPDATE dropbox SET claimed = 1 WHERE transaction_id = ?");
//                    p.setInt(1, transactionId);
//                    int rowsUpdated = p.executeUpdate();
//                    if (rowsUpdated == 1) {
//                        messageLabel.setText(String.format("$%.2f claimed successfully", amount));
//
//                        // Deduct amount from sender's account
//                        PreparedStatement p2 = con.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE account_number = ?");
//                        p2.setDouble(1, amount);
//                        p2.setLong(2, user.getAccountNumber());
//                        p2.executeUpdate();
//                        p2.close();
//
//                        // Update balance label in main page
//                        PreparedStatement p3 = con.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_number = ?");
//                        p3.setDouble(1, amount);
//                        p3.setLong(2, user.getAccountNumber());
//                        p3.executeUpdate();
//                        p3.close();
//                    } else {
//                        messageLabel.setText("Failed to claim the check");
//                    }
//                } catch (SQLException ex) {
//                    messageLabel.setText("Error: " + ex.getMessage());
//                }
            }
        });

        // Load checks from dropbox
        loadChecks();
    }

    private void loadChecks() {
//        try {
//            PreparedStatement p = con.prepareStatement("SELECT * FROM dropbox WHERE receiver_bank_account_number = ? AND claimed = 0");
//            p.setLong(1, user.getAccountNumber());
//            ResultSet rs = p.executeQuery();
//
//            checkListModel.clear();
//            while (rs.next()) {
//                int transactionId = rs.getInt("transaction_id");
//                double amount = rs.getDouble("amount");
//                long senderAccountNumber = rs.getLong("sender_account_number");
//                int senderBankId = rs.getInt("sender_bank_id");
//
//                checkListModel.addElement(String.format("%d | $%.2f from %d at Bank %d", transactionId, amount, senderAccountNumber, senderBankId));
//            }
//            p.close();
//            } catch (SQLException ex) {
//            messageLabel.setText("Error: " + ex.getMessage());
//            }
        }
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
            java.util.logging.Logger.getLogger(send_cheque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(send_cheque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(send_cheque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(send_cheque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
// try {
//            DatabaseConnection.getInstance().connectToDatabase();
//            System.out.println("Connection Succesfully to main page");
//        } catch (SQLException e) {
// 
//            e.printStackTrace();
//            System.out.println("Connection not Succesfully to main page");
//        }
//        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DropboxPage().setVisible(true);
            }
        });
    }
    
};
