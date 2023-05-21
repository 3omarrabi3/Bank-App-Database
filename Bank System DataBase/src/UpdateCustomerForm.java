import javax.swing.*;
import java.awt.*;

public class UpdateCustomerForm extends JDialog{
    private JPanel UpdateCustomerPanel;
    private JTextField tfSSN;
    private JButton getCustomerButton;
    private JButton cancelButton;

    private int SSN;
    public UpdateCustomerForm(JFrame parent, int ssn)     // constructor that sets the suitable attributes for the form.
    {
        super(parent);
        setTitle("Login");
        setContentPane(UpdateCustomerPanel);
        setMinimumSize(new Dimension(820,440));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);

        SSN = ssn;
        //__________________________________________________________________________________________________________________

        getCustomerButton.addActionListener(e -> {
            int customerSSN =Integer.parseInt( tfSSN.getText());
            new CustomerInfoForm (null,SSN,customerSSN);
            dispose();
        });

        // __________________________________________________________________________________________________________________

        cancelButton.addActionListener(e -> {
            new EmployeeForm(null,SSN);
            dispose();
        });
    }
}
