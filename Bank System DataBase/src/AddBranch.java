import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBranch extends JDialog
{
    private JPanel AddBranchPanel;
    private JTextField tfBankName;
    private JTextField tfStreet;
    private JTextField tfBuildingNumber;
    private JTextField tfCountry;
    private JButton cancelButton;
    private JButton addBranchButton;
    private JTextField tfCity;
    private JTextField tfBranchNumber;
    public AddBranch(JFrame parent)  // Constructor.
    {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Admin");
        setContentPane(AddBranchPanel);
        setMinimumSize(new Dimension(820, 820));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);
        addBranchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bankName = tfBankName.getText();
                String branchNumber = tfBranchNumber.getText();
                String Country = tfCountry.getText();
                String Street = tfStreet.getText();
                String City = tfCity.getText();
                String BuildingNumber = tfBuildingNumber.getText();

                if(LoginForm.checkWords(bankName) &&
                 LoginForm.checkWords(Country) &&
                LoginForm.checkWords(City) &&
                LoginForm.checkNumber(branchNumber) &&
                LoginForm.checkNumber(BuildingNumber) )
                {
                    JOptionPane.showMessageDialog(AddBranch.this,
                            "Branch has been Added successfully",
                            "Successful Operation",
                            JOptionPane.INFORMATION_MESSAGE);
                    // function to check if the Branch is already in the table to be added.
                    dispose();
                    new AdminForm(null);
                }
            }
        });
        cancelButton.addActionListener(e -> {
            new AdminForm(null);
            dispose();
        });
    }
}

