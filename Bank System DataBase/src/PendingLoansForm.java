import javax.swing.*;
import java.awt.*;

public class PendingLoansForm extends JDialog{
    private JPanel PendingLoansPannel;
    private JTable PendingLoansTable;
    private JButton cancelButton;
    public PendingLoansForm(JFrame parent)  // Constructor.
    {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Pending Loans List");
        setContentPane(PendingLoansPannel);
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
