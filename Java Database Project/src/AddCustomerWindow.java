import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class AddCustomerWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField nameField;
	private JTextField phoneField;
	private JTextField addressField;
	private JTextField tariffField;
	private JTextField meterField;
	private JButton saveButton;

	public AddCustomerWindow() {
		setTitle("Add Customer");
		setSize(400, 300);
		setResizable(false);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(5, 5, 5, 5);

		panel.add(new JLabel("Name:"), constraints);
		constraints.gridx = 1;
		nameField = new JTextField(20);
		panel.add(nameField, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		panel.add(new JLabel("Phone Number:"), constraints);
		constraints.gridx = 1;
		phoneField = new JTextField(20);
		panel.add(phoneField, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		panel.add(new JLabel("Current Address:"), constraints);
		constraints.gridx = 1;
		addressField = new JTextField(20);
		panel.add(addressField, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;
		panel.add(new JLabel("Energy Tariff:"), constraints);
		constraints.gridx = 1;
		tariffField = new JTextField(20);
		panel.add(tariffField, constraints);

		constraints.gridx = 0;
		constraints.gridy = 4;
		panel.add(new JLabel("Meter Type:"), constraints);
		constraints.gridx = 1;
		meterField = new JTextField(20);
		panel.add(meterField, constraints);

		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 2;
		saveButton = new JButton("Save");
		saveButton.addActionListener(e -> saveCustomer());
		panel.add(saveButton, constraints);

		add(panel);
	}

	private void saveCustomer() {
		try {
			// Create a database connection
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/kc_energycompany", "root", "password@123");

			// Create the SQL query to insert a new customer row
			String query = "INSERT INTO customer (name, phone_number, current_address, energy_tariff, meter_type) VALUES (?, ?, ?, ?, ?)";

			// Create a PreparedStatement object to execute the query with parameters for the customer data
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, nameField.getText());
			stmt.setString(2, phoneField.getText());
			stmt.setString(3, addressField.getText());
			stmt.setString(4, tariffField.getText());
			stmt.setString(5, meterField.getText());

			// Execute the query to insert the new customer row
			stmt.executeUpdate();

			// Close the database connection
			conn.close();

			// Close the window
			dispose();
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error saving customer: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
