import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;


public class InvoicesPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField searchField;
	private JButton searchButton;
	private JButton addButton;
	private JButton editButton;
	private JButton deleteButton;
	private JButton refreshButton;
	private JLabel blank;
	private JTable table;
	private JButton generateButton;
	private int currentPage = 1;
	private int pageSize = 200;


	public InvoicesPanel() {
		setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		searchField = new JTextField(20);
		topPanel.add(searchField);

		searchButton = new JButton("Search");
		searchButton.addActionListener(e -> search());
		topPanel.add(searchButton);

		blank = new JLabel("             ");
		topPanel.add(blank);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		addButton = new JButton("Add");
		topPanel.add(addButton);
		addButton.addActionListener(e -> {
			AddInvoiceInfo addWindow = new AddInvoiceInfo();
			addWindow.setVisible(true);

		});

		editButton = new JButton("Edit");
		topPanel.add(editButton);
		editButton.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
			int rowCount = table.getRowCount();
			if (selectedRow < 0 || selectedRow >= rowCount) {
				JOptionPane.showMessageDialog(this, "Please select a row to edit.", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				Object[] rowData = new Object[tableModel.getColumnCount()];
				rowData[0] = tableModel.getValueAt(selectedRow, 1);

				for (int i = 0; i < rowData.length; i++) {
					rowData[i] = tableModel.getValueAt(selectedRow, i);
				}

				// Create a new window to edit the customer's information
				EditInvoiceInfo editWindow = new EditInvoiceInfo();
				editWindow.setInvoiceInfo(rowData);

				// Add an ActionListener to the "Save" button in the edit window
				editWindow.addSaveButtonListener(ee -> {
					int id = (int) rowData[0];
					String customer_id = editWindow.getCustomerField().getText();
					String invoice_date = editWindow.getDateField().getText();
					String total_amount = editWindow.getAmountField().getText();
					String paid = editWindow.getPaidField().getText();


					// Update the customer's information in the database
					try {
						Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/kc_energycompany", "root", "password@123");
						String query = "UPDATE invoice SET customer_id=?, invoice_date=?, total_amount=?, paid=? WHERE id=?";
						PreparedStatement stmt = conn.prepareStatement(query);
						stmt.setString(1, customer_id);
						stmt.setString(2, invoice_date);
						stmt.setString(3, total_amount);
						stmt.setString(4, paid);
						stmt.setInt(5, id);
						stmt.executeUpdate();
						conn.close();

						// Update the customer's information in the table model
						rowData[1] = customer_id;
						rowData[2] = invoice_date;
						rowData[3] = total_amount;
						rowData[4] = paid;
						tableModel.fireTableDataChanged();

						// Close the edit window
						editWindow.dispose();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}

				});


				// Add an ActionListener to the "Cancel" button in the edit window
				editWindow.addCancelButtonListener(ee -> {
					// Close the edit window
					editWindow.dispose();
				});

				editWindow.setVisible(true);
			}
		});


		deleteButton = new JButton("Delete");
		topPanel.add(deleteButton);
		deleteButton.addActionListener(e -> {
		    int selectedRow = table.getSelectedRow();
		    if (selectedRow == -1) {
		        JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
		    } else {
		        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		        int columnCount = table.getColumnCount();
		        Object[] rowData = new Object[columnCount];
		        for (int i = 0; i < columnCount; i++) {
		            rowData[i] = table.getValueAt(selectedRow, i);
		        }

		        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this row?\n\n" + 
		                "Invoice ID: " + rowData[0] + "\n" +
		                "Customer ID: " + rowData[1] + "\n" +
		                "Invoice Date: " + rowData[2] + "\n" +
		                "Total Amount: " + rowData[3] + "\n" +
		                "Paid (Yes/No?): " + rowData[4] + "\n", "Confirm Delete", JOptionPane.YES_NO_OPTION);
		        if (result == JOptionPane.YES_OPTION) {
		            deleteRow();
		            tableModel.removeRow(selectedRow);
		        }
		    }
		});


		refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(e -> refresh()); // add ActionListener
		topPanel.add(refreshButton);


		add(topPanel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());

		// Initialize the table model and set it to the JTable
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Invoice ID");
		model.addColumn("Customer ID");
		model.addColumn("Invoice Date");
		model.addColumn("Meter Fee");
		model.addColumn("Paid?:");

		// Retrieve data from the database and add it to the table model
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kc_energycompany", "root", "password@123");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM invoice");

			while (rs.next()) {
				int id = rs.getInt("id");
				String customerId = rs.getString("customer_id");
				Date invoiceDate = rs.getDate("invoice_date");
				double amount = rs.getDouble("total_amount");
				String paid = rs.getString("paid");
				model.addRow(new Object[] {id, customerId, invoiceDate, amount, paid});
			}
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}


		// Populate the table with customer data
		refresh(); // call refresh method initially



		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		centerPanel.add(scrollPane, BorderLayout.CENTER);

		// Add the generate button to the button panel
		generateButton = new JButton("Generate Invoice");
		centerPanel.add(generateButton, BorderLayout.SOUTH);
		add(centerPanel, BorderLayout.CENTER);

		generateButton.addActionListener(new ActionListener() {
			@Override

			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(InvoicesPanel.this, "Please select an invoice.");
					return;
				}

				// Get the selected invoice's data from the table model
				int invoiceId = (int) table.getValueAt(row, 0);
				String customerId = (String) table.getValueAt(row, 1);
				//java.sql.Date invoiceDate = (java.sql.Date) table.getValueAt(row, 2);
				Object invoiceDateObj = table.getValueAt(row, 2);
				java.sql.Date invoiceDate = null;
				try {
				    invoiceDate = (java.sql.Date) invoiceDateObj;
				} catch (ClassCastException e2) {
				    String invoiceDateStr = (String) invoiceDateObj;
				    invoiceDate = java.sql.Date.valueOf(invoiceDateStr);
				}
				

				// Get the customer's name and address from the customers table
				String customerName = "";
				String customerAddress = "";
				String energyType = "";
				String meterType = "";
				double readingValue = 0.0;
				double energyRate = 0.0;
				double meterFee = 0.0;

				try {
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kc_energycompany", "root", "password@123");
					PreparedStatement stmt = conn.prepareStatement("SELECT name, current_address, energy_tariff, meter_type FROM customer WHERE id = ?");
					stmt.setString(1, customerId);
					ResultSet rs = stmt.executeQuery();

					if (rs.next()) {
						customerName = rs.getString("name");
						customerAddress = rs.getString("current_address");
						energyType = rs.getString("energy_tariff");
						meterType = rs.getString("meter_type");
					}

					stmt = conn.prepareStatement("SELECT reading_value, tariff_value FROM customer_consumption WHERE customer_id = ?");
					stmt.setString(1, customerId);
					rs = stmt.executeQuery();

					if (rs.next()) {
						readingValue = rs.getDouble("reading_value");
						energyRate = rs.getDouble("tariff_value");
					}

					stmt = conn.prepareStatement("SELECT total_amount FROM invoice WHERE customer_id = ?");
					stmt.setString(1, customerId);
					rs = stmt.executeQuery();

					if (rs.next()) {
						meterFee = rs.getDouble("total_amount");
					}

					conn.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}

				// Calculate the subtotal
				double subtotal = (readingValue * energyRate) + meterFee;

				// Calculate the tax
				double tax = subtotal * 0.065;

				// Calculate the final total
				double finalTotal = subtotal + tax;


				// Create a new JFrame window for the invoice details
				JFrame invoiceFrame = new JFrame("Invoice");
				invoiceFrame.setSize(600, 500);
				invoiceFrame.setLocationRelativeTo(null);
				invoiceFrame.setLayout(new BorderLayout());

				JPanel topPanel = new JPanel();
				topPanel.setLayout(new GridLayout(4, 1));

				// Add the customer name and address
				JLabel nameLabel = new JLabel("Customer Name: " + customerName);
				JLabel addressLabel = new JLabel("Customer Address: " + customerAddress);
				topPanel.add(nameLabel);
				topPanel.add(addressLabel);

				// Add KC Energy Company and invoice details
				JLabel companyLabel = new JLabel("KC Energy Company");
				JLabel invoiceNumberLabel = new JLabel("Invoice ID#: " + invoiceId);
				JLabel dateLabel = new JLabel("Invoice Date: " + invoiceDate);
				topPanel.add(companyLabel);
				topPanel.add(invoiceNumberLabel);
				topPanel.add(dateLabel);

				invoiceFrame.add(topPanel, BorderLayout.NORTH);

				// Add the invoice description and amount
				JPanel centerPanel = new JPanel();
				centerPanel.setLayout(new BorderLayout());

				String description = "\n\tInvoice for customerID: " + customerId + "\n" +
						"\tCustomer Name: " + customerName + "\n" +
						"\tAddress: " + customerAddress + "\n\n" +
						"\tKC Energy Company\n" +
						"\tInvoice Date: " + invoiceDate + "\n\n" + "\tBreakdown of charges for this month:\n\n" +
						"\tYou are using " + energyType + " which has a energy tarrif rate of " + energyRate + " KW/h\n\tas well as having a " + meterType +
						" meter which has a fee of $" + meterFee + " and all of \n\tthese are factors which make up your bill.\n\n"+ "\tThis means that your total is calculated as follows...\n\n" + 
						"\tSubtotal: (" + readingValue + " KWh x " + energyRate + ") + $" + meterFee + "\n\tTotal: $" + subtotal + " x Sales tax of 6.5%\n\n" +
						"\tPlease pay the balance of $" + String.format("%.2f", finalTotal) + " within the next 30 days.";

				JTextArea descriptionArea = new JTextArea(description);

				descriptionArea.setEditable(false);
				centerPanel.add(descriptionArea, BorderLayout.CENTER);

				JLabel amountLabel = new JLabel("\tTotal Charges: $" + String.format("%.2f", finalTotal));
				centerPanel.add(amountLabel, BorderLayout.SOUTH);

				invoiceFrame.add(centerPanel, BorderLayout.CENTER);

				// Add the send button
				JPanel bottomPanel = new JPanel();
				bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

				JButton sendButton = new JButton("Make PDF");
				bottomPanel.add(sendButton);

				invoiceFrame.add(bottomPanel, BorderLayout.SOUTH);

				invoiceFrame.setVisible(true);
			}
		});
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

	private void refresh() {
		if (this.table != null) { // add null check for this.table
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setRowCount(0);

			try {
				// Create a database connection
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/kc_energycompany", "root", "password@123");

				// Create the SQL query to retrieve the customer information
				String query = "SELECT id, customer_id, invoice_date, total_amount, paid FROM invoice ORDER BY id LIMIT ? OFFSET ?";

				// Create a PreparedStatement object to execute the query with parameters for pagination
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setInt(1, pageSize);
				stmt.setInt(2, (currentPage - 1) * pageSize);

				// Execute the query and retrieve the results
				ResultSet rs = stmt.executeQuery();

				// Add each row of customer data to the table model
				while (rs.next()) {
					Object[] row = {rs.getInt("id"), rs.getString("customer_id"), rs.getString("invoice_date"), rs.getString("total_amount"), rs.getString("paid")};
					model.addRow(row);
				}

				// Close the database connection
				conn.close();

			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void deleteRow() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Please select a row to delete.");
			return;
		}

		// Display a dialog to confirm the deletion
		int confirmResult = JOptionPane.showConfirmDialog(this, "This Customer Meter Info will now be deleted from the system. \nTo proceed click Yes. To cancel click No\n\n Please Click Refresh after your choice, to reflect these changes.", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
		if (confirmResult != JOptionPane.YES_OPTION) {
			return;
		}

		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int modelRow = table.convertRowIndexToModel(selectedRow);

		try {
			// Create a database connection
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/kc_energycompany", "root", "password@123");

			// Create the SQL query to delete the rows from invoice table that correspond to the customer row being deleted
			String queryInvoice = "DELETE FROM invoice WHERE customer_id = ?";

			// Create a PreparedStatement object to execute the invoice query
			PreparedStatement stmtInvoice = conn.prepareStatement(queryInvoice);
			stmtInvoice.setInt(1, (int) model.getValueAt(modelRow, 0));
			stmtInvoice.executeUpdate();	

			// Create the SQL query to delete the row from customer table
			String queryCustomer = "DELETE FROM customer_consumption WHERE id = ?";

			// Create a PreparedStatement object to execute the customer query
			PreparedStatement stmtCustomer = conn.prepareStatement(queryCustomer);
			stmtCustomer.setInt(1, (int) model.getValueAt(modelRow, 0));
			stmtCustomer.executeUpdate();

			// Close the database connection
			conn.close();

			// Remove the row from the table model
			//model.removeRow(modelRow);
		} catch (SQLException ex) {
			// Handle any errors
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error deleting row: " + ex.getMessage());
		}
	}
}