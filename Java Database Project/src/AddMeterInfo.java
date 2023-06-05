import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class AddMeterInfo extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField customer_id;
	private JTextField days_used;
	private JTextField reading_date;
	private JTextField reading_value;
	private JTextField tariff_value;
	private JButton saveButton;

	public AddMeterInfo() {
		setTitle("Add Customer Meter Info");
		setSize(500, 300);
		setResizable(false);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(6, 6, 6, 6);

		panel.add(new JLabel("Customer ID:"), constraints);
		constraints.gridx = 1;
		customer_id = new JTextField(20);
		panel.add(customer_id, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		panel.add(new JLabel("Days Since Reading:"), constraints);
		constraints.gridx = 1;
		days_used = new JTextField(20);
		panel.add(days_used, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		panel.add(new JLabel("Reading Date (YYYY-MM-DD):"), constraints);
		constraints.gridx = 1;
		reading_date = new JTextField(20);
		panel.add(reading_date, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;
		panel.add(new JLabel("KWh (Meter Reading):"), constraints);
		constraints.gridx = 1;
		reading_value = new JTextField(20);
		panel.add(reading_value, constraints);

		constraints.gridx = 0;
		constraints.gridy = 4;
		panel.add(new JLabel("KWh Energy Tariff Rate:"), constraints);
		constraints.gridx = 1;
		tariff_value = new JTextField(20);
		panel.add(tariff_value, constraints);

		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 2;
		saveButton = new JButton("Save");
		saveButton.addActionListener(e -> saveMeter());
		panel.add(saveButton, constraints);

		add(panel);
	}

	private void saveMeter() {
		// Get text from input fields
		String customerId = customer_id.getText().trim();
        String daysUsed = days_used.getText().trim();
        String totalAmount = reading_date.getText().trim();
        String paidValue = reading_value.getText().trim();
        String tariffValue = tariff_value.getText().trim();


        if (customerId.isEmpty() || daysUsed.isEmpty() || totalAmount.isEmpty() || paidValue.isEmpty() || tariffValue.isEmpty()) {
            JPanel messagePanel = new JPanel();
            messagePanel.add(new JLabel("Please fill out all fields before saving."));
            JOptionPane.showMessageDialog(this, messagePanel, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Create a database connection
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/kc_energycompany", "root", "password@123");

            // Create the SQL query to insert a new customer row
            String query = "INSERT INTO customer_consumption (customer_id, days_used, reading_date, reading_value, tariff_value) VALUES (?, ?, ?, ?, ?)";

            // Create a PreparedStatement object to execute the query with parameters for the customer data
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, customerId);
            stmt.setString(2, daysUsed);
            stmt.setString(3, totalAmount);
            stmt.setString(4, paidValue);
            stmt.setString(5, tariffValue);

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
