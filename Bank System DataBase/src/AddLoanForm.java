import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AddLoanForm extends JDialog{   // Constructor to set the Attributes.
    private JPanel AddLoanPanel;
    private JTextField tfLoanNumber;
    private JTextField tfLoanAmount;
    private JTextField tfBranchNumber;
    private JButton addLoanButton;
    private JButton cancelButton;
    private JTextField tfLoanType;
    private JTextField tfBankCode;
    private JPanel JPanel;
    int SSN;
    public AddLoanForm(JFrame parent, int ssn)  // Constructor.
    {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Add Loan");
        setContentPane(AddLoanPanel);
        setMinimumSize(new Dimension(820, 820));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);
        SSN = ssn;  // Assign the ssn of the employee to be returned again when the method is ended.

//======================================================================================================================

        addLoanButton.addActionListener(e -> {  // What will happen when the button is pressed.

            // Getting the fields from the panel.
            String branchNumber = tfBranchNumber.getText();
            String bankCode = tfBankCode.getText();
            String amount = tfLoanAmount.getText();
            String type = tfLoanType.getText();
            String loanNumber = tfLoanNumber.getText();

            // Checking the validation of the data.
            if (LoginForm.checkWords(type) &&
                    LoginForm.checkDouble(amount) &&
                    LoginForm.checkNumber(branchNumber) &&
                    LoginForm.checkNumber(bankCode) &&
                    LoginForm.checkNumber(loanNumber))
            {
                // Trying to push the data into the table.
                try {

                    DataBase  dataBase = new DataBase();
                    if( dataBase.addLoan(SSN, Integer.parseInt(loanNumber) , Integer.parseInt(branchNumber) , Integer.parseInt(bankCode) , Double.parseDouble(amount) , type ) )
                    {
                        JOptionPane.showMessageDialog(AddLoanForm.this,
                                "Loan has been added successfully",
                                "Successful Operation",
                                JOptionPane.INFORMATION_MESSAGE);   // if the operation succeeded.

                        dispose();  // close the current form.
                        new EmployeeForm(null,SSN); // return to the employee form.

                    }

                    else {

                        JOptionPane.showMessageDialog(AddLoanForm.this,
                                "Failed to add Loan",
                                "Operation Failed !!!",
                                JOptionPane.ERROR_MESSAGE); // If the operation failed.
                    }
                }

                catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });

//======================================================================================================================

        cancelButton.addActionListener(e -> {   // What will happen when we press the cancel button.
            dispose();  // Close the current Form.
            new EmployeeForm(null , SSN);   // Return to the employee form.
        });

//======================================================================================================================

    }

}
