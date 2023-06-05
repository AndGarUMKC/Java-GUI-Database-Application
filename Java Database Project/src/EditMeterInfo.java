
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditMeterInfo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField customer_id;
	private JTextField days_used;
	private JTextField reading_date;
	private JTextField reading_value;
	private JTextField tariff_value;
	private JButton saveButton;
	private JButton cancelButton;

	public EditMeterInfo() {
		setTitle("Edit Customer's Meter Info");
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
		JLabel phoneNumberLabel = new JLabel("Days Since Reading:");
		formPanel.add(phoneNumberLabel);
		days_used = new JTextField();
		formPanel.add(days_used);

		// Add the current address field
		JLabel currentAddressLabel = new JLabel("Reading Date (YYYY-MM-DD):");
		formPanel.add(currentAddressLabel);
		reading_date = new JTextField();
		formPanel.add(reading_date);

		// Add the energy tariff field
		JLabel energyTariffLabel = new JLabel("KWh (Meter Reading):");
		formPanel.add(energyTariffLabel);
		reading_value = new JTextField();
		formPanel.add(reading_value);

		// Add the meter type field
		JLabel meterTypeLabel = new JLabel("KWh Energy Tariff Rate:");
		formPanel.add(meterTypeLabel);
		tariff_value = new JTextField();
		formPanel.add(tariff_value);

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

	public void setMeterInfo(Object[] rowData) {
		if (rowData.length >= 5) {
	        customer_id.setText(rowData[1].toString());
	        days_used.setText(rowData[2].toString());
	        reading_date.setText(rowData[3].toString());
	        reading_value.setText(rowData[4].toString());
	        tariff_value.setText(rowData[5].toString());
	    } else {
	        // Handle the case where rowData doesn't have enough elements
	        System.err.println("Row data array has insufficient length.");
	    }
	}

	public JTextField getCustomerField() {
		return customer_id;
	}

	public JTextField getDaysField() {
		return days_used;
	}

	public JTextField getDateField() {
		return reading_date;
	}

	public JTextField getReadingField() {
		return reading_value;
	}

	public JTextField getTariffField() {
		return tariff_value;
	}

	public void addSaveButtonListener(ActionListener listener) {
		saveButton.addActionListener(listener);
	}
	public void addCancelButtonListener(ActionListener listener) {
		cancelButton.addActionListener(listener);
}}