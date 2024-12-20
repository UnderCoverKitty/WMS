
import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ACLC PC17
 */
public class ManageProduct extends javax.swing.JFrame {

    private int productPk = 0;
    private String currentUserRole;
    private String currentUserName;
    private ManageOrder manageOrder;
    /**
     * Creates new form ManageProduct
     */
    public ManageProduct(ManageOrder order) {
    this.manageOrder = order;
    initComponents();
    refresh();
}

    public ManageProduct() {
        
        initComponents();
        String role = null;
        setLocationRelativeTo(null);
        populateCategoryComboBox();
        currentUserRole = role;
        this.currentUserRole = role;
        refresh();
       

        DefaultTableModel model = (DefaultTableModel) tableProduct.getModel();
        tableProduct.setModel(new DefaultTableModel(model.getDataVector(), getColumnNames(model)) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Disable editing for all cells
            }
        });
    }

    private Vector<String> getColumnNames(DefaultTableModel model) {
        Vector<String> columnNames = new Vector<>();
        for (int i = 0; i < model.getColumnCount(); i++) {
            columnNames.add(model.getColumnName(i));
        }
        return columnNames;
    }

    private void refresh() {
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tableProduct.getModel();
        model.setRowCount(0);

        try {
            // Establish database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventorymanagement", "root", "");

            // Execute the query to get all products
            String query = "SELECT * FROM product";
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            // Loop through the result set and add each product to the table
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String quantity = rs.getString("quantity");
                String price = rs.getString("price");
                String description = rs.getString("description");
                String category = rs.getString("category");

                // Add row to the table
                model.addRow(new Object[]{id, name, quantity, price, description, category});
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to refresh product list: " + e.getMessage());
        }
    }

    public ManageProduct(String role, String names) {
        initComponents();
        setLocationRelativeTo(null);
        this.currentUserRole = role;
        this.currentUserName = names;
        currentUserRole = role;  // Set the role
        populateCategoryComboBox();
        refresh();  // Refresh data when the form is initialized
    }

    private boolean validateFields() {
        if (!name.getText().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    DefaultTableModel m;

    private void populateCategoryComboBox() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventorymanagement", "root", "");
            ps = con.prepareStatement("SELECT DISTINCT name FROM category");
            rs = ps.executeQuery();
            cat.addItem("All");
            cat.removeAllItems();  // Clear existing items
            while (rs.next()) {
                cat.addItem(rs.getString("name"));  // Add category names to combo box

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void formComponentShown(java.awt.event.ComponentEvent evt) {

        DefaultTableModel model = (DefaultTableModel) tableProduct.getModel();

        try {
//          Connection con = ConnectionProvider.getCon();
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventorymanagement", "root", "");
            ps = con.prepareStatement("Select * from category where userrole = ?");
            ps.executeQuery("Select * from category");
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("category_pk"), rs.getString("name")});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        update.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        save = new javax.swing.JButton();
        update = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        close = new javax.swing.JButton();
        name = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableProduct = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        quan = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        price = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cat = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        desc = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        searchbar = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        BG5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(950, 520));
        setUndecorated(true);
        getContentPane().setLayout(null);

        jPanel3.setBackground(new java.awt.Color(163, 198, 134));
        getContentPane().add(jPanel3);
        jPanel3.setBounds(0, 0, 950, 10);

        jPanel8.setBackground(new java.awt.Color(41, 88, 82));
        getContentPane().add(jPanel8);
        jPanel8.setBounds(0, 510, 950, 10);

        jPanel2.setBackground(new java.awt.Color(41, 88, 82));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Impact", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(242, 242, 242));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("MANAGE PRODUCT:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 5, -1, 70));

        jPanel4.setBackground(new java.awt.Color(163, 198, 134));
        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 0, 20, 20));

        jPanel5.setBackground(new java.awt.Color(163, 198, 134));
        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 60, 20, 10));

        jPanel6.setBackground(new java.awt.Color(163, 198, 134));
        jPanel2.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 20, -1));

        jPanel7.setBackground(new java.awt.Color(163, 198, 134));
        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 20, 20));

        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 0, 950, 70);

        jLabel2.setFont(new java.awt.Font("Javanese Text", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 29, 29));
        jLabel2.setText("NAME:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(490, 140, 51, 20);

        save.setBackground(new java.awt.Color(0, 29, 29));
        save.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        save.setForeground(new java.awt.Color(255, 255, 255));
        save.setText("SAVE");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        getContentPane().add(save);
        save.setBounds(490, 450, 80, 40);

        update.setBackground(new java.awt.Color(0, 29, 29));
        update.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        update.setForeground(new java.awt.Color(255, 255, 255));
        update.setText("UPDATE");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });
        getContentPane().add(update);
        update.setBounds(610, 450, 80, 40);

        delete.setBackground(new java.awt.Color(0, 29, 29));
        delete.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        delete.setForeground(new java.awt.Color(255, 255, 255));
        delete.setText("DELETE");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
        getContentPane().add(delete);
        delete.setBounds(730, 450, 80, 40);

        close.setBackground(new java.awt.Color(0, 29, 29));
        close.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        close.setForeground(new java.awt.Color(255, 255, 255));
        close.setText("CLOSE");
        close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeActionPerformed(evt);
            }
        });
        getContentPane().add(close);
        close.setBounds(850, 450, 80, 40);

        name.setBackground(new java.awt.Color(254, 254, 254));
        name.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 88, 82), 2, true));
        name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameActionPerformed(evt);
            }
        });
        name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nameKeyTyped(evt);
            }
        });
        getContentPane().add(name);
        name.setBounds(490, 160, 430, 30);

        tableProduct.setBackground(new java.awt.Color(254, 254, 254));
        tableProduct.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tableProduct.setForeground(new java.awt.Color(41, 88, 82));
        tableProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Quantity", "Price", "Description", "Category"
            }
        ));
        tableProduct.setToolTipText("");
        tableProduct.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tableProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableProductMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableProduct);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(30, 90, 440, 400);

        jLabel3.setFont(new java.awt.Font("Javanese Text", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 29, 29));
        jLabel3.setText("QUANTITY:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(490, 200, 90, 20);

        quan.setBackground(new java.awt.Color(254, 254, 254));
        quan.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 88, 82), 2, true));
        quan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quanActionPerformed(evt);
            }
        });
        quan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                quanKeyTyped(evt);
            }
        });
        getContentPane().add(quan);
        quan.setBounds(490, 220, 430, 30);

        jLabel4.setFont(new java.awt.Font("Javanese Text", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 29, 29));
        jLabel4.setText("PRICE:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(490, 260, 80, 20);

        price.setBackground(new java.awt.Color(254, 254, 254));
        price.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 88, 82), 2, true));
        price.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priceActionPerformed(evt);
            }
        });
        price.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                priceKeyTyped(evt);
            }
        });
        getContentPane().add(price);
        price.setBounds(490, 280, 430, 30);

        jLabel5.setFont(new java.awt.Font("Javanese Text", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 29, 29));
        jLabel5.setText("DESCRIPTION:");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(490, 320, 120, 20);

        cat.setBackground(new java.awt.Color(254, 254, 254));
        cat.setForeground(new java.awt.Color(227, 186, 24));
        cat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- - " }));
        cat.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 88, 82), 2, true));
        cat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catActionPerformed(evt);
            }
        });
        getContentPane().add(cat);
        cat.setBounds(490, 400, 430, 30);

        jLabel6.setFont(new java.awt.Font("Javanese Text", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 29, 29));
        jLabel6.setText("CATEGORY:");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(490, 380, 120, 20);

        desc.setBackground(new java.awt.Color(254, 254, 254));
        desc.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 88, 82), 2, true));
        desc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descActionPerformed(evt);
            }
        });
        getContentPane().add(desc);
        desc.setBounds(490, 340, 430, 30);

        jLabel7.setFont(new java.awt.Font("Javanese Text", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/magnifying-glass.png"))); // NOI18N
        getContentPane().add(jLabel7);
        jLabel7.setBounds(490, 90, 30, 30);

        searchbar.setBackground(new java.awt.Color(254, 254, 254));
        searchbar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 88, 82), 2, true));
        searchbar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchbarActionPerformed(evt);
            }
        });
        searchbar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchbarKeyTyped(evt);
            }
        });
        getContentPane().add(searchbar);
        searchbar.setBounds(520, 90, 400, 30);

        jPanel1.setBackground(new java.awt.Color(163, 198, 134));
        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 950, 520);

        BG5.setBackground(new java.awt.Color(153, 153, 153));
        BG5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/MO.png"))); // NOI18N
        getContentPane().add(BG5);
        BG5.setBounds(0, 0, 1172, 796);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        // Your existing save logic...
    String names = name.getText();
    String quans = quan.getText();
    String prices = price.getText();
    String descs = desc.getText();
    String categorys = cat.getSelectedItem().toString();

    if (names.isEmpty() || quans.isEmpty() || prices.isEmpty() || descs.isEmpty()) {
        JOptionPane.showMessageDialog(null, "All fields are required.");
        return;
    }

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventorymanagement", "root", "");

        String insertQuery = "INSERT INTO product (name, quantity, price, description, category) VALUES (?, ?, ?, ?, ?)";
        ps = con.prepareStatement(insertQuery);
        ps.setString(1, names);
        ps.setString(2, quans);
        ps.setString(3, prices);
        ps.setString(4, descs);
        ps.setString(5, categorys);

        int rowsAffected = ps.executeUpdate(); // Execute the insert query
        if (rowsAffected > 0) {  // Check if the product was added successfully
            JOptionPane.showMessageDialog(null, "Product Added Successfully!");

            // If ManageOrder is passed, call the refresh method to update the product list in ManageOrder
            if (manageOrder != null) {
                manageOrder.refresh();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Failed to add product.");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    }
    }//GEN-LAST:event_saveActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        // Check if a row is selected
        int selectedRow = tableProduct.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a product to update.");
            return;
        }

        // Retrieve current product details from the table
        String productId = tableProduct.getValueAt(selectedRow, 0).toString(); // Use product ID
        String currentName = tableProduct.getValueAt(selectedRow, 1).toString();
        String currentQuan = tableProduct.getValueAt(selectedRow, 2).toString();
        String currentPrice = tableProduct.getValueAt(selectedRow, 3).toString();
        String currentDesc = tableProduct.getValueAt(selectedRow, 4).toString();
        String currentCategory = tableProduct.getValueAt(selectedRow, 5).toString();

        // Retrieve new product details from input fields
        String names = name.getText();
        String quans = quan.getText();
        String prices = price.getText();
        String descs = desc.getText();
        String categorys = cat.getSelectedItem().toString();

        // Validate fields
        if (validateFields()) {
            JOptionPane.showMessageDialog(null, "All fields are required.");
            return;
        }

        // Check if any field has changed
        if (names.equals(currentName) && quans.equals(currentQuan) && prices.equals(currentPrice)
                && descs.equals(currentDesc) && categorys.equals(currentCategory)) {
            JOptionPane.showMessageDialog(null, "No changes detected. Please modify at least one field to update.");
            return;

        }

        try {
            // Establish database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventorymanagement", "root", "");

            // Prepare the update query
            ps = con.prepareStatement(
                    "UPDATE product SET name = ?, quantity = ?, price = ?, description = ?, category = ? WHERE id = ?"
            );
            ps.setString(1, names);
            ps.setString(2, quans);
            ps.setString(3, prices);
            ps.setString(4, descs);
            ps.setString(5, categorys);
            ps.setString(6, productId);

            // Execute the update
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Product updated successfully!");

                // Update the table with new values
                tableProduct.setValueAt(names, selectedRow, 1);
                tableProduct.setValueAt(quans, selectedRow, 2);
                tableProduct.setValueAt(prices, selectedRow, 3);
                tableProduct.setValueAt(descs, selectedRow, 4);
                tableProduct.setValueAt(categorys, selectedRow, 5);

                // Clear the input fields after update
                clearFields();
            } else {
                JOptionPane.showMessageDialog(null, "Product update failed.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_updateActionPerformed

    // Method to clear the text fields
    private void clearFields() {
        name.setText("");
        quan.setText("");
        price.setText("");
        desc.setText("");
        cat.setSelectedIndex(0);  // Reset combo box selection (optional)
    }
    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        // TODO add your handling code here:
        /*DefaultTableModel model = (DefaultTableModel) tableProduct.getModel();
        model.setRowCount(0);
        
        try {
        // Establish database connection
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventorymanagement", "root", "");
        
        // Execute the query to get all products
        String query = "SELECT * FROM product";
        ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        
        // Loop through the result set and add each product to the table
        while (rs.next()) {
        String id = rs.getString("id");
        String name = rs.getString("name");
        String quantity = rs.getString("quantity");
        String price = rs.getString("price");
        String description = rs.getString("description");
        String category = rs.getString("category");
        
        // Add row to the table
        model.addRow(new Object[]{id, name, quantity, price, description, category});
        }
        
        } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Failed to refresh product list: " + e.getMessage());
        }*/
        int selectedRow = tableProduct.getSelectedRow(); // Get the selected row index
        if (selectedRow == -1) {
            // No row is selected
            JOptionPane.showMessageDialog(this, "Please select a product to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirm deletion
        int confirmation = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete the selected product?",
                "Delete Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                // Get the product ID from the selected row
                String productId = tableProduct.getValueAt(selectedRow, 0).toString();
                System.out.println("Product ID to delete: " + productId); // Debug message

                // Connect to the database
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventorymanagement", "root", "");

                // Execute DELETE SQL query
                String deleteQuery = "DELETE FROM product WHERE id = ?";
                PreparedStatement ps = con.prepareStatement(deleteQuery);

                // Pass the ID as appropriate (string or integer)
                ps.setString(1, productId); // If id is VARCHAR
                // ps.setInt(1, Integer.parseInt(productId)); // Uncomment if id is INT

                int rowsDeleted = ps.executeUpdate();

                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Product deleted successfully.");

                    // Remove the row from the table model
                    DefaultTableModel model = (DefaultTableModel) tableProduct.getModel();
                    model.removeRow(selectedRow);

                    // Clear the text fields
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete product.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                ps.close();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting product: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace(); // Debug the error
            }
        }
    }//GEN-LAST:event_deleteActionPerformed


    private void closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeActionPerformed
        // TODO add your handling code here:
        int a = JOptionPane.showConfirmDialog(null, "Do you want to close the app?", "Select", JOptionPane.YES_NO_OPTION);
        if (a == 0) {
            this.dispose();
            if (currentUserRole != null) {
                new Home(currentUserRole, currentUserName).setVisible(true);
            } else {
// Handle the case where currentUserRole is null (e.g., show an error message)
                JOptionPane.showMessageDialog(this, "Error: User  role is not defined.");
            }
        }
    }//GEN-LAST:event_closeActionPerformed

    private void nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameActionPerformed

    private void quanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_quanActionPerformed

    private void priceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_priceActionPerformed

    private void descActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_descActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_descActionPerformed

    private void searchbarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchbarActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tableProduct.getModel();
        model.setRowCount(0);

        String selectedCategory = (String) cat.getSelectedItem();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventorymanagement", "root", "");

            // Query to fetch products based on the selected category
            String query;
            if ("All".equals(selectedCategory)) {
                query = "SELECT * FROM product";
                ps = con.prepareStatement(query);
            } else {
                query = "SELECT * FROM product WHERE category = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, selectedCategory);
            }

            ResultSet rs = ps.executeQuery();

            // Loop through the result set and add each product to the table
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String quantity = rs.getString("quantity");
                String price = rs.getString("price");
                String description = rs.getString("description");
                String category = rs.getString("category");

                // Add row to the table
                model.addRow(new Object[]{id, name, quantity, price, description, category});
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to search products: " + e.getMessage());
        }

    }//GEN-LAST:event_searchbarActionPerformed

    private void searchbarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchbarKeyTyped
        // TODO add your handling code here:
        try {
            // Mysql Connector
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventorymanagement", "root", "");
            String query = "SELECT * FROM product WHERE id LIKE ? OR name LIKE ? OR quantity LIKE ? OR price LIKE ? "
                    + "OR description LIKE ? OR category LIKE ?";

            ps = con.prepareStatement(query);
            String searchText = "%" + searchbar.getText() + "%";

            ps.setString(1, searchText);
            ps.setString(2, searchText);
            ps.setString(3, searchText);
            ps.setString(4, searchText);
            ps.setString(5, searchText);
            ps.setString(6, searchText);

            rs = ps.executeQuery();
            DefaultTableModel dt = (DefaultTableModel) tableProduct.getModel();
            dt.setRowCount(0);

            while (rs.next()) {
                dt.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6)
                });
            }

        } catch (SQLException ex) {
            Logger.getLogger(ManageProduct.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ManageProduct.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_searchbarKeyTyped

    private void catActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_catActionPerformed

    private void tableProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableProductMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) { // Single click
            int selectedRow = tableProduct.getSelectedRow();
            if (selectedRow != -1) {
                // Get data from the selected row
                String id = tableProduct.getValueAt(selectedRow, 0).toString(); // ID column
                String names = tableProduct.getValueAt(selectedRow, 1).toString(); // Name column
                String quantity = tableProduct.getValueAt(selectedRow, 2).toString(); // Quantity column
                String prices = tableProduct.getValueAt(selectedRow, 3).toString(); // Price column
                String decsription = tableProduct.getValueAt(selectedRow, 4).toString(); // Decsription column
                String categorys = tableProduct.getValueAt(selectedRow, 5).toString(); // Category column

                // Retrieve password and role from the database
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventorymanagement", "root", "");

                    // Query to fetch password and role based on ID
                    String query = "SELECT password, userrole FROM inventorymanagementsystem WHERE appuser_pk = ?";
                    ps = con.prepareStatement(query);
                    ps.setString(1, id);
                    rs = ps.executeQuery();

                } catch (ClassNotFoundException | SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error fetching user details: " + e.getMessage());
                }

                // Set data to the text fields
                name.setText(names);
                quan.setText(quantity);
                price.setText(prices);
                desc.setText(decsription);
                cat.setSelectedItem(categorys);
            }
        }
    }//GEN-LAST:event_tableProductMouseClicked

    private void quanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_quanKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();

        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_quanKeyTyped

    private void priceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_priceKeyTyped
        // TODO add your handling code here:\
        char c = evt.getKeyChar();

        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_priceKeyTyped

    private void nameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        /*//can't type letter
        if (!Character.isDigit(c)) {
        evt.consume();
        }*/

        // can't type number
        if (Character.isDigit(c)) {
            evt.consume();
        }
        //can't type symbol
        if (!Character.isLetter(c) && c != ' ') {
            evt.consume();  // Block everything except letters and spaces
        }
    }//GEN-LAST:event_nameKeyTyped

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(ManageProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManageProduct().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BG5;
    private javax.swing.JComboBox<String> cat;
    private javax.swing.JButton close;
    private javax.swing.JButton delete;
    private javax.swing.JTextField desc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField name;
    private javax.swing.JTextField price;
    private javax.swing.JTextField quan;
    private javax.swing.JButton save;
    private javax.swing.JTextField searchbar;
    private javax.swing.JTable tableProduct;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables
}
