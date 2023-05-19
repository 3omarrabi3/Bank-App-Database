import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCustomerForm extends JDialog{
    private JPanel AddCustomerPanel;
    private JButton addCustomerButton;
    private JButton cancelButton;
    private javax.swing.JPanel JPanel;
    private JTextField tfFirstName;
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JTextField tfLastName;
    private JTextField tfPhone;
    private JTextField tfCountry;
    private JTextField tfCity;
    private JTextField tfStreet;
    private JTextField tfBuildingNumber;
    private JTextField tfAccountType;
    private JTextField tfAccountNumber;
    private JTextField tfAccountBalance;

    public AddCustomerForm(JFrame parent)  // Constructor.
    {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Add Customer");
        setContentPane(AddCustomerPanel);
        setMinimumSize(new Dimension(820, 820));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);

        addCustomerButton.addActionListener(e -> {

            new AddBankForm(null);
            dispose();

        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminForm(null);
                dispose();
            }
        });
    }
}