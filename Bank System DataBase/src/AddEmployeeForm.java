import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AddEmployeeForm extends JDialog{
    private JPanel AddEmployeePanel;
    private javax.swing.JPanel JPanel;
    private JTextField tfFirstName;
    private JTextField tfEmail;
    private JPasswordField tfPassword;
    private JTextField tfLastName;

    private JButton addEmployeeButton;
    private JButton cancelButton;
    private JTextField tfSSN;
    private JTextField tfBranchNumber;
    private JTextField tfBankCode;
    private JTextField tfSalary;

    public AddEmployeeForm(JFrame parent)  // Constructor.
    {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Add Employee");
        setContentPane(AddEmployeePanel);
        setMinimumSize(new Dimension(820, 820));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);

    //==================================================================================================================

        addEmployeeButton.addActionListener(e -> {  // Button to add Employee.
            String branchNumber = tfBranchNumber.getText();
            String bankCode = tfBankCode.getText();
            String firstName = tfFirstName.getText();
            String lastName = tfLastName.getText();
            String salary = tfSalary.getText();
            String SSN = tfSSN.getText();
            String email = tfEmail.getText();
            String password = new String(tfPassword.getPassword());

            if (LoginForm.checkWords(firstName) &&
                    LoginForm.checkWords(lastName) &&
                    LoginForm.checkEmail(email) &&
                    LoginForm.checkNumber(branchNumber) &&
                    LoginForm.checkNumber(bankCode) &&
                    LoginForm.checkDouble(salary) &&
                    LoginForm.checkNumber(SSN)
            ) {


                try {
                    DataBase  dataBase = new DataBase();
                    if(dataBase.addEmployee(Integer.parseInt(SSN),Double.parseDouble(salary),firstName,lastName,email,password,Integer.parseInt(branchNumber),Integer.parseInt(bankCode)))
                    {
                        JOptionPane.showMessageDialog(AddEmployeeForm.this,
                                "Employee has been added successfully",
                                "Successful Operation",
                                JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new AdminForm(null);
                    }
                    else {
                        JOptionPane.showMessageDialog(AddEmployeeForm.this,
                                "Failed to add branch",
                                "Operation Failed !!!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    //==================================================================================================================

        cancelButton.addActionListener(e -> {   // Button to return to previous form.
            dispose();
            new AdminForm(null);
        });

    //==================================================================================================================

    }

    //==================================================================================================================
}
