import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeForm extends JDialog {
    private JPanel EmployeePanel;
    private JButton addCustomerButton;
    private JButton LogOutButton;
    private JButton updateCustomerButton;
    private JButton showCustomersButton;
    private JButton LoansButton;
    private JButton payButton;
    private JButton pendingLoansButton;
    private int SSN;
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

        addCustomerButton.addActionListener(e -> {
            dispose();
            new AddCustomerForm(null,SSN);

        });

        updateCustomerButton.addActionListener(e -> {
            dispose();
            new UpdateCustomerForm(null,SSN);

        });

        // Show List of customers of the same bank.
        showCustomersButton.addActionListener(e -> {
            dispose();
            new CustomersListForm(null,SSN);
        });
        LoansButton.addActionListener(e -> {
            dispose();
            new LoansForm(null,SSN);

        });
        LogOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Home(null);
            }
        });
        pendingLoansButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PendingLoansForm(null,SSN);
            }
        });
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PayForm(null,SSN);
            }
        });
    }

    int getSSN()
    {
        return SSN;
    }
}
