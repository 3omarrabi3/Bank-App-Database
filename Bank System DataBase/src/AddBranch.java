import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;


public class AddBranch extends JDialog
{
    private JPanel AddBranchPanel;
    private JTextField tfBankCode;
    private JTextField tfStreet;
    private JTextField tfCountry;
    private JButton cancelButton;
    private JButton addBranchButton;
    private JTextField tfCity;
    private JTextField tfBranchNumber;
    private JTextField tfBranchName;

    //==================================================================================================================

    public AddBranch(JFrame parent) {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Admin");
        setContentPane(AddBranchPanel);
        setMinimumSize(new Dimension(820, 820));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);

        //==================================================================================================================

        addBranchButton.addActionListener(e -> {    // Button to add the branch.

            String branchNumber = tfBranchNumber.getText();
            String bankCode = tfBankCode.getText();
            String branchName = tfBranchName.getText();
            String country = tfCountry.getText();
            String city = tfCity.getText();
            String street = tfStreet.getText();

            if (LoginForm.checkWords(branchName) &&
                    LoginForm.checkWords(country) &&
                    LoginForm.checkWords(city) &&
                    LoginForm.checkNumber(branchNumber) &&
                    LoginForm.checkNumber(bankCode)) {


                try {
                    DataBase  dataBase = new DataBase();
                    if(dataBase.addBranch(Integer.parseInt(branchNumber),Integer.parseInt(bankCode),branchName,street,city,country))
                    {
                        JOptionPane.showMessageDialog(AddBranch.this,
                                "Branch has been added successfully",
                                "Successful Operation",
                                JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new AdminForm(null);
                    }
                    else {
                        JOptionPane.showMessageDialog(AddBranch.this,
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

        cancelButton.addActionListener(e -> {   // Button to return to the previous menu.
            new AdminForm(null);
            dispose();

        });

        //==================================================================================================================

    }
}

