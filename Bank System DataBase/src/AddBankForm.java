import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AddBankForm extends JDialog{

// Attributes of the form.
    private JPanel AddBankPanel;
    private JTextField tfBankName;
    private JTextField tfStreet;
    private JTextField tfCountry;
    private JButton cancelButton;
    private JButton addBankButton;
    private JTextField tfCity;
    private JTextField tfBankCode;

    //==================================================================================================================

    public AddBankForm(JFrame parent)  // Constructor.
    {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Add Bank");
        setContentPane(AddBankPanel);
        setMinimumSize(new Dimension(820, 820));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);

    //==================================================================================================================

        addBankButton.addActionListener(e -> {  // Button to add the bank.
            {
                DataBase dataBase;
                try {
                    dataBase = new DataBase();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                String code = tfBankCode.getText();
                String name = tfBankName.getText();
                String street = tfStreet.getText();
                String city = tfCity.getText();
                String country = tfCountry.getText();

                if (LoginForm.checkWords(name) &&
                        LoginForm.checkWords(street) &&
                        LoginForm.checkWords(city) &&
                        LoginForm.checkNumber(code) &&
                        LoginForm.checkWords(country)) {
                    try {



                        if (dataBase.addBank(Integer.parseInt(code), name, street, city, country)) {
                            JOptionPane.showMessageDialog(AddBankForm.this,
                                    "Bank has been added successfully",
                                    "Successful Operation",
                                    JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new AdminForm(null);
                        } else {
                            JOptionPane.showMessageDialog(AddBankForm.this,
                                    "This bank is already exist in the system",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);

                        }

                    } catch (SQLException ee) {
                        throw new RuntimeException(ee);
                    }
                }

            }

        });


//==================================================================================================================

        // Button to return to previous window.
        cancelButton.addActionListener(e -> {
            new AdminForm(null);
            dispose();
        });
    //==================================================================================================================
    }
    //==================================================================================================================
}
