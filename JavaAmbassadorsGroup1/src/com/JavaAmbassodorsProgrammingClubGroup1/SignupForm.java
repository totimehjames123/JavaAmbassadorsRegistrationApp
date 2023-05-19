package com.JavaAmbassodorsProgrammingClubGroup1;

import com.mysql.cj.xdevapi.PreparableStatement;
import com.mysql.cj.xdevapi.Result;

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

public class SignupForm extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JTextField usernameTextField;
    private JTextField emailTextField;
    private JLabel logoImageLabel;
    private JPasswordField newPasswordField;
    private JPasswordField confirmNewPasswordField;
    private JButton signupButton;
    private JButton loginButton;
    private JComboBox genderComboBox;
    private JTextField phoneNumberTextField;

    SignupForm(){
        ImageIcon imageIcon = new ImageIcon("logo.png");
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(100, 80, image.SCALE_SMOOTH);
        ImageIcon newImageIcon = new ImageIcon(newImage);
        logoImageLabel.setIcon(newImageIcon);

        //Combo box - adding items to it
        String[] gender = {"Male", "Female", "Other"};
        for (int i = 0; i < gender.length; i++){
            genderComboBox.addItem(gender[i]);
        }

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

        //emailTextField
        emailTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (emailTextField.getText().length() >= 25) {
                    JOptionPane.showMessageDialog(null, "Maximum character limit reached (Takes up to 25 Characters)!", "Email length", JOptionPane.WARNING_MESSAGE);
                    e.consume(); // prevent further input
                }
            }
        });

        //phoneNumberTextField
        phoneNumberTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // prevent non-digit input
                } else {
                    String phoneNumber = phoneNumberTextField.getText();
                    if (phoneNumber.length() >= 10) {
                        JOptionPane.showMessageDialog(null, "Maximum character limit reached (Takes up to 10 Characters)!", "Password length", JOptionPane.WARNING_MESSAGE);
                        e.consume(); // prevent further input
                    }
                }
            }
        });


        //newPasswordField
        newPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (String.valueOf(newPasswordField.getPassword()).length() >= 25) {
                    JOptionPane.showMessageDialog(null, "Maximum character limit reached (Takes up to 25 Characters)!", "New password length", JOptionPane.WARNING_MESSAGE);
                    e.consume(); // prevent further input
                }
            }
        });

        //confirmNewPassword
        confirmNewPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (String.valueOf(confirmNewPasswordField.getPassword()).length() >= 25) {
                    JOptionPane.showMessageDialog(null, "Maximum character limit reached (Takes up to 25 Characters)!", "Confirm password length", JOptionPane.WARNING_MESSAGE);
                    e.consume(); // prevent further input
                }
            }
        });

        //Adding action listeners to buttons
        loginButton.addActionListener(this);
        signupButton.addActionListener(this);

        //Add main panel to frame
        this.add(mainPanel);

        //Creating a frame
        this.setVisible(true);
        this.setTitle("Java Ambassadors Group 1");
        this.setIconImage(new ImageIcon("logo.png").getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,710);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton){
            new LoginForm();
            this.setVisible(false);
        }

        if (e.getSource() == signupButton){
            try{
                // create a mysql database connection
                String myDriver = "com.mysql.cj.jdbc.Driver";
                String myUrl = "jdbc:mysql://localhost:3306/java_ambassadors_db";
                Class.forName(myDriver);
                Connection connection = DriverManager.getConnection(myUrl, "root", "");

                //Collecting and storing inputs into variables
                String username = usernameTextField.getText().trim();
                String email = emailTextField.getText().trim();
                String gender = genderComboBox.getSelectedItem().toString();
                String phoneNumber = phoneNumberTextField.getText().trim();
                String newPassword = String.valueOf(newPasswordField.getPassword());
                String confirmNewPassword = String.valueOf(confirmNewPasswordField.getPassword());


                try {
                    if (username.isEmpty()){
                        JOptionPane.showMessageDialog(this, "Username field is required!", "Username", JOptionPane.WARNING_MESSAGE);
                    }else{
                        PreparedStatement usernameStatement = connection.prepareStatement("SELECT * FROM signup WHERE username = ?");
                        usernameStatement.setString(1, username);
                        ResultSet usernameResult = usernameStatement.executeQuery();
                        if (usernameResult.next()) {
                            JOptionPane.showMessageDialog(this, "Username already exists!", "Username", JOptionPane.WARNING_MESSAGE);
                        } else {

                            //Setting conditions for email
                            try {
                                if (email.isEmpty()){
                                    JOptionPane.showMessageDialog(this, "Email field is required!", "Email", JOptionPane.WARNING_MESSAGE);
                                }
                                else{
                                    if (phoneNumber.isEmpty()){
                                        JOptionPane.showMessageDialog(this, "Phone number field is required", "Phone Number", JOptionPane.WARNING_MESSAGE);
                                    }
                                    else if (phoneNumber.length() < 10) {
                                        JOptionPane.showMessageDialog(this, "Phone number must contain 10 digits!", "Phone Number", JOptionPane.WARNING_MESSAGE);
                                    }
                                    else{
                                        if (newPassword.isEmpty()){
                                            JOptionPane.showMessageDialog(this, "New password field is required!", "New Password", JOptionPane.WARNING_MESSAGE);
                                        }
                                        else{
                                            if (confirmNewPassword.isEmpty()){
                                                JOptionPane.showMessageDialog(this, "Confirm password field is required!", "Confirm Password", JOptionPane.WARNING_MESSAGE);
                                            }
                                            else{
                                                if (gender.isEmpty()){
                                                    JOptionPane.showMessageDialog(this, "Gender field is required!", "Gender", JOptionPane.WARNING_MESSAGE);
                                                }
                                                else{
                                                    if (newPassword.equals(confirmNewPassword)){
                                                        if (newPassword.length() >= 6){
                                                            try {
                                                                String sql = "INSERT INTO signup (username, email, phoneNumber, gender, password) VALUES (?, ?, ?, ?, ?)";
                                                                PreparedStatement statement = connection.prepareStatement(sql);
                                                                statement.setString(1, username);
                                                                statement.setString(2, email);
                                                                statement.setString(3, phoneNumber);
                                                                statement.setString(4, gender);
                                                                statement.setString(5, newPassword);
                                                                int rowsInserted = statement.executeUpdate();
                                                                if (rowsInserted > 0) {
                                                                    usernameTextField.setText("");
                                                                    emailTextField.setText("");
                                                                    phoneNumberTextField.setText("");
                                                                    newPasswordField.setText("");
                                                                    confirmNewPasswordField.setText("");
                                                                    JOptionPane.showMessageDialog(this, "Congratulation, You've successfully created an account!", "Signup success", JOptionPane.INFORMATION_MESSAGE);
                                                                    this.setVisible(false);
                                                                    new LoginForm();
                                                                }
                                                            }
                                                            catch (Exception exception) {
                                                                JOptionPane.showMessageDialog(this, "Failed to summit form data!", "Submission Failed", JOptionPane.WARNING_MESSAGE);
                                                            }
                                                        }
                                                        else {
                                                            JOptionPane.showMessageDialog(this, "Password must contain up to 6 to 25 characters!", "Password", JOptionPane.WARNING_MESSAGE);
                                                        }
                                                    }
                                                    else{
                                                        JOptionPane.showMessageDialog(this, "Both passwords do not match!", "Passwords equality", JOptionPane.WARNING_MESSAGE);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            catch (Exception exception){
                                JOptionPane.showMessageDialog(this, "Invalid input!", "Invalid input", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    }


                }
                catch (Exception exception){
                    JOptionPane.showMessageDialog(this, "Failed to submit form data!", "Submission Failed", JOptionPane.WARNING_MESSAGE);
                }




            }
            catch (Exception exception){
                JOptionPane.showMessageDialog(this, "Failed to connect to the server. Please try again later!", "Server connectivity", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
