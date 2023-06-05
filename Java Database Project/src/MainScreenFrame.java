import java.awt.*;
import javax.swing.*;

public class MainScreenFrame extends JFrame {
	// Serial version UID to ensure compatibility across different versions
	private static final long serialVersionUID = 1L;
	// JTabbedPane to hold the panels for each tab
	private JTabbedPane tabbedPane;

	public MainScreenFrame() {
		// Set the title, size, location and resize properties of the JFrame
		setTitle("Energy Supplier Management System");
		setSize(900, 600);
		setLocationRelativeTo(null);
		setResizable(true);

		// Create a new JPanel and set its layout to BorderLayout
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		// Create a new JTabbedPane to hold the panels for each tab
		tabbedPane = new JTabbedPane();
		// Add the CustomersPanel to the first tab
		tabbedPane.addTab("Customers", new CustomersPanel());
		// Add the InvoicesPanel to the second tab
		tabbedPane.addTab("Invoices", new InvoicesPanel());
		// Add the InvoicesPanel to the second tab
		tabbedPane.addTab("Energy Consumption", new MetersPanel());

		// Add the JTabbedPane to the center of the JPanel
		panel.add(tabbedPane, BorderLayout.CENTER);

		// Add the JPanel to the JFrame
		add(panel);
	}
}
