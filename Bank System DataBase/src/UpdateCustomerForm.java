import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class UpdateCustomerForm extends JDialog{
    private JPanel UpdateCustomerPanel;
    private JTextField tfSSN;
    private JButton getCustomerButton;
    private JButton cancelButton;
    private JButton deleteCustomerButton;

    private int SSN;
    public UpdateCustomerForm(JFrame parent, int ssn)     // constructor that sets the suitable attributes for the form.
    {
        super(parent);
        setTitle("Login");
        setContentPane(UpdateCustomerPanel);
        setMinimumSize(new Dimension(820,440));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);

        SSN = ssn;
        //__________________________________________________________________________________________________________________

        getCustomerButton.addActionListener(e -> {
            int customerSSN =Integer.parseInt( tfSSN.getText());
            DataBase dataBase = null;
            try {
                dataBase = new DataBase ();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            try {
                if(dataBase.UpdateCustomer(SSN,customerSSN))
                    dispose();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        // _____________________________________________________________________________________________________________

        deleteCustomerButton.addActionListener(e->{
            int customerSSN =Integer.parseInt( tfSSN.getText());
            DataBase dataBase = null;
            try {
                dataBase = new DataBase ();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            try {
                if(dataBase.DeleteCustomer(SSN,customerSSN)) {

                    dispose();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        cancelButton.addActionListener(e -> {
            new EmployeeForm(null,SSN);
            dispose();
        });
    }
}
