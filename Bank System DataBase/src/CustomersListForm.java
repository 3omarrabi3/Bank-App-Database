import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class CustomersListForm extends JDialog {
    private JPanel CustomersListPanel;
    private JTable CustomersTable;
    private JButton cancelButton;

    private int SSN;

    public CustomersListForm(JFrame parent , int ssn) {
        super(parent);
        setTitle("Customers List");
        setMinimumSize(new Dimension(1300, 700));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);

        SSN = ssn;

        // Create the table
        CustomersTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(CustomersTable);


        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.red);

        // Create a panel to hold the table and the button
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(cancelButton, BorderLayout.SOUTH);

        setContentPane(contentPanel);

        try {
            this.showList();
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getErrorCode());
        }

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new EmployeeForm(null, SSN);
            }
        });

        pack();
        setVisible(true);
    }

    private void showList() throws SQLException {
        // Get the customers from the database
        // Then make the table

        DataBase dataBase = new DataBase();
        List<List<String>> list = dataBase.showList();
        createCustomerTable(list);
    }

    private void createCustomerTable(List<List<String>> list) {
        // Create a custom table model
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells uneditable
            }
        };

        // Declare the columns of header
        String[] columns = {"SSN", "FName", "LName", "Phone", "Country", "City", "Street",
                "BuildingNumber", "Email", "Password", "BranchName", "BankName"};

        // Add the columns to the model
        model.setColumnIdentifiers(columns);

        // Add the data to the model
        for (List<String> row : list) {
            model.addRow(row.toArray());
        }

        // Create the table
        CustomersTable.setModel(model);

        JTableHeader tableHeader = CustomersTable.getTableHeader();
        tableHeader.setBackground(new Color(0, 145, 201));

        Font font = new Font("Segoe Print", Font.BOLD, 18);
        Font tablefont = new Font("Segoe Print",Font.PLAIN, 12);
        CustomersTable.setFont(tablefont);
        tableHeader.setFont(font);
        cancelButton.setFont(font);

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tableHeader.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
    }
}

