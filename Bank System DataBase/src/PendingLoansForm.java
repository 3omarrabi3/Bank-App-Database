import javax.swing.*;
import java.awt.*;

public class PendingLoansForm extends JDialog{
    private JPanel PendingLoansPannel;
    private JTable PendingLoansTable;
    private JButton cancelButton;

    private int SSN;
    public PendingLoansForm(JFrame parent,int ssn)  // Constructor.
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

        SSN = ssn;
        cancelButton.addActionListener(e -> {
            new EmployeeForm(null,SSN);
            dispose();
        });
    }
}
