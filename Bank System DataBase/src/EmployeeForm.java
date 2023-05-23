import javax.swing.*;
import java.awt.*;

public class EmployeeForm extends JDialog {
    private JPanel EmployeePanel;
    private JButton addCustomerButton;
    private JButton LogOutButton;
    private JButton updateCustomerButton;
    private JButton showCustomersButton;
    private JButton LoansButton;
    private JButton payButton;
    private JButton pendingLoansButton;
    private JButton addLoanButton;
    private final int SSN;
    public EmployeeForm(JFrame parent,int ssn)  // Constructor.
    {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Employee");
        setContentPane(EmployeePanel);
        setMinimumSize(new Dimension(820, 420));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);

        SSN = ssn;

    //==================================================================================================================

        addCustomerButton.addActionListener(e -> {  // Button to add a new customer.
            dispose();
            new AddCustomerForm(null,SSN);

        });

    //==================================================================================================================

        updateCustomerButton.addActionListener(e -> {   // Button to update a customer.
            dispose();
            new UpdateCustomerForm(null,SSN);

        });

    //==================================================================================================================

        addLoanButton.addActionListener(e -> {

            dispose();
            new AddLoanForm(null,SSN);
            // When we press the button we go to the add loan form and close the current form.
        });

    //==================================================================================================================

        // Show List of customers of the same bank.
        showCustomersButton.addActionListener(e -> {
            dispose();
            new CustomersListForm(null,SSN);
        });

    //==================================================================================================================

        LoansButton.addActionListener(e -> {    // Button to show loan list.
            dispose();
            new LoansForm(null,SSN);

        });

    //==================================================================================================================

        LogOutButton.addActionListener(e -> {   // Button to return to the previous window.
            dispose();
            new Home(null);
        });

    //==================================================================================================================

        pendingLoansButton.addActionListener(e -> { // Button to show the pending loans.
            dispose();
            new PendingLoansForm(null,SSN);
        });

    //==================================================================================================================

        payButton.addActionListener(e -> {  // Button to pay a loan.
            dispose();
            new PayForm(null,SSN);
        });
    }

    //==================================================================================================================

}
