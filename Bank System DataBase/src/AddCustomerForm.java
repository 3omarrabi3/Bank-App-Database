import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AddCustomerForm extends JDialog{
    private JPanel AddCustomerPanel;
    private JButton addCustomerButton;
    private JButton cancelButton;
    private javax.swing.JPanel JPanel;
    private JTextField tfFirstName;
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JTextField tfLastName;
    private JTextField tfPhone;
    private JTextField tfCountry;
    private JTextField tfCity;
    private JTextField tfStreet;
    private JTextField tfBuildingNumber;
    private JTextField tfAccountType;
    private JTextField tfAccountNumber;
    private JTextField tfAccountBalance;
    private JTextField tfSSN;
    private JTextField tfBranchNumber;
    private JTextField tfBankCode;
    int SSN;

    //==================================================================================================================

    public AddCustomerForm(JFrame parent,int ssn)  // Constructor.
    {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Add Customer");
        setContentPane(AddCustomerPanel);
        setMinimumSize(new Dimension(820, 820));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);

        SSN =ssn;

    //==================================================================================================================

        addCustomerButton.addActionListener(e -> {

                if (LoginForm.checkWords(tfFirstName.getText()) &&
                        LoginForm.checkWords(tfLastName.getText()) &&
                        LoginForm.checkWords(tfCity.getText()) &&
                        LoginForm.checkWords(tfCountry.getText()) &&
                        LoginForm.checkNumber(tfSSN.getText()) &&
                        LoginForm.checkNumber(tfPhone.getText()) &&
                        LoginForm.checkEmail(tfEmail.getText()) &&
                        LoginForm.checkNumber(tfBranchNumber.getText()) &&
                        LoginForm.checkNumber(tfAccountNumber.getText()) &&
                        LoginForm.checkNumber(tfBankCode.getText()) &&
                        LoginForm.checkDouble(tfAccountBalance.getText())) {

                    int SSN = Integer.parseInt(tfSSN.getText());
                    String firstName = tfFirstName.getText();
                    String lastName = tfLastName.getText();
                    int phone = Integer.parseInt(tfPhone.getText());
                    String street = tfStreet.getText();
                    String city = tfCity.getText();
                    String country = tfCountry.getText();
                    int buildingNumber = Integer.parseInt(tfBuildingNumber.getText());
                    String email = tfEmail.getText();
                    String password = new String(pfPassword.getPassword());
                    int branchNumber = Integer.parseInt(tfBranchNumber.getText());
                    int bankCode = Integer.parseInt(tfBankCode.getText());
                    String AccountType = tfAccountType.getText();
                    int AccountNumber = Integer.parseInt(tfAccountNumber.getText());
                    double AccountBalance = Double.parseDouble(tfAccountBalance.getText());

                    DataBase  dataBase;
                    try {
                        dataBase = new DataBase();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        if(!(dataBase.checkExistanceAddCustomer(branchNumber, bankCode)))
                        {
                            JOptionPane.showMessageDialog(AddCustomerForm.this,
                                    "This data doesn't exist",
                                    "Login Failed!",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }


                    JOptionPane.showMessageDialog(AddCustomerForm.this,
                            "Customer and his account has been Added successfully",
                            "Successful Operation",
                            JOptionPane.INFORMATION_MESSAGE);
                    new EmployeeForm(null, ssn);
                    dispose();


                    try {

                        dataBase.addCustomer(SSN, firstName, lastName, phone,
                                street, city, country, buildingNumber,
                                email, password, branchNumber, bankCode);
                        dataBase.addAccount(AccountNumber, SSN, AccountType, AccountBalance, branchNumber, bankCode);
                    }
                    catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
            }


        });

    //==================================================================================================================

        cancelButton.addActionListener(e -> {
            new EmployeeForm(null,ssn);
            dispose();
        });

    //==================================================================================================================
    }
}
