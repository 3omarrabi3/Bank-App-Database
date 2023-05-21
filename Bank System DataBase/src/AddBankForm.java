import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBankForm extends JDialog{
    private JPanel AddBankPanel;
    private JTextField tfBankName;
    private JTextField tfStreet;
    private JTextField tfBuildingNumber;
    private JTextField tfCountry;
    private JButton cancelButton;
    private JButton addBankButton;
    private JTextField tfCity;

    public AddBankForm(JFrame parent)  // Constructor.
    {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Admin");
        setContentPane(AddBankPanel);
        setMinimumSize(new Dimension(820, 820));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);
        addBankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(AddBankForm.this,
                        "Bank has been Added successfully",
                        "Successful Operation",
                        JOptionPane.INFORMATION_MESSAGE);
                // function to check if the bank is already in the table to be added.
                dispose();
                new AdminForm(null);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminForm(null);
                dispose();
            }
        });
    }
}
