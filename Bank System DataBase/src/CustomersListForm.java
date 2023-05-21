import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomersListForm extends JDialog {
    private JPanel CustomersListPanel;
    private JTable CustomersTable;
    private JTable table1;
    private JButton cancelButton;

    public CustomersListForm(JFrame parent)  // Constructor.
    {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Customers List");
        setContentPane(CustomersListPanel);
        setMinimumSize(new Dimension(820, 420));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new EmployeeForm(null);
               dispose();
            }
        });
    }
}
