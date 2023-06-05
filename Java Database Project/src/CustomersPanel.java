import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

public class CustomersPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField searchField;
	private JButton searchButton;
	private JTable table;
	private JButton addButton;
	private JButton editButton;
	private JButton deleteButton;
	private JButton refreshButton;


	public CustomersPanel() {
		setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		searchField = new JTextField(20);
		topPanel.add(searchField);

		searchButton = new JButton("Search");
		searchButton.addActionListener(e -> search());
		topPanel.add(searchButton);

		add(topPanel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());

		// Initialize the table model and set it to the JTable
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Customer ID");
		model.addColumn("Customer Name");
		model.addColumn("Phone Number");
		model.addColumn("Current Address");
		model.addColumn("Energy Tariff");
		model.addColumn("Meter Type");

		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		centerPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		addButton = new JButton("Add");
		buttonPanel.add(addButton);
		addButton.addActionListener(e -> {
			AddCustomerWindow addWindow = new AddCustomerWindow();
			addWindow.setVisible(true);
		});

		editButton = new JButton("Edit");
		buttonPanel.add(editButton);
		editButton.addActionListener(e -> {
			int selectedViewRow = table.getSelectedRow();
			if (selectedViewRow == -1) {
				JOptionPane.showMessageDialog(this, "Please select a row to edit.", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				// Get the index of the selected row in the model
				int selectedRow = table.convertRowIndexToModel(selectedViewRow);

				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				Object[] rowData = new Object[tableModel.getColumnCount()];
				for (int i = 0; i < rowData.length; i++) {
					rowData[i] = tableModel.getValueAt(selectedRow, i);
				}

				// Create a new window to edit the customer's information
				EditCustomerWindow editWindow = new EditCustomerWindow();
				editWindow.setCustomerInfo(rowData);

				// Add an ActionListener to the "Save" button in the edit window
				editWindow.addSaveButtonListener(ee -> {
					int id = (int) rowData[0];
					String name = editWindow.getNameField().getText();
					String phoneNumber = editWindow.getPhoneNumberField().getText();
					String currentAddress = editWindow.getCurrentAddressField().getText();
					String energyTariff = editWindow.getEnergyTariffField().getText();
					String meterType = editWindow.getMeterTypeField().getText();

					// Update the customer's information in the database
					try {
						Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/kc_energycompany", "root", "password@123");
						String query = "UPDATE customer SET name=?, phone_number=?, current_address=?, energy_tariff=?, meter_type=? WHERE id=?";
						PreparedStatement stmt = conn.prepareStatement(query);
						stmt.setString(1, name);
						stmt.setString(2, phoneNumber);
						stmt.setString(3, currentAddress);
						stmt.setString(4, energyTariff);
						stmt.setString(5, meterType);
						stmt.setInt(6, id);
						stmt.executeUpdate();
						conn.close();

						// Update the customer's information in the table model
						rowData[1] = name;
						rowData[2] = phoneNumber;
						rowData[3] = currentAddress;
						rowData[4] = energyTariff;
						rowData[5] = meterType;
						tableModel.fireTableDataChanged();

						// Close the edit window
						editWindow.dispose();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				});

				editWindow.setVisible(true);
			}
		});


		deleteButton = new JButton("Delete");
		buttonPanel.add(deleteButton);
		deleteButton.addActionListener(e -> {
			int selectedViewRow = table.getSelectedRow();
			if (selectedViewRow == -1) {
				JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				// Get the index of the selected row in the model
				int selectedRow = table.convertRowIndexToModel(selectedViewRow);

				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				int id = (int) tableModel.getValueAt(selectedRow, 0);

				// Delete the customer's record from the database
				try {
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/kc_energycompany", "root", "password@123");
					conn.setAutoCommit(false);

					// Delete from the invoice table first
					String deleteInvoiceQuery = "DELETE FROM invoice WHERE customer_id=?";
					PreparedStatement deleteInvoiceStmt = conn.prepareStatement(deleteInvoiceQuery);
					deleteInvoiceStmt.setInt(1, id);
					deleteInvoiceStmt.executeUpdate();
					
					// Delete from the customer_consumption table second
					String deleteConsumptionQuery = "DELETE FROM customer_consumption WHERE customer_id=?";
					PreparedStatement deleteConsumptionStmt = conn.prepareStatement(deleteConsumptionQuery);
					deleteConsumptionStmt.setInt(1, id);
					deleteConsumptionStmt.executeUpdate();

					// Delete from the customer table
					String deleteCustomerQuery = "DELETE FROM customer WHERE id=?";
					PreparedStatement deleteCustomerStmt = conn.prepareStatement(deleteCustomerQuery);
					deleteCustomerStmt.setInt(1, id);
					deleteCustomerStmt.executeUpdate();

					conn.commit();
					conn.setAutoCommit(true);

					// Remove the customer's record from the table model
					tableModel.removeRow(selectedRow);

					JOptionPane.showMessageDialog(this, "Customer deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(this, "An error occurred while deleting the customer's record:\n\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}}



		});

		refreshButton = new JButton("Refresh");
		buttonPanel.add(refreshButton);
		refreshButton.addActionListener(e -> {
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/kc_energycompany", "root", "password@123");
				String query = "SELECT * FROM customer";
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery();

				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				tableModel.setRowCount(0); // Clear the table before adding new rows

				while (rs.next()) {
					Object[] row = new Object[tableModel.getColumnCount()];
					for (int i = 0; i < row.length; i++) {
						row[i] = rs.getObject(i + 1);
					}
					tableModel.addRow(row);
				}

				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		});


		centerPanel.add(buttonPanel, BorderLayout.SOUTH);

		add(centerPanel, BorderLayout.CENTER);

		// Populate the table with customer data
		refresh(); // call refresh method initially
	}

	private void search() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
		table.setRowSorter(sorter);

		String query = searchField.getText();
		if (query.length() == 0) {
			sorter.setRowFilter(null);
		} else {
			sorter.setRowFilter(RowFilter.regexFilter(query));
		}
	}

	public void refresh() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/kc_energycompany", "root", "password@123");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM customer");
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String phoneNumber = rs.getString("phone_number");
				String currentAddress = rs.getString("current_address");
				String energyTariff = rs.getString("energy_tariff");
				String meterType = rs.getString("meter_type");
				Object[] rowData = {id, name, phoneNumber, currentAddress, energyTariff, meterType};
				model.addRow(rowData);
			}
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}



