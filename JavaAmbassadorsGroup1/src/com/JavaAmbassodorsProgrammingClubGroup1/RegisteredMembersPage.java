package com.JavaAmbassodorsProgrammingClubGroup1;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.Vector;

public class RegisteredMembersPage extends JFrame implements ActionListener{

    private static JTable table;
    private JScrollPane scrollPane;
    private JButton deleteButton;
    private JButton searchButton;
    private JPanel mainPanel;
    private JTextField searchField;
    private JPanel headerPanel;
    private JPanel titlePanel;
    private JPanel delete;
    private JLabel logoLabel;
    private JPanel tablePanel;
    private JButton refreshButton;
    private JButton logoutButton;




    Connection conn;
    Statement statement;
    ResultSet resultSet;



    RegisteredMembersPage() throws SQLException {
        //JMENU
        JMenuBar menuBar = new JMenuBar();

        //Creating menu items
        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");

        //Adding MenuItems to file menu
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(exitMenuItem);

        //Adding about menu item to help menu
        JMenuItem aboutMenuItem = new JMenuItem("About");
        helpMenu.add(aboutMenuItem);

        //Adding menus to menu bar
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        //Adding menus to menu bar
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        //Adding mnemonics to file menu
        fileMenu.setMnemonic(KeyEvent.VK_F);

        //Adding accelerators to all menu items in file menu
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));

        //Adding mnemonics to help menu
        helpMenu.setMnemonic(KeyEvent.VK_H);

        //Adding accelerators to about menu item
        aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));

        //Adding event listener to new menu item
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignupForm signupForm = new SignupForm();
                signupForm.setDefaultCloseOperation(HIDE_ON_CLOSE);
            }
        });


        //Adding event listener to open menu item
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog();
                dialog.setVisible(true);
                dialog.setSize(getToolkit().getScreenSize());
                dialog.add(tablePanel);
                dialog.setTitle("Registered Members of Java Ambassadors 2023");
            }
        });

        //Adding event listener to exit menu
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //Adding event listener to about menu
        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create the JTable with the column names and data
                String[] columnNames = {"Names of Group Members", "Index Numbers"};
                Object[][] data = {
                        {"Totimeh James Tetteh", "01210232D"},
                        {"Quarshie Dela Emmanuel", "01210295D"},
                        {"Appiah Boateng Meshack", "01212047D"},
                        {"David Godson", "01213930D"},
                        {"Sarah Marfo", "01213622D"},
                        {"Abubakar Mahmoud Mohammed", "01213226D"}
                };
                JTable table = new JTable(data, columnNames);
                table.setSelectionBackground(Color.LIGHT_GRAY);

                JTableHeader header = table.getTableHeader();
                header.setPreferredSize(new Dimension(header.getPreferredSize().width, 20));
                header.setBackground(Color.black);
                header.setForeground(Color.white);

                // Create the title label and set its text
                JLabel titleLabel = new JLabel("OBJECT ORIENTED SOFTWARE DEVELOPMENT");
//                titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

                titleLabel.setForeground(Color.white);

                // Create the label below the title and set its text
                JLabel javaLabel = new JLabel("Java II");
                javaLabel.setForeground(Color.white);
                javaLabel.setFont(new Font("Consolas", Font.BOLD, 20));

                //Label for academic year
                JLabel academicYearLabel = new JLabel("2023 Academic Year");
                academicYearLabel.setForeground(Color.white);
                academicYearLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                academicYearLabel.setIconTextGap(5);

                //about label
                JLabel aboutLabel = new JLabel("copyright information © ");
                aboutLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
                aboutLabel.setForeground(Color.white);

                // Create a JPanel with a vertical BoxLayout and add the labels to it
                JPanel labelPanel = new JPanel();
                labelPanel.setBackground(Color.black);
                labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

                labelPanel.add(aboutLabel);
                labelPanel.add(javaLabel);
                labelPanel.add(titleLabel);
                labelPanel.add(academicYearLabel);


                JWindow window = new JWindow();
                // Create the cancel button and add an ActionListener to handle button clicks
                JButton cancelButton = new JButton("Cancel");
                cancelButton.setBackground(Color.black);
                cancelButton.setForeground(Color.white);
                cancelButton.setPreferredSize(new Dimension(80, 35));
                cancelButton.setBorder(BorderFactory.createLineBorder(Color.white, 3));
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Handle button click event
                        window.dispose();
                    }
                });

                // Create a JPanel for the cancel button and add it to the window's content pane
                JPanel buttonPanel = new JPanel();
                buttonPanel.add(cancelButton);
                buttonPanel.setBackground(Color.black);

                // Create the JWindow and add the components to it
                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.getViewport().setBackground(Color.white);

                window.setLocation(500, 210);

                window.getContentPane().add(labelPanel, BorderLayout.NORTH);
                window.getContentPane().add(scrollPane, BorderLayout.CENTER);
                window.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

                // Set the size and show the window
                window.setSize(400, 300);
                window.setVisible(true);

            }
        });


        // Create a connection to your database using JDBC
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_ambassadors_db", "root", "");

        // Execute a SELECT statement to retrieve the data you want from your table
        statement = conn.createStatement();
        resultSet = statement.executeQuery("SELECT username, email, phoneNumber, gender, registrationDateTime FROM signup");


        // Store the data in a Vector object
        Vector<Vector<String>> data = new Vector<Vector<String>>();
        Vector<String> row;
        while (resultSet.next()) {
            row = new Vector<String>();
            row.add(resultSet.getString("username"));
            row.add(resultSet.getString("email"));
            row.add(resultSet.getString("phoneNumber"));
            row.add(resultSet.getString("gender"));
            row.add(resultSet.getString("registrationDateTime"));
            data.add(row);
        }

        // Create a JTable object with the data stored in the Vector
        Vector<String> columnNames = new Vector<String>();
        columnNames.add("Username");
        columnNames.add("Email");
        columnNames.add("Phone Number");
        columnNames.add("Gender");
        columnNames.add("Time of Registration");
        table = new JTable(data, columnNames);
        table.setBackground(Color.WHITE);
        // Add a JScrollPane to your JTable object so that it can be scrolled if there are too many rows to fit on the screen
        scrollPane = new JScrollPane(table);

        // Create a custom cell renderer with a white background color
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBackground(Color.WHITE);

        // Set the cell renderer to the table
        table.setDefaultRenderer(Object.class, renderer);
//
        // Add a "Delete" button to your frame and add an ActionListener to it that will delete the selected row(s) from your table and database
        deleteButton.setBorder(BorderFactory.createEmptyBorder());
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    if (table.getRowCount() == 0){
                        JOptionPane.showMessageDialog(null, "As of now, there have been no individuals registered!", "0 registered members", JOptionPane.WARNING_MESSAGE);
                    }
                    else{
                        int numberOfRowsSelected = 0;
                        int[] selectedRows = table.getSelectedRows();
                        System.out.println(selectedRows.length);
                        if (selectedRows.length == 0){
                            JOptionPane.showMessageDialog(
                                    null, "You must select at least a row", "0 rows selected", JOptionPane.WARNING_MESSAGE);
                        }
                        else{
                            numberOfRowsSelected = selectedRows.length;
                        }
                        DefaultTableModel model = (DefaultTableModel) table.getModel();


                        Boolean isDeleted = false;
                        for (int i : selectedRows) {
                            String username = (String) model.getValueAt(i, 0);
                            System.out.println(username);

                            PreparedStatement statement = conn.prepareStatement("DELETE FROM signup WHERE username = ?");
                            statement.setString(1, username);
                            statement.executeUpdate();
                            isDeleted = true;
                        }

                        if (isDeleted == true){
                            if (numberOfRowsSelected == 1){
                                JOptionPane.showMessageDialog(null,   numberOfRowsSelected+" row deleted successfully!", "Delete", JOptionPane.INFORMATION_MESSAGE);
                                setVisible(false);
                                new RegisteredMembersPage();
                            }
                            else{
                                JOptionPane.showMessageDialog(null,   numberOfRowsSelected+" rows deleted successfully!", "Delete", JOptionPane.INFORMATION_MESSAGE);
                                setVisible(false);
                                new RegisteredMembersPage();
                            }

                        }
                    }

                }
                catch (Exception exception){
                    System.out.println(exception.getMessage());
                }

            }
        });

        // Add a "Search" button to your frame and add an ActionListener to it that will search through your table for rows that match the search criteria
        searchButton.addActionListener(this);
        searchField.addActionListener(this);

        //Logo label
        ImageIcon imageIcon = new ImageIcon("logo.png");
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(70, 70, image.SCALE_SMOOTH);
        ImageIcon newImageIcon = new ImageIcon(newImage);
        logoLabel.setIcon(newImageIcon);

        // Set up your JFrame with all of these components
        this.setJMenuBar(menuBar);
        this.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.white);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        refreshButton.addActionListener(this);
        logoutButton.addActionListener(this);

        this.add(mainPanel);
        // Set the height of the table header to 40 pixels
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
        header.setBackground(Color.black);
        header.setForeground(Color.white);
        tablePanel.add(scrollPane);
        table.setSelectionBackground(Color.LIGHT_GRAY);
        table.setRowHeight(30);
        tablePanel.setVisible(true);
        table.setBackground(new Color(200, 20, 20));

        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton){
            try{
                String username = searchField.getText();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                TableRowSorter<DefaultTableModel> searchItem = new TableRowSorter<>(model);
                table.setRowSorter(searchItem);
                searchItem.setRowFilter(RowFilter.regexFilter(username));

            }
            catch (Exception exception){
                System.out.println("couldn't search");
            }

        } else if (e.getSource() == searchField) {
            try{
                String username = searchField.getText();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                TableRowSorter<DefaultTableModel> searchItem = new TableRowSorter<>(model);
                table.setRowSorter(searchItem);

                searchItem.setRowFilter(RowFilter.regexFilter(username));
            }
            catch (Exception exception){
                System.out.println("couldn't search");
            }
        }

        if (e.getSource() == refreshButton){
            try{
                setVisible(false);
                new RegisteredMembersPage();
            }
            catch (Exception exception){
                JOptionPane.showMessageDialog(this, "Couldn't refresh this page.");
            }
        }

        if (e.getSource() == logoutButton){
            this.dispose();
            new LoginForm();
        }
    }


}
