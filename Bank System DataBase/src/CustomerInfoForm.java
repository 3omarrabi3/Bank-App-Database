import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class CustomerInfoForm extends JDialog{
    private JPanel CustomerInfoPanel;
    private javax.swing.JPanel JPanel;
    private JTextField tfFirstName;
    private JTextField tfEmail;
    private JTextField tfPassword;
    private JTextField tfLastName;
    private JTextField tfPhone;
    private JTextField tfCountry;
    private JTextField tfCity;
    private JTextField tfStreet;
    private JTextField tfBuildingNumber;
    private JTextField tfAccountType;
    private JTextField tfAccountBalance;
    private JButton updateCustomerButton;
    private JButton cancelButton;
    private JTextField tfBranchNumber;
    private JTextField tfBankCode;

    private final int CustomerSSN;
    private final int EmployeeSSN;

    //==================================================================================================================
    public CustomerInfoForm(JFrame parent,int employeeSSN,int customerSSN,
                            String FirstName, String LastName,String Email , String Password, String Country , String City , String Street
            , String AccountType , int BankCode, int BranchNumber, int BuildingNumber , int Phone ,double Balance )  // Constructor.
    {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Customer Info");
        setContentPane(CustomerInfoPanel);
        setMinimumSize(new Dimension(820, 820));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);

        tfFirstName.setText(FirstName);
        tfLastName.setText(LastName);
        tfEmail.setText(Email);
        tfPhone.setText(String.valueOf(Phone));
        tfAccountBalance.setText(String.valueOf(Balance));
        tfBranchNumber.setText(String.valueOf(BranchNumber));
        tfBankCode.setText(String.valueOf(BankCode));
        tfStreet.setText(Street);
        tfCity.setText(City);
        tfCountry.setText(Country);
        tfPassword.setText(Password);
        tfAccountType.setText(AccountType);
        tfBuildingNumber.setText(String.valueOf(BuildingNumber));


        EmployeeSSN = employeeSSN;
        CustomerSSN = customerSSN;

    //==================================================================================================================

        // Button to update the customer.
        updateCustomerButton.addActionListener(e ->{ if (LoginForm.checkWords(tfFirstName.getText()) &&
                LoginForm.checkWords(tfLastName.getText()) &&
                LoginForm.checkWords(tfCity.getText()) &&
                LoginForm.checkWords(tfCountry.getText()) &&
                LoginForm.checkNumber(tfPhone.getText()) &&
                LoginForm.checkEmail(tfEmail.getText()) &&
                LoginForm.checkNumber(tfBranchNumber.getText()) &&
                LoginForm.checkNumber(tfBankCode.getText()) &&
                LoginForm.checkDouble(tfAccountBalance.getText())) {

            String firstName = tfFirstName.getText();
            String lastName = tfLastName.getText();
            int phone = Integer.parseInt(tfPhone.getText());
            String street = tfStreet.getText();
            String city = tfCity.getText();
            String country = tfCountry.getText();
            int buildingNumber = Integer.parseInt(tfBuildingNumber.getText());
            String email = tfEmail.getText();
            String password = tfPassword.getText();
            int branchNumber = Integer.parseInt(tfBranchNumber.getText());
            int bankCode = Integer.parseInt(tfBankCode.getText());
            String accountType = tfAccountType.getText();
            double AccountBalance = Double.parseDouble(tfAccountBalance.getText());

            JOptionPane.showMessageDialog(CustomerInfoForm.this,
                    "Customer and his account has been Updated successfully",
                    "Successful Operation",
                    JOptionPane.INFORMATION_MESSAGE);
            new EmployeeForm(null, EmployeeSSN);
            dispose();

            try {
                DataBase dataBase = new DataBase();
                dataBase.setCustomer(
                        CustomerSSN, firstName, lastName, phone,
                        street, city, country, buildingNumber,
                        email, password, branchNumber,
                        bankCode);
                dataBase.setAccount(CustomerSSN,
                        accountType, AccountBalance,
                        branchNumber, bankCode);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }});

    //==================================================================================================================

        // Button to return to previous panel.
        cancelButton.addActionListener(e -> {
            new EmployeeForm(null,EmployeeSSN);
            dispose();
        });

    //==================================================================================================================
    }
}
