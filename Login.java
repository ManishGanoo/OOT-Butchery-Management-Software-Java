package GUI;

import BackEnd.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.awt.event.ActionEvent;


public class Login extends JLabel{

    private JTextField txtUsername = new JTextField(15);
    private JPasswordField txtPassword = new JPasswordField(15);

    private JLabel lblUsername = new JLabel("Username");
    private JLabel lblPassword = new JLabel("Password");

    private JButton btnLogin = new JButton("Login");
    private JButton btnCancel = new JButton("Cancel");

    private Users user;
    private String password;
    private String msg = " ";
    private ID userID = new ID();



    public Login() {

        Icon icon=new ImageIcon(getClass().getResource("login"));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setPreferredSize(screenSize);

        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.PAGE_START;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(200,200,30,-50);
        lblUsername.setForeground(new Color(150, 0, 61));
        lblPassword.setForeground(new Color(150, 0, 61));
        add(lblUsername,gbc);

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.ipady = 10;
        gbc.insets = new Insets(200,0,30,0);
        add(txtUsername,gbc);

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0,200,0,-50);
        add(lblPassword,gbc);

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(0,0,0,0);
        add(txtPassword,gbc);

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.ipadx =50;
        gbc.insets = new Insets(50,350,0,0);
        add(btnLogin,gbc);

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(50,0,0,100);
        add(btnCancel,gbc);


        btnLogin.addActionListener(new LoginListener());
        btnCancel.addActionListener(new CancelListener());


        lblUsername.setFont(new Font("Helvetica", Font.BOLD, 22));
        lblPassword.setFont(new Font("Helvetica", Font.BOLD, 22));
        txtUsername.setFont(new Font("Helvetica", Font.BOLD, 22));
        txtPassword.setFont(new Font("Helvetica", Font.BOLD, 22));

        btnLogin.setForeground(Color.darkGray);
        btnCancel.setForeground(Color.darkGray);

        setIcon(icon);
    }


    public class LoginListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent event){
            try{
                user=userID.searchUsers("SELECT Name,l_password\n" +
                        "FROM tblUser\n" +
                        "WHERE Name='"+txtUsername.getText()+"'");

                password = String.valueOf(txtPassword.getPassword());

                if(user.getName().equals(txtUsername.getText())){
                    if(user.getPassword().equals(password)){
                        msg="LOGIN SUCCESSFUL";
                        Nav.changePanel(Page.HOME);
                    }else{
                        msg = "Login Denied";
                        PopUp.DisplayMessage(new JFrame(),msg,"LOGIN",JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    msg = "Login Denied";
                    PopUp.DisplayMessage(new JFrame(),msg,"LOGIN",JOptionPane.ERROR_MESSAGE);
                }
            }
            catch(NullPointerException e){}
        }
    }

    public class CancelListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent event){
            fCancel();
        }
    }

    public void fCancel(){
        txtUsername.setText("");
        txtPassword.setText("");
        txtUsername.requestFocus();
    }
}