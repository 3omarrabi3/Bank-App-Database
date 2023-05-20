import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoansForm extends JDialog{
    private JPanel LoansPannel;
    private JTable LoansTable;
    private JButton cancelButton;

    public LoansForm(JFrame parent)  // Constructor.
    {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Loans List");
        setContentPane(LoansPannel);
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
