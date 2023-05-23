import javax.swing.*;
import java.awt.*;

public class AdminForm extends  JDialog{
    private JPanel AdminPanel;
    private JButton addBankButton;
    private JButton addBranchButton;
    private JButton LogOutButton;
    private JButton addEmployeeButton;

    //==================================================================================================================

    public AdminForm(JFrame parent)  // Constructor.
    {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Admin");
        setContentPane(AdminPanel);
        setMinimumSize(new Dimension(820, 420));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);

    //==================================================================================================================

        // Button to add new bank.
        addBankButton.addActionListener(e -> {

            new AddBankForm(null);
            dispose();

        });

    //==================================================================================================================

        // Button to add new branch.
        addBranchButton.addActionListener(e -> {

            new AddBranch(null);
            dispose();

        });

    //==================================================================================================================

        // Button to add new employee.
        addEmployeeButton.addActionListener(e -> {
            new AddEmployeeForm (null);
            dispose();
        });

    //==================================================================================================================

        // Button to return to the home.
        LogOutButton.addActionListener(e -> {
            new Home(null);
            dispose();
        });

    //==================================================================================================================

    }
}
