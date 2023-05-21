import javax.swing.*;
import java.awt.*;

public class PayForm extends JDialog{
    private JPanel PayPanel;
    private JTable PayTable;
    private JButton cancelButton;
    int SSN;
    public PayForm(JFrame parent , int ssn)  // Constructor.
    {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Pay for loan");
        setContentPane(PayPanel);
        setMinimumSize(new Dimension(820, 820));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);

        SSN = ssn;
        cancelButton.addActionListener(e -> {
            new EmployeeForm(null,SSN);
            dispose();
        });
    }
}
