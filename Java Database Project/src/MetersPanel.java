import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

public class MetersPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField searchField;
    private JButton searchButton;
    private JTable table;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton refreshButton;
    private int currentPage = 1;
	private int pageSize = 200;

    public MetersPanel() {
    	
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
        model.addColumn("ID");
        model.addColumn("Customer ID");
        model.addColumn("Days Since Reading");
        model.addColumn("Reading Date (YYYY-MM-DD)");
        model.addColumn("KWh (Meter Reading)");
        model.addColumn("KWh Energy Tariff Rate");

        try {
            // Establish a connection to the database
        	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/kc_energycompany", "root", "password@123");

            // Execute a query to retrieve the data from the customer_consumption table
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customer_consumption");

            // Populate the table model with the data
            while (resultSet.next()) {
                model.addRow(new Object[] {
                        resultSet.getInt("id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getDouble("days_used"),
                        resultSet.getDate("reading_date"),
                        resultSet.getDouble("reading_value"),
                        resultSet.getDouble("tariff_value")
                });
            }

            // Close the database connection
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        addButton = new JButton("Add");
        buttonPanel.add(addButton);
        addButton.addActionListener(e -> {
			AddMeterInfo addWindow = new AddMeterInfo();
			addWindow.setVisible(true);
		});

        editButton = new JButton("Edit");
		buttonPanel.add(editButton);
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
		        EditMeterInfo editWindow = new EditMeterInfo();
		        editWindow.setMeterInfo(rowData);

		        // Add an ActionListener to the "Save" button in the edit window
		        editWindow.addSaveButtonListener(ee -> {
		            int id = (int) rowData[0];
		            String customer_id = editWindow.getCustomerField().getText();
		            String days_used = editWindow.getDaysField().getText();
		            String reading_date = editWindow.getDateField().getText();
		            String reading_value = editWindow.getReadingField().getText();
		            String tariff_value = editWindow.getTariffField().getText();

		            // Update the customer's information in the database
		            try {
		                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/kc_energycompany", "root", "password@123");
		                String query = "UPDATE customer_consumption SET customer_id=?, days_used=?, reading_date=?, reading_value=?, tariff_value=? WHERE id=?";
		                PreparedStatement stmt = conn.prepareStatement(query);
		                stmt.setString(1, customer_id);
		                stmt.setString(2, days_used);
		                stmt.setString(3, reading_date);
		                stmt.setString(4, reading_value);
		                stmt.setString(5, tariff_value);
		                stmt.setInt(6, id);
		                stmt.executeUpdate();
		                conn.close();

		                // Update the customer's information in the table model
		                rowData[1] = customer_id;
		                rowData[2] = days_used;
		                rowData[3] = reading_date;
		                rowData[4] = reading_value;
		                rowData[5] = tariff_value;
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

		// add delete button and action listener
		deleteButton = new JButton("Delete");
		buttonPanel.add(deleteButton);
		deleteButton.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
			
			// if no row is selected give error
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				Object[] rowData = new Object[tableModel.getColumnCount()];
				for (int i = 0; i < rowData.length; i++) {
					rowData[i] = tableModel.getValueAt(selectedRow, i);
				}
				// Display a confirmation that shows what will be deleted
				int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this row?\n\n" + 
						"Customer Consumption ID: " + rowData[0] + "\n" +
						"Customer ID: " + rowData[1] + "\n" +
						"Days Used: " + rowData[2] + "\n" +
						"Reading Date: " + rowData[3] + "\n" +
						"Total Watt Usage: " + rowData[4] + "\n" +
						"Meter Fee: " + rowData[5], "Confirm Delete", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					deleteRow();
					tableModel.removeRow(selectedRow);
				}
			}
		});
		

		refreshButton = new JButton("Refresh"); // add refresh button
		refreshButton.addActionListener(e -> refresh()); // add ActionListener that calls the refresh(); method
		buttonPanel.add(refreshButton); // add to button panel

		centerPanel.add(buttonPanel, BorderLayout.SOUTH);

		add(centerPanel, BorderLayout.CENTER);


		// Populate the table with customer data
		refresh(); // call refresh method initially
	}

    // Be able to filter the table by what is searched
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

	// Fetch changes done to the database
	private void refresh() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);

		try {
			// Create a database connection
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/kc_energycompany", "root", "password@123");

			// Create the SQL query to retrieve the customer information
			String query = "SELECT id, customer_id, days_used, reading_date, reading_value, tariff_value FROM customer_consumption ORDER BY id LIMIT ? OFFSET ?";

			// Create a PreparedStatement object to execute the query with parameters for pagination
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, pageSize);
			stmt.setInt(2, (currentPage - 1) * pageSize);

			// Execute the query and retrieve the results
			ResultSet rs = stmt.executeQuery();

			// Add each row of customer data to the table model
			while (rs.next()) {
				Object[] row = {rs.getInt("id"), rs.getString("customer_id"), rs.getString("days_used"), rs.getString("reading_date"), rs.getString("reading_value"), rs.getString("tariff_value")};
				model.addRow(row);
			}

			// Close the database connection
			conn.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
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
	        String queryInvoice = "DELETE FROM customer_consumption WHERE customer_id = ?";

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
