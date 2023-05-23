import javax.swing.*;
import java.awt.*;

public class CustomersForm extends JDialog{
    private JPanel CustomerPanel;
    private JButton requestButton;
    private JButton startButton;
    private JButton LogOutButton;

    private final int SSN;

    public CustomersForm(JFrame parent,int ssn)  // Constructor.
    {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Customers");
        setContentPane(CustomerPanel);
        setMinimumSize(new Dimension(820, 480));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);
        SSN =ssn;

    //==================================================================================================================
        requestButton.addActionListener(e -> {  // Button of request loan.

            new RequestForm(null,SSN);
            dispose();

        });

    //==================================================================================================================

        startButton.addActionListener(e -> {    // Button of start Payment of a loan.

            new StartForm(null,SSN);
            dispose();

        });

    //==================================================================================================================

        LogOutButton.addActionListener(e -> {   // Button of returning to home.
            new Home(null);
            dispose();
        });
    //==================================================================================================================

    }
}
