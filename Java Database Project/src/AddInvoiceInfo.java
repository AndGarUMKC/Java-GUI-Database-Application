import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class AddInvoiceInfo extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField customer_id;
    private JTextField invoice_date;
    private JTextField total_amount;
    private JTextField paid;
    private JButton saveButton;
    private JButton cancelButton;

    public AddInvoiceInfo() {
        setTitle("Add Invoice");
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(5, 5, 5, 5);

        panel.add(new JLabel("Customer ID:"), constraints);
        constraints.gridx = 1;
        customer_id = new JTextField(20);
        panel.add(customer_id, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(new JLabel("Invoice Date (YYYY-MM-DD):"), constraints);
        constraints.gridx = 1;
        invoice_date = new JTextField(20);
        panel.add(invoice_date, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(new JLabel("Meter Fee:"), constraints);
        constraints.gridx = 1;
        total_amount = new JTextField(20);
        panel.add(total_amount, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(new JLabel("Paid(YES/NO):"), constraints);
        constraints.gridx = 1;
        paid = new JTextField(20);
        panel.add(paid, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveMeter());
        panel.add(saveButton, constraints);

        constraints.gridx = 1;
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        panel.add(cancelButton, constraints);

        add(panel);
    }

    private void saveMeter() {
    	// Get text from input fields
        String customerId = customer_id.getText().trim();
        String invoiceDate = invoice_date.getText().trim();
        String totalAmount = total_amount.getText().trim();
        String paidValue = paid.getText().trim();
        
     // Check if any field is empty, display an error message and return if so
        if (customerId.isEmpty() || invoiceDate.isEmpty() || totalAmount.isEmpty() || paidValue.isEmpty()) {
            JPanel messagePanel = new JPanel();
            messagePanel.add(new JLabel("Please fill out all fields before saving."));
            JOptionPane.showMessageDialog(this, messagePanel, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Create a database connection
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/kc_energycompany", "root", "password@123");

            // Create the SQL query to insert a new customer row
            String query = "INSERT INTO invoice (customer_id, invoice_date, total_amount, paid) VALUES (?, ?, ?, ?)";

            // Create a PreparedStatement object to execute the query with parameters for the customer data
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, customerId);
            stmt.setString(2, invoiceDate);
            stmt.setString(3, totalAmount);
            stmt.setString(4, paidValue);

            // Execute the query to insert the new customer row
            stmt.executeUpdate();

            // Close the database connection
            conn.close();
            // Close the window
            dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving Invoice info: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
