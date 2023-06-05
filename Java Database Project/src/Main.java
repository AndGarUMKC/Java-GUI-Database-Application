import javax.swing.SwingUtilities;

public class Main {
    
    public static void main(String[] args) {
        // Use SwingUtilities to run the program on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Create a new instance of the LoginFrame
                LoginFrame loginFrame = new LoginFrame();
                // Display the LoginFrame
                loginFrame.setVisible(true);
            }
        });
    }
}
