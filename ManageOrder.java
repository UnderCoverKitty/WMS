
import java.awt.FileDialog;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Destination;
import javax.swing.JTable;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ACLC PC17
 */
public class ManageOrder extends javax.swing.JFrame {

    /**
     * Creates new form ManageOrder
     */
    private String currentUserName;
    private int selectedCustomerId;
    private boolean isEditMode = false;
    private String originalName = "";
    private String originalEmail = "";
    private String originalNumber = "";
    private int customerPk = 0;
    private int productPk = 0;
    private int finalTotalPrice = 0;
    private String orderId = "";
    private String currentUserRole;
    private String orderPaid;

    // LIMITER
    class IntegerDocumentFilter extends DocumentFilter {

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string != null && string.matches("\\d+")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text != null && text.matches("\\d+")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }

    public ManageOrder() {

        this("DefaultRole", "DefaultName"); // Provide default values

        initComponents();
        refresh();
        // Set filters for text fields
        ((AbstractDocument) pprice.getDocument()).setDocumentFilter(new IntegerDocumentFilter());
        ((AbstractDocument) oquan.getDocument()).setDocumentFilter(new IntegerDocumentFilter());
        ((AbstractDocument) number.getDocument()).setDocumentFilter(new IntegerDocumentFilter());

        // Make both tables non-editable
        makeTableNonEditable(tableCustomer);
        makeTableNonEditable(tableProduct);
    }

    public void refresh() {
        DefaultTableModel model = (DefaultTableModel) tableProduct.getModel();
        model.setRowCount(0); // Clear the table

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventorymanagement", "root", "");

            String query = "SELECT * FROM product";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("quantity"),
                    rs.getString("price"),
                    rs.getString("description"),
                    rs.getString("category")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error refreshing product list: " + e.getMessage());
        }
    }

    private void makeTableNonEditable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        table.setModel(new DefaultTableModel(model.getDataVector(), getColumnNames(model)) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Disable editing for all cells
            }
        });
    }

    // Helper method to get column names
    private Vector<String> getColumnNames(DefaultTableModel model) {
        Vector<String> columnNames = new Vector<>();
        for (int i = 0; i < model.getColumnCount(); i++) {
            columnNames.add(model.getColumnName(i));
        }
        return columnNames;
    }

    public ManageOrder(String role, String names) {
        super();
        // Set undecorated before any other initialization
        setUndecorated(true);

        // Store user information
        this.currentUserRole = role;
        this.currentUserName = names;

        // Initialize components and setup
        initComponents();
        setLocationRelativeTo(null);
        refresh();

        this.currentUserRole = role; // Store the role
        this.currentUserName = names; // Store the name

        // Optional: Log or display the information
        System.out.println("ManageOrder initialized with Role: " + currentUserRole + " and Name: " + currentUserName);
    }

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    private void clearProductFields() {
        productPk = 0;
        pname.setText("");
        pprice.setText("");
        pdesc.setText("");
        oquan.setText("");

    }

    private void setNonEditableTableModel(JTable table) {
        DefaultTableModel model = new NonEditableTableModel();
        table.setModel(model);
    }

    public String getUniqueId(String prefix) {
        return prefix + System.nanoTime();
    }

    public class NonEditableTableModel extends DefaultTableModel {

        @Override
        public boolean isCellEditable(int row, int column) {
            tableProduct.setModel(new NonEditableTableModel());
            return false;  // Make all cells non-editable
        }
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tableCustomer = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableProduct = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableCart = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        number = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        pdesc = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        pprice = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        pname = new javax.swing.JTextField();
        finalprice = new javax.swing.JLabel();
        oquan = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        cart = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        save = new javax.swing.JButton();
        close = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        search = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        reset = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        BG8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1172, 796));
        setUndecorated(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(41, 88, 82));
        jPanel1.setForeground(new java.awt.Color(41, 88, 82));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(242, 242, 242));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Manage Order");
        jLabel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jLabel1ComponentShown(evt);
            }
        });
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, -1, 40));

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1170, 50);

        jLabel2.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(41, 88, 82));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("PRODUCT LIST");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(430, 50, 710, 40);

        tableCustomer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tableCustomer.setForeground(new java.awt.Color(0, 29, 29));
        tableCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Email", "Number"
            }
        ));
        tableCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCustomerMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableCustomer);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(30, 90, 360, 200);

        tableProduct.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tableProduct.setForeground(new java.awt.Color(0, 29, 29));
        tableProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Quantity", "Price", "Description", "Category ID", "Category Name"
            }
        ));
        tableProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableProductMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableProduct);
        if (tableProduct.getColumnModel().getColumnCount() > 0) {
            tableProduct.getColumnModel().getColumn(5).setHeaderValue("Category ID");
        }

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(430, 90, 710, 200);

        jLabel4.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(41, 88, 82));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("CART");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(70, 520, 540, 30);

        tableCart.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tableCart.setForeground(new java.awt.Color(0, 29, 29));
        tableCart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Name", "Quantity", "Price", "Description", "Sub Total"
            }
        ));
        tableCart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCartMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableCart);

        getContentPane().add(jScrollPane3);
        jScrollPane3.setBounds(70, 550, 550, 200);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(41, 88, 82));
        jLabel5.setText("Selected Customer:");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(30, 300, 360, 20);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(41, 88, 82));
        jLabel6.setText("Name:");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(30, 320, 360, 20);

        email.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 88, 82), 2, true));
        email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailActionPerformed(evt);
            }
        });
        getContentPane().add(email);
        email.setBounds(30, 390, 360, 31);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(41, 88, 82));
        jLabel7.setText("Email:");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(30, 370, 360, 20);

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
        name.setBounds(30, 340, 360, 31);

        number.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 88, 82), 2, true));
        number.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numberActionPerformed(evt);
            }
        });
        number.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                numberKeyTyped(evt);
            }
        });
        getContentPane().add(number);
        number.setBounds(30, 440, 280, 31);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(41, 88, 82));
        jLabel8.setText("Number:");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(30, 420, 360, 20);

        pdesc.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 88, 82), 2, true));
        getContentPane().add(pdesc);
        pdesc.setBounds(790, 340, 350, 30);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(41, 88, 82));
        jLabel9.setText("Product Description");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(790, 320, 320, 17);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(41, 88, 82));
        jLabel10.setText("Selected Product:");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(430, 300, 410, 20);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(41, 88, 82));
        jLabel11.setText("Product Name:");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(430, 320, 340, 20);

        pprice.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 88, 82), 2, true));
        pprice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ppriceKeyTyped(evt);
            }
        });
        getContentPane().add(pprice);
        pprice.setBounds(430, 390, 340, 30);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(41, 88, 82));
        jLabel12.setText("Product Price");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(430, 370, 340, 20);

        pname.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 88, 82), 2, true));
        pname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pnameKeyTyped(evt);
            }
        });
        getContentPane().add(pname);
        pname.setBounds(430, 340, 340, 30);

        finalprice.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        finalprice.setForeground(new java.awt.Color(41, 88, 82));
        finalprice.setText("0000");
        getContentPane().add(finalprice);
        finalprice.setBounds(810, 540, 300, 33);

        oquan.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 88, 82), 2, true));
        oquan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                oquanKeyTyped(evt);
            }
        });
        getContentPane().add(oquan);
        oquan.setBounds(790, 390, 350, 30);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(41, 88, 82));
        jLabel17.setText("Order Quantity");
        getContentPane().add(jLabel17);
        jLabel17.setBounds(790, 370, 320, 17);

        cart.setBackground(new java.awt.Color(0, 29, 29));
        cart.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        cart.setForeground(new java.awt.Color(242, 242, 242));
        cart.setText("Add to Cart");
        cart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cartActionPerformed(evt);
            }
        });
        getContentPane().add(cart);
        cart.setBounds(430, 440, 710, 30);

        jLabel18.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(41, 88, 82));
        jLabel18.setText("Total Amount RS:");
        getContentPane().add(jLabel18);
        jLabel18.setBounds(670, 540, 140, 33);

        save.setBackground(new java.awt.Color(0, 29, 29));
        save.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        save.setForeground(new java.awt.Color(242, 242, 242));
        save.setText("Save Order Details");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        getContentPane().add(save);
        save.setBounds(660, 590, 450, 30);

        close.setBackground(new java.awt.Color(0, 29, 29));
        close.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        close.setForeground(new java.awt.Color(242, 242, 242));
        close.setText("Close");
        close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeActionPerformed(evt);
            }
        });
        getContentPane().add(close);
        close.setBounds(660, 710, 450, 30);

        jButton1.setBackground(new java.awt.Color(0, 29, 29));
        jButton1.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(242, 242, 242));
        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(320, 440, 70, 30);

        jPanel2.setBackground(new java.awt.Color(163, 198, 134));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/magnifying-glass.png"))); // NOI18N
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 30, -1));

        search.setBackground(new java.awt.Color(254, 254, 254));
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });
        search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchKeyTyped(evt);
            }
        });
        jPanel2.add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 190, -1));

        jLabel3.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(41, 88, 82));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("CUSTOMER LIST");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 130, 40));

        getContentPane().add(jPanel2);
        jPanel2.setBounds(10, 50, 1150, 450);

        jPanel3.setBackground(new java.awt.Color(163, 198, 134));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        reset.setBackground(new java.awt.Color(0, 29, 29));
        reset.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        reset.setForeground(new java.awt.Color(242, 242, 242));
        reset.setText("Reset");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });
        jPanel3.add(reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 140, 450, 30));

        getContentPane().add(jPanel3);
        jPanel3.setBounds(10, 510, 1150, 280);

        jPanel5.setBackground(new java.awt.Color(41, 88, 82));
        getContentPane().add(jPanel5);
        jPanel5.setBounds(0, 50, 1170, 460);

        jPanel4.setBackground(new java.awt.Color(41, 88, 82));
        getContentPane().add(jPanel4);
        jPanel4.setBounds(0, 510, 1190, 290);

        BG8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/MOo.png"))); // NOI18N
        getContentPane().add(BG8);
        BG8.setBounds(0, 0, 1170, 800);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jLabel1ComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1ComponentShown

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        name.setEditable(true);
        email.setEditable(true);
        number.setEditable(true);

        pname.setEditable(true);
        pprice.setEditable(true);
        pdesc.setEditable(true);
        pdesc.setEditable(true);

        DefaultTableModel model = (DefaultTableModel) tableCustomer.getModel();
        DefaultTableModel pmodel = (DefaultTableModel) tableProduct.getModel();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventorymanagement", "root", "");

            // Specify that we want to retrieve generated keys (ID)
            ps = con.prepareStatement("Select * from customer");
            rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("customer_pk"), rs.getString("name"), rs.getString("email"), rs.getString("number")});
            }

            rs = ps.executeQuery("Select * from product inner join category on product.id = category.category_pk");

            while (rs.next()) {
                pmodel.addRow(new Object[]{rs.getString("id"), rs.getString("name"), rs.getString("quantity"), rs.getString("price"), rs.getString("description"), rs.getString("category_pk"), rs.getString(8)});

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_formComponentShown

    private void closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeActionPerformed
        // TODO add your handling code here:
        int a = JOptionPane.showConfirmDialog(null, "Do you want to close the app?", "Select", JOptionPane.YES_NO_OPTION);
        if (a == 0) {
            this.dispose();
            if (currentUserRole != null) {
                new Home(currentUserRole, currentUserName).setVisible(true); // Pass the user role back to Home
            } else {
                JOptionPane.showMessageDialog(this, "User role is not defined. Redirecting to default home.");
                new Home().setVisible(true); // Open Home without the role (safe default)
            }
        }
    }//GEN-LAST:event_closeActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        if (finalTotalPrice != 0 && !name.getText().equals("")) {
        orderId = getUniqueId("Bill-");

        DefaultTableModel dtm = (DefaultTableModel) tableCart.getModel();
        if (tableCart.getRowCount() != 0) {
            List<String[]> items = new ArrayList<>();
            for (int i = 0; i < tableCart.getRowCount(); i++) {
                String[] item = {
                    tableCart.getValueAt(i, 1).toString(), // Product name
                    tableCart.getValueAt(i, 3).toString(), // Price
                    tableCart.getValueAt(i, 2).toString(), // Quantity
                    tableCart.getValueAt(i, 5).toString()  // Subtotal
                };
                items.add(item);
            }

            PrintReceipt receipt = new PrintReceipt(orderId, name.getText(), currentUserName, items, finalTotalPrice);
            receipt.showPreview(); // Show preview first
        }
    } else {
        JOptionPane.showMessageDialog(null, "Please select a customer and add items to cart.");
    }

    }//GEN-LAST:event_saveActionPerformed

    private void nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameActionPerformed

    private void tableCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCustomerMouseClicked
        // TODO add your handling code here:
        int index = tableCustomer.getSelectedRow();
        TableModel model = tableCustomer.getModel();
        String id = model.getValueAt(index, 0).toString();
        customerPk = Integer.parseInt(id);

        String CustomerName = model.getValueAt(index, 1).toString();
        name.setText(CustomerName);

        String Email = model.getValueAt(index, 2).toString();
        email.setText(Email);

        String MobileNumber = model.getValueAt(index, 3).toString();
        number.setText(MobileNumber);
    }//GEN-LAST:event_tableCustomerMouseClicked

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        new ManageOrder().setVisible(true);
    }//GEN-LAST:event_resetActionPerformed

    private void emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailActionPerformed

    private void tableProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableProductMouseClicked
        // TODO add your handling code here:
        int index = tableProduct.getSelectedRow();
        TableModel model = tableProduct.getModel();
        String id = model.getValueAt(index, 0).toString();
        productPk = Integer.parseInt(id);

        String ProductName = model.getValueAt(index, 1).toString();
        pname.setText(ProductName);

        String Price = model.getValueAt(index, 3).toString();
        pprice.setText(Price);

        String Description = model.getValueAt(index, 4).toString();
        pdesc.setText(Description);

        String Orderquantity = model.getValueAt(index, 2).toString();
        oquan.setText(Orderquantity);

    }//GEN-LAST:event_tableProductMouseClicked

    private void cartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cartActionPerformed
        // TODO add your handling code here:
        // Check if a customer is selected
        if (customerPk == 0) {
            JOptionPane.showMessageDialog(null, "Please pick a customer before adding items to the cart.", "Warning", JOptionPane.WARNING_MESSAGE);
            return; // Exit the method if no customer is selected
        }

        String noOfUnits = oquan.getText();

        // Check if the quantity is valid
        if (noOfUnits.equals("") || Integer.parseInt(noOfUnits) <= 0) {
            JOptionPane.showMessageDialog(null, "Please enter a quantity greater than 0.");
            return; // Exit the method
        }

        String ProductName = pname.getText();
        String ProductPrice = pprice.getText();
        String ProductDescription = pdesc.getText();

        int totalPrice = Integer.parseInt(noOfUnits) * Integer.parseInt(ProductPrice);

        int checkStock = 0;
        int checkProductAlreadyExistInCart = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventorymanagement", "root", "");
            ps = con.prepareStatement("Select * from product where id = " + productPk + "");
            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt("quantity") >= Integer.parseInt(noOfUnits)) {
                    checkStock = 1;
                } else {
                    JOptionPane.showMessageDialog(null, "Product is out of stock, Only " + rs.getInt("quantity") + " left.");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ManageOrder.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (checkStock == 1) {
                DefaultTableModel model = (DefaultTableModel) tableCart.getModel();
                if (tableCart.getRowCount() != 0) {
                    for (int i = 0; i < tableCart.getRowCount(); i++) {
                        if (Integer.parseInt(model.getValueAt(i, 0).toString()) == productPk) {
                            checkProductAlreadyExistInCart = 1;
                            JOptionPane.showMessageDialog(null, "Product already exists in the cart.");
                        }
                    }
                }
                if (checkProductAlreadyExistInCart == 0) {
                    model.addRow(new Object[]{productPk, ProductName, noOfUnits, ProductPrice, ProductDescription, totalPrice});
                    finalTotalPrice += totalPrice;
                    finalprice.setText(String.valueOf(finalTotalPrice));
                    JOptionPane.showMessageDialog(null, "Added Successfully.");
                }
                clearProductFields();
            }
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number");
        }
    }//GEN-LAST:event_cartActionPerformed

    private void tableCartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCartMouseClicked
        // TODO add your handling code here:
        int index = tableCart.getSelectedRow();
        int a = JOptionPane.showConfirmDialog(null, "Do you want to remove this product?", "Select", JOptionPane.YES_NO_OPTION);
        if (a == 0) {
            TableModel model = tableCart.getModel();
            String subTotal = model.getValueAt(index, 5).toString();
            finalTotalPrice = finalTotalPrice - Integer.parseInt(subTotal);
            oquan.setText(String.valueOf(finalTotalPrice));
            ((DefaultTableModel) tableCart.getModel()).removeRow(index);
        }
    }//GEN-LAST:event_tableCartMouseClicked

    private void numberKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numberKeyTyped
        // TODO add your handling code here:
        String currentText = number.getText();
        char c = evt.getKeyChar();

        if (!Character.isDigit(c)) {
            evt.consume();
        }

        if (currentText.length() == 0 && c != '0') {
            evt.consume();
            return;
        }
        if (currentText.length() == 1 && currentText.charAt(0) == '0' && c != '9') {
            evt.consume();
            return;
        }
        if (currentText.length() >= 11) {
            evt.consume();
        }
    }//GEN-LAST:event_numberKeyTyped

    private void ppriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ppriceKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();

        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_ppriceKeyTyped

    private void oquanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_oquanKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();

        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_oquanKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (!isEditMode) {
            // Add mode
            if (!validateFields(false)) {
                return; // Validate fields in Add mode
            }

            // Get current values from the text fields
            String currentName = name.getText().trim();
            String currentEmail = email.getText().trim();
            String currentNumber = number.getText().trim();

            // Check if no changes are made (all fields are still default/empty)
            if (currentName.isEmpty() && currentEmail.isEmpty() && currentNumber.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No changes made.");
                return; // Exit early if no changes
            }

            try {
                // Database logic for adding a new customer
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventorymanagement", "root", "");
                ps = con.prepareStatement(
                        "INSERT INTO customer (name, email, number) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, currentName);
                ps.setString(2, currentEmail);
                ps.setString(3, currentNumber);

                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Customer Added Successfully!");

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int newCustomerID = rs.getInt(1);
                    DefaultTableModel model = (DefaultTableModel) tableCustomer.getModel();
                    model.addRow(new Object[]{newCustomerID, currentName, currentEmail, currentNumber});
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void loadSelectedCustomer() {
        int selectedRow = tableCustomer.getSelectedRow();
        if (selectedRow >= 0) {
            isEditMode = true; // Mark as edit mode
            DefaultTableModel model = (DefaultTableModel) tableCustomer.getModel();
            selectedCustomerId = (int) model.getValueAt(selectedRow, 0); // Customer ID
            originalName = (String) model.getValueAt(selectedRow, 1); // Set original values
            originalEmail = (String) model.getValueAt(selectedRow, 2);
            originalNumber = (String) model.getValueAt(selectedRow, 3);

            name.setText(originalName);
            email.setText(originalEmail);
            number.setText(originalNumber);
        } else {
            JOptionPane.showMessageDialog(null, "No customer selected.");
        }
    }

    private boolean validateFields(boolean isEditMode) {
        String names = name.getText().trim();
        String emails = email.getText().trim();
        String numbers = number.getText().trim();

        // Check for empty fields
        if (names.isEmpty() || emails.isEmpty() || numbers.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are required.");
            return false; // Validation failed
        }

        // If edit mode, ensure a change has been made
        if (isEditMode) {
            if (names.equals(originalName) && emails.equals(originalEmail) && numbers.equals(originalNumber)) {
                JOptionPane.showMessageDialog(null, "No Changes Made");
                return false; // No changes detected
            }
        }

        return true; // Validation passed
    }

    private void numberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numberActionPerformed

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

    private void pnameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pnameKeyTyped
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
    }//GEN-LAST:event_pnameKeyTyped

    private void searchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyTyped
        // TODO add your handling code here:
        try {
            // Mysql Connector
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventorymanagement", "root", "");
            String query = "SELECT * FROM customer WHERE customer_pk LIKE ? OR name LIKE ? OR email LIKE ? OR number LIKE ? ";

            ps = con.prepareStatement(query);
            String searchText = "%" + search.getText() + "%";

            ps.setString(1, searchText);
            ps.setString(2, searchText);
            ps.setString(3, searchText);
            ps.setString(4, searchText);

            rs = ps.executeQuery();
            DefaultTableModel dt = (DefaultTableModel) tableCustomer.getModel();
            dt.setRowCount(0);

            while (rs.next()) {
                dt.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4)
                });
            }

        } catch (SQLException ex) {
            Logger.getLogger(ManageOrder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ManageOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_searchKeyTyped

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tableCustomer.getModel();
        model.setRowCount(0);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventorymanagement", "root", "");

            ResultSet rs = ps.executeQuery();

            // Loop through the result set and add each product to the table
            while (rs.next()) {
                String id = rs.getString("customer_pk");
                String named = rs.getString("name");
                String emaild = rs.getString("email");
                String numberd = rs.getString("number");

                // Add row to the table
                model.addRow(new Object[]{id, name, email, number});
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to search products: " + e.getMessage());
        }
    }//GEN-LAST:event_searchActionPerformed

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
            java.util.logging.Logger.getLogger(ManageOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManageOrder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BG8;
    private javax.swing.JButton cart;
    private javax.swing.JButton close;
    private javax.swing.JTextField email;
    private javax.swing.JLabel finalprice;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField name;
    private javax.swing.JTextField number;
    private javax.swing.JTextField oquan;
    private javax.swing.JTextField pdesc;
    private javax.swing.JTextField pname;
    private javax.swing.JTextField pprice;
    private javax.swing.JButton reset;
    private javax.swing.JButton save;
    private javax.swing.JTextField search;
    private javax.swing.JTable tableCart;
    private javax.swing.JTable tableCustomer;
    private javax.swing.JTable tableProduct;
    // End of variables declaration//GEN-END:variables
}
