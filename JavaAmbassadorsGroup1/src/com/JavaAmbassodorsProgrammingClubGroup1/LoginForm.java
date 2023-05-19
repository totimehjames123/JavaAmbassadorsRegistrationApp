package com.JavaAmbassodorsProgrammingClubGroup1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginForm extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createNewAccountButton;
    private JLabel logoImageLabel;


    LoginForm(){
        //Logo Image Label

        ImageIcon imageIcon = new ImageIcon("logo.png");
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(100, 100, image.SCALE_SMOOTH);
        ImageIcon newImageIcon = new ImageIcon(newImage);
        logoImageLabel.setIcon(newImageIcon);

        //INPUT LENGTH SETTINGS
        //usernameTextField
        usernameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (usernameTextField.getText().length() >= 25){
                    JOptionPane.showMessageDialog(null, "Maximum character limit reached (Takes up to 25 Characters)!", "Username length", JOptionPane.WARNING_MESSAGE);
                    e.consume(); // prevent further input
                }
            }
        });

        //passwordField
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (String.valueOf(passwordField.getPassword()).length() >= 25) {
                    JOptionPane.showMessageDialog(null, "Maximum character limit reached (Takes up to 25 Characters)!", "Password length", JOptionPane.WARNING_MESSAGE);
                    e.consume(); // prevent further input
                }
            }
        });

        //Adding action listeners to buttons
        loginButton.addActionListener(this);
        createNewAccountButton.addActionListener(this);

        //Add main panel to frame
        this.add(mainPanel);

        //Creating a frame
        this.setVisible(true);
        this.setTitle("Java Ambassadors Group 1");
        this.setIconImage(new ImageIcon("logo.png").getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createNewAccountButton){
            new SignupForm();
            this.setVisible(false);
        }
        if (e.getSource() == loginButton){
            try {
                // create a mysql database connection
                String myDriver = "com.mysql.cj.jdbc.Driver";
                String myUrl = "jdbc:mysql://localhost:3306/java_ambassadors_db";
                Class.forName(myDriver);
                Connection connection = DriverManager.getConnection(myUrl, "root", "");

                //Collecting and storing inputs into variables
                String username = usernameTextField.getText().trim();
                String password = String.valueOf(passwordField.getPassword());

                try{
                    if (username.isEmpty()){
                        JOptionPane.showMessageDialog(this, "Username field is required!", "Username", JOptionPane.WARNING_MESSAGE);
                    }
                    else{
                        PreparedStatement usernameStatement = connection.prepareStatement("SELECT * FROM signup WHERE username = ? AND password = ?");
                        usernameStatement.setString(1, username);
                        usernameStatement.setString(2, password);
                        ResultSet usernameResult = usernameStatement.executeQuery();

                        if (password.isEmpty()){
                            JOptionPane.showMessageDialog(this, "Password field is required!", "Password", JOptionPane.WARNING_MESSAGE);
                        }
                        else{
                            if (!usernameResult.next()) {
                                JOptionPane.showMessageDialog(this, "Invalid username or password!", "Invalid Login Credentials", JOptionPane.WARNING_MESSAGE);
                            }
                            else{
                                new RegisteredMembersPage();
                                this.dispose();
                            }
                        }

                    }
                }
                catch (Exception exception){

                }

            }
            catch (Exception exception){
                JOptionPane.showMessageDialog(this, "Failed to connect to the server. Please try again later!", "Server connectivity", JOptionPane.WARNING_MESSAGE);

            }
        }
    }

}
