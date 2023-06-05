import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditCustomerWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField nameField;
	private JTextField phoneNumberField;
	private JTextField currentAddressField;
	private JTextField energyTariffField;
	private JTextField meterTypeField;
	private JButton saveButton;

	public EditCustomerWindow() {
		setTitle("Edit Customer");
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
		JLabel nameLabel = new JLabel("Name:");
		formPanel.add(nameLabel);
		nameField = new JTextField();
		formPanel.add(nameField);

		// Add the phone number field
		JLabel phoneNumberLabel = new JLabel("Phone Number:");
		formPanel.add(phoneNumberLabel);
		phoneNumberField = new JTextField();
		formPanel.add(phoneNumberField);

		// Add the current address field
		JLabel currentAddressLabel = new JLabel("Current Address:");
		formPanel.add(currentAddressLabel);
		currentAddressField = new JTextField();
		formPanel.add(currentAddressField);

		// Add the energy tariff field
		JLabel energyTariffLabel = new JLabel("Energy Tariff:");
		formPanel.add(energyTariffLabel);
		energyTariffField = new JTextField();
		formPanel.add(energyTariffField);

		// Add the meter type field
		JLabel meterTypeLabel = new JLabel("Meter Type:");
		formPanel.add(meterTypeLabel);
		meterTypeField = new JTextField();
		formPanel.add(meterTypeField);

		// Create the button panel
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 1;
		contentPane.add(buttonPanel, gbc);

		// Add the save button
		saveButton = new JButton("Save");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.LINE_END;
		buttonPanel.add(saveButton, gbc);

		// Set the default button
		getRootPane().setDefaultButton(saveButton);
	}

	public void setCustomerInfo(Object[] rowData) {
		nameField.setText(rowData[1].toString());
		phoneNumberField.setText(rowData[2].toString());
		currentAddressField.setText(rowData[3].toString());
		energyTariffField.setText(rowData[4].toString());
		meterTypeField.setText(rowData[5].toString());
	}

	public JTextField getNameField() {
		return nameField;
	}

	public JTextField getPhoneNumberField() {
		return phoneNumberField;
	}

	public JTextField getCurrentAddressField() {
		return currentAddressField;
	}

	public JTextField getEnergyTariffField() {
		return energyTariffField;
	}

	public JTextField getMeterTypeField() {
		return meterTypeField;
	}

	public void addSaveButtonListener(ActionListener listener) {
		saveButton.addActionListener(listener);
	}
}