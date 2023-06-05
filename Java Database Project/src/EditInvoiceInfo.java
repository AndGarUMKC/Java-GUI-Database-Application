import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditInvoiceInfo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField customer_id;
	private JTextField invoice_date;
	private JTextField total_amount;
	private JTextField paid;
	private JButton saveButton;
	private JButton cancelButton;


	public EditInvoiceInfo() {
		setTitle("Edit Invoice Info");
		setSize(400, 300);
		setResizable(false);
		setLocationRelativeTo(null); // Center the window on the screen
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Create the content pane
		JPanel contentPane = new JPanel(new GridBagLayout());
		setContentPane(contentPane);

		// Create the form panel
		JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		contentPane.add(formPanel, gbc);

		// Add the name field
		JLabel nameLabel = new JLabel("Customer ID:");
		formPanel.add(nameLabel);
		customer_id = new JTextField();
		formPanel.add(customer_id);

		// Add the phone number field
		JLabel phoneNumberLabel = new JLabel("Invoice Date (YYYY-MM-DD):");
		formPanel.add(phoneNumberLabel);
		invoice_date = new JTextField();
		formPanel.add(invoice_date);

		// Add the current address field
		JLabel currentAddressLabel = new JLabel("Meter Fee:");
		formPanel.add(currentAddressLabel);
		total_amount = new JTextField();
		formPanel.add(total_amount);

		// Add the energy tariff field
		JLabel energyTariffLabel = new JLabel("Paid (Yes/No?):");
		formPanel.add(energyTariffLabel);
		paid = new JTextField();
		formPanel.add(paid);


		// Create the button panel
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 1;
		contentPane.add(buttonPanel, gbc);

		// Add the save button
		saveButton = new JButton("Save");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 0, 10);
		gbc.anchor = GridBagConstraints.LINE_END;
		buttonPanel.add(saveButton, gbc);

		// Add the cancel button
		cancelButton = new JButton("Cancel");
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 10, 0, 0);
		gbc.anchor = GridBagConstraints.LINE_START;
		buttonPanel.add(cancelButton, gbc);

		// Set the default button
		getRootPane().setDefaultButton(saveButton);
	}

	public void setInvoiceInfo(Object[] rowData) {
	    if (rowData.length >= 4) {
	        customer_id.setText(rowData[1].toString());
	        invoice_date.setText(rowData[2].toString());
	        total_amount.setText(rowData[3].toString());
	        paid.setText(rowData[4].toString());
	    } else {
	        // Handle the case where rowData doesn't have enough elements
	        System.err.println("Row data array has insufficient length.");
	    }
	}


	public JTextField getCustomerField() {
		return customer_id;
	}

	public JTextField getDateField() {
		return invoice_date;
	}

	public JTextField getAmountField() {
		return total_amount;
	}

	public JTextField getPaidField() {
		return paid;
	}


	public void addSaveButtonListener(ActionListener listener) {
		saveButton.addActionListener(listener);
	}
	
	public void addCancelButtonListener(ActionListener listener) {
		cancelButton.addActionListener(listener);
}}