import java.io.FileOutputStream;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.io.File;
import javax.swing.*;
import java.awt.event.*;
import com.itextpdf.text.Rectangle;  // Add this specific import
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrintReceipt {
    private String orderId;
    private String customerName;
    private String cashierName;
    private List<String[]> items;
    private int totalPaid;
    private File tempFile;
    private String currenUserName;
    private String currentUserName;
    

    public PrintReceipt(String orderId, String customerName, String cashierName, List<String[]> items, int totalPaid) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.cashierName = cashierName;
        this.items = items;
        this.totalPaid = totalPaid;
    }
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public void showPreview() {
    try {
        // Create temporary PDF file
        tempFile = File.createTempFile("receipt_", ".pdf");
        tempFile.deleteOnExit();
        generatePDF(tempFile);

        // Create main frame
        JFrame frame = new JFrame("Receipt Preview");
        frame.setSize(400, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Create a preview panel with receipt information
        JTextArea previewArea = new JTextArea();
        previewArea.setEditable(false);
        previewArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        previewArea.setMargin(new Insets(10, 10, 10, 10));

        // Add receipt content to preview
        StringBuilder receiptContent = new StringBuilder();
        receiptContent.append("                INVENTORY STORE\n\n");
        receiptContent.append("            123 Business Street\n");
        receiptContent.append("            City, State 12345\n");
        receiptContent.append("            Tel: (555) 123-4567\n");
        receiptContent.append("          www.inventorystore.com\n\n");
        receiptContent.append("----------------------------------------\n\n");
        
        // Add receipt details
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        
        receiptContent.append(String.format("Date: %s\n", now.format(dateFormatter)));
        receiptContent.append(String.format("Time: %s\n", now.format(timeFormatter)));
        receiptContent.append(String.format("Receipt: %s\n", orderId));
        receiptContent.append(String.format("Cashier: %s\n", cashierName));
        receiptContent.append(String.format("Customer: %s\n\n", customerName));
        
        receiptContent.append("----------------------------------------\n");
        receiptContent.append("ITEM                 PRICE   QTY   AMOUNT\n");
        receiptContent.append("----------------------------------------\n");

        // Add items
        for (String[] item : items) {
            String itemLine = String.format("%-20s", item[0].length() > 20 ? 
                item[0].substring(0, 17) + "..." : item[0]);
            String priceLine = String.format("%6s", item[1]);
            String qtyLine = String.format("%5s", item[2]);
            String amountLine = String.format("%8s", item[3]);
            
            receiptContent.append(itemLine).append(priceLine)
                         .append(qtyLine).append(amountLine).append("\n");
        }

        receiptContent.append("----------------------------------------\n");
        receiptContent.append(String.format("TOTAL AMOUNT: $%,d\n", totalPaid));
        receiptContent.append("----------------------------------------\n\n");
        
        receiptContent.append("PAYMENT INFORMATION\n");
        receiptContent.append(String.format("Payment Method: Cash\n"));
        receiptContent.append(String.format("Amount Paid: $%,d\n\n", totalPaid));
        
        receiptContent.append("----------------------------------------\n");
        receiptContent.append("          Thank you for shopping with us!\n\n");
        receiptContent.append("        Returns accepted within 7 days\n");
        receiptContent.append("          with original receipt\n\n");
        receiptContent.append("         Follow us on social media\n");
        receiptContent.append("             @InventoryStore\n\n");
        receiptContent.append("           *** End of Receipt ***\n");

        previewArea.setText(receiptContent.toString());
        JScrollPane scrollPane = new JScrollPane(previewArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton printButton = new JButton("Print");
        
        // Style button
        printButton.setPreferredSize(new Dimension(100, 30));

        // Add action listener
        printButton.addActionListener(e -> {
            try {
                updateDatabase();
                Desktop.getDesktop().print(tempFile);
                frame.dispose();
                JOptionPane.showMessageDialog(null,
                    "Order processed and printed successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                showErrorDialog(frame, "Error printing: " + ex.getMessage());
            }
        });

        buttonPanel.add(printButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Show frame
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

    } catch (Exception e) {
        showErrorDialog(null, "Error creating receipt: " + e.getMessage());
    }
}

    private void updateDatabase() throws SQLException, ClassNotFoundException {
    Connection con = null;
    PreparedStatement ps = null;
    
    try {
        // Database Connection
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventorymanagement", "root", "");
        
        // Start transaction
        con.setAutoCommit(false);
        
        // Update product quantities
        for (String[] item : items) {
            String updateQuery = "UPDATE product SET quantity = quantity - ? WHERE name = ?";
            ps = con.prepareStatement(updateQuery);
            ps.setInt(1, Integer.parseInt(item[2])); // Quantity
            ps.setString(2, item[0]); // Product name
            
            int updated = ps.executeUpdate();
            if (updated != 1) {
                throw new SQLException("Failed to update quantity for product: " + item[0]);
            }
        }
        
        // Get current date/time
        LocalDateTime now = LocalDateTime.now();
        
        // Insert into orderdetail table using selectedCustomerId
        String insertQuery = "INSERT INTO orderdetail (customer_pk, orderdate, totalpaid) VALUES (?, ?, ?)";
        ps = con.prepareStatement(insertQuery);
        ps.setInt(1, selectedCustomerId); // Use selectedCustomerId from class
        ps.setDate(2, java.sql.Date.valueOf(now.toLocalDate()));
        ps.setInt(3, totalPaid);
        ps.executeUpdate();
        
        // Commit the transaction
        con.commit();
        
    } catch (SQLException e) {
        // Rollback on error
        if (con != null) {
            con.rollback();
        }
        throw e;
    } finally {
        if (ps != null) ps.close();
        if (con != null) {
            con.setAutoCommit(true);
            con.close();
        }
    }
}
    private void showErrorDialog(Component parent, String message) {
    JOptionPane.showMessageDialog(
        parent,
        message,
        "Error",
        JOptionPane.ERROR_MESSAGE
    );
}

    private void generatePDF(File file) {
        Rectangle pagesize = new Rectangle(226f, 842f);
        Document document = new Document(pagesize, 10f, 10f, 10f, 10f);

        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            com.itextpdf.text.Font headerFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 12, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font normalFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 8, com.itextpdf.text.Font.NORMAL);
            com.itextpdf.text.Font smallFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 7, com.itextpdf.text.Font.NORMAL);
            com.itextpdf.text.Font totalFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 10, com.itextpdf.text.Font.BOLD);

            // Store Name
            Paragraph storeName = new Paragraph("INVENTORY STORE", headerFont);
            storeName.setAlignment(Element.ALIGN_CENTER);
            document.add(storeName);

            // Store Info
            Paragraph storeInfo = new Paragraph(
                "123 Business Street\n" +
                "City, State 12345\n" +
                "Tel: (555) 123-4567\n" +
                "www.inventorystore.com\n", normalFont);
            storeInfo.setAlignment(Element.ALIGN_CENTER);
            document.add(storeInfo);

            document.add(new Paragraph("----------------------------------------", normalFont));

            // Receipt Details
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            Paragraph details = new Paragraph();
            details.setFont(normalFont);
            details.add(String.format("Date: %s\n", now.format(dateFormatter)));
            details.add(String.format("Time: %s\n", now.format(timeFormatter)));
            details.add(String.format("Receipt: %s\n", orderId));
            details.add(String.format("Cashier: %s\n", cashierName));
            details.add(String.format("Customer: %s\n", customerName));
            document.add(details);

            document.add(new Paragraph("----------------------------------------", normalFont));

            // Items Header
            Paragraph itemHeader = new Paragraph();
            itemHeader.setFont(normalFont);
            itemHeader.add("ITEM                 PRICE   QTY   AMOUNT\n");
            document.add(itemHeader);
            document.add(new Paragraph("----------------------------------------", normalFont));

            // Items
            for (String[] item : items) {
                String itemLine = String.format("%-20s", item[0].length() > 20 ? 
                    item[0].substring(0, 17) + "..." : item[0]);
                String priceLine = String.format("%6s", item[1]);
                String qtyLine = String.format("%5s", item[2]);
                String amountLine = String.format("%8s", item[3]);

                Paragraph itemParagraph = new Paragraph(
                    itemLine + priceLine + qtyLine + amountLine, normalFont);
                document.add(itemParagraph);
            }

            document.add(new Paragraph("----------------------------------------", normalFont));

            // Total
            Paragraph totalLine = new Paragraph();
            totalLine.setFont(totalFont);
            totalLine.add(String.format("TOTAL AMOUNT: $%,d\n", totalPaid));
            totalLine.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalLine);

            document.add(new Paragraph("----------------------------------------", normalFont));

            // Payment Info
            Paragraph paymentInfo = new Paragraph("PAYMENT INFORMATION\n", normalFont);
            paymentInfo.add(String.format("Payment Method: Cash\n"));
            paymentInfo.add(String.format("Amount Paid: $%,d\n", totalPaid));
            document.add(paymentInfo);

            document.add(new Paragraph("----------------------------------------", normalFont));

            // Footer
            Paragraph footer = new Paragraph();
            footer.setFont(smallFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.add("Thank you for shopping with us!\n\n");
            footer.add("Returns accepted within 7 days\n");
            footer.add("with original receipt\n\n");
            footer.add("Follow us on social media\n");
            footer.add("@InventoryStore\n\n");
            footer.add(String.format("*** End of Receipt ***\n"));
            document.add(footer);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }
    }
}