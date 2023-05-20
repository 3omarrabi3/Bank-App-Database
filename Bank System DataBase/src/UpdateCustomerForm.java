import javax.swing.*;
import java.awt.*;

public class UpdateCustomerForm extends JDialog{
    private JPanel UpdateCustomerPanel;
    private JTextField tfSSN;
    private JButton getCustomerButton;
    private JButton cancelButton;

    public UpdateCustomerForm(JFrame parent)     // constructor that sets the suitable attributes for the form.
    {
        super(parent);
        setTitle("Login");
        setContentPane(UpdateCustomerPanel);
        setMinimumSize(new Dimension(820,440));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);

        //__________________________________________________________________________________________________________________

        getCustomerButton.addActionListener(e -> {
            String SSN = tfSSN.getText();
            new CustomerInfoForm (null);
            dispose();
        });

        // __________________________________________________________________________________________________________________

        cancelButton.addActionListener(e -> {
            new EmployeeForm(null);
            dispose();
        });
    }
}
