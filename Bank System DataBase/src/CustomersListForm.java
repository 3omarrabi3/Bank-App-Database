import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomersListForm extends JDialog {
    private JPanel CustomersListPanel;
    private JTable customersTable;
    private JButton cancelButton;
    private final int SSN;

    //==================================================================================================================

    public CustomersListForm(JFrame parent , int ssn) {
        super(parent);
        setTitle("Customers List");
        setContentPane(CustomersListPanel);
        setMinimumSize(new Dimension(1000, 700));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        SSN = ssn;

        try {
            this.showList();
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getErrorCode());
        }
        cancelButton.addActionListener(e -> {
            dispose();
            new EmployeeForm(null, SSN);
        });

        pack();
        setVisible(true);
    }

    //==================================================================================================================

    private void showList() throws SQLException {
        DataBase dataBase = new DataBase();
        List<List<String>> list = dataBase.showList(SSN);
        createCustomerTable(list);
    }

    //==================================================================================================================

    private void createCustomerTable(List<List<String>> list) {
        String[] columns = {"SSN", "FName", "LName", "Phone", "Country", "City", "Street",
                "BuildingNumber", "Email", "Password", "BranchName", "BankName"};

        String[][] data = new String[list.size()][] ;

        for (int i = 0; i < list.size(); i++) {
            data[i] = new String[list.get(i).size()] ;

            for (int j = 0; j < list.get(i).size(); j++) {
                data[i][j] = list.get(i).get(j) ;
            }
        }

        customersTable.setModel(new DefaultTableModel(
                data,
                columns
        ));

        JTableHeader tableHeader = customersTable.getTableHeader();
        tableHeader.setBackground(new Color(0, 145, 201));

        Font font = new Font("Segoe Print", Font.BOLD, 18);
        Font tablefont = new Font("Segoe Print",Font.PLAIN, 12);
        customersTable.setFont(tablefont);
        tableHeader.setFont(font);
        cancelButton.setFont(font);

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tableHeader.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
    }
    //==================================================================================================================
}

