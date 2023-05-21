import javax.swing.*;
import java.awt.*;

public class PayForm extends JDialog{
    private JPanel PayPanel;
    private JTable PayTable;
    private JButton cancelButton;
    public PayForm(JFrame parent)  // Constructor.
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

        cancelButton.addActionListener(e -> {
            new EmployeeForm(null);
            dispose();
        });
    }
}
