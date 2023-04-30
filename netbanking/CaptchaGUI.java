package netbanking;
import com.raven.connection.DatabaseConnection;
import com.raven.model.ModelUser;
import com.raven.service.ServiceUser;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;
import javax.swing.*;

public class CaptchaGUI extends JFrame implements ActionListener {

    private JTextField captchaField;
    private JButton captchaButton;
    private JLabel captchaLabel;
    private String captchaString;
    private ModelUser currentUser;
    private ServiceUser service;
     private Connection con;
    public CaptchaGUI(ModelUser user, Connection con, ServiceUser service){
        this.currentUser = user;
        this.con = con;
        this.service = service;
    }

    public CaptchaGUI() {
        
        super("Captcha");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        captchaLabel = new JLabel();
        captchaLabel.setFont(new Font("Serif", Font.BOLD, 32));
        captchaLabel.setHorizontalAlignment(JLabel.CENTER);
        generateCaptcha();
        

        captchaField = new JTextField(10);

        captchaButton = new JButton("Verify");
        captchaButton.addActionListener(this);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(captchaLabel, BorderLayout.NORTH);
        panel.add(captchaField, BorderLayout.CENTER);
        panel.add(captchaButton, BorderLayout.SOUTH);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String input = captchaField.getText().toLowerCase();
        if (input.equals(captchaString.toLowerCase())) {
            JOptionPane.showMessageDialog(this, "Captcha verified");
            
            Checkbalance sb = new Checkbalance(currentUser,con,service);
            
            sb.setVisible(true);
            this.toBack();
            
        } else {
            JOptionPane.showMessageDialog(this, "Invalid captcha, please try again");
            captchaField.setText("");
            generateCaptcha();
        }
    }
    
    //actionlistner add for ok

    private void generateCaptcha() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append((char)(random.nextInt(26) + 'a'));
        }
        captchaString = sb.toString();
        captchaLabel.setText(captchaString.toUpperCase());
    }

    public static void main(String[] args) throws ClassNotFoundException {
         try {
            DatabaseConnection.getInstance().connectToDatabase();
            System.out.println("Connection Succesfully to main page");
        } catch (SQLException e) {
 
            e.printStackTrace();
            System.out.println("Connection not Succesfully to main page");
        }
        
        new CaptchaGUI();
    }
}
