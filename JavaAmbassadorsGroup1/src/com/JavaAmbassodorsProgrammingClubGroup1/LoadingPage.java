package com.JavaAmbassodorsProgrammingClubGroup1;


import javax.swing.*;
import java.awt.*;
import java.sql.*;


public class LoadingPage extends JFrame {

    Statement stmt = null;
    Connection conn = null;
    public LoadingPage() {
        super("Java Ambassadors - Group One");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.white);
        this.setIconImage(new ImageIcon("logo.png").getImage());

        // Create the components
        ImageIcon image = new ImageIcon("logo.png");
        JLabel label = new JLabel(image);

        // Set up the layout
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.fill = GridBagConstraints.CENTER;
        add(label, gridBagConstraints);

        // Show the window
        this.setVisible(true);

        // Wait for 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Create database
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String databaseName = "java_ambassadors_db";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '"+databaseName+"'");
            if(!rs.next()) {
                stmt.executeUpdate("CREATE DATABASE " + databaseName);
                System.out.println("Database created successfully.");

                String url1 = "jdbc:mysql://localhost/java_ambassadors_db";
                String user1 = "root";
                String password1 = "";

                try (Connection conn1 = DriverManager.getConnection(url1, user1, password1);
                     Statement stmt1 = conn1.createStatement()) {

                    String sql = "CREATE TABLE signup (" +
                            "registrationID INT AUTO_INCREMENT PRIMARY KEY, " +
                            "username VARCHAR(25) UNIQUE, " +
                            "email VARCHAR(50), " +
                            "phoneNumber VARCHAR(20), " +
                            "gender VARCHAR(10), " +
                            "password VARCHAR(50), " +
                            "registrationDateTime DATETIME DEFAULT CURRENT_TIMESTAMP)";

                    stmt1.executeUpdate(sql);
                    System.out.println("Table created successfully");

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Database already exists.");

            }
        } catch (SQLException e) {
                int result = JOptionPane.showOptionDialog(null, "Failed to connect to the server!",
                    "Server connectivity", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.ERROR_MESSAGE, null,
                    new String[]{"Try Again", "Cancel"}, "default");

            if (result == JOptionPane.YES_OPTION) {
                setVisible(false);
                new LoadingPage();
            }
            else{
                System.exit(0);
            }
        }

        //Create a signup table inside the database


        // Close the window and open the next page
        this.dispose();
        new LoginForm();

    }

    public static void main(String[] args) {
        new LoadingPage();
    }
}
