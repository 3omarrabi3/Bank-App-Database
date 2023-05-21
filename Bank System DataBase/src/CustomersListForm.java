import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class CustomersListForm extends JDialog {
    private JPanel CustomersListPanel;
    private JTable CustomersTable;
    private JTable table1;
    private JButton cancelButton;

    int SNN;

    public CustomersListForm(JFrame parent , int snn) // Constructor.
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

        SNN = snn;
        try {
            this.showList();
        }
        catch (SQLException sqlException){
            System.out.println(sqlException.getErrorCode()) ;
        }

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EmployeeForm(null,SNN);
                dispose();
            }
        });
    }
    private void showList() throws SQLException {
        // Get the customers from database
        // Then make the table

        DataBase dataBase = new DataBase() ;
        List<List<String>> list = dataBase.showList() ;
        createCustomerTable(list) ;
    }

    private void createCustomerTable(List<List<String>> list) {
        // Declare the Columns of header
        String[] columns = {"SSN", "FName", "LName", "Phone", "Country", "City", "Street",
                "BuildingNumber", "Email", "Password", "BranchName", "BankName"} ;

        // Declare the data
        String[][] rows = new String[list.size()][columns.length] ;

        for (int j = 0; j < list.size(); ++j) {
            for (int i = 0; i < list.get(j).size(); ++i) {
                rows[j][i] = (list.get(j)).get(i) ;
            }
        }

        /*
            Create Table and Show it
         */
    }
}
