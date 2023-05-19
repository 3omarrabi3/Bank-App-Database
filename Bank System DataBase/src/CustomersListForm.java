import javax.swing.*;
import java.awt.*;

public class CustomersListForm extends JDialog {
    private JPanel CustomersListPanel;
    private JTable CustomersTable;

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
    }
}
