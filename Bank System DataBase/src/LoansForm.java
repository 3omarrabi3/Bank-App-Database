import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class LoansForm extends JDialog{
    private JPanel LoansPannel;
    private JTable LoansTable;
    private JButton cancelButton;

    private final int SSN;

    //__________________________________________________________________________________________________________________
    public LoansForm(JFrame parent,int ssn)  // Constructor.
    {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Loans List");
        setContentPane(LoansPannel);
        setMinimumSize(new Dimension(1300, 700));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);

        SSN = ssn;

        // Create the table
        LoansTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(LoansTable);

        // Create a panel to hold the table and the button
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(cancelButton, BorderLayout.SOUTH);

        setContentPane(contentPanel);

        this.getLoans();

    //__________________________________________________________________________________________________________________

        cancelButton.addActionListener(e -> {   // Button to return to previous panel.
            new EmployeeForm(null,SSN);
            dispose();
        });

    //__________________________________________________________________________________________________________________

    }

    //__________________________________________________________________________________________________________________

    private void getLoans() {
        DataBase dataBase;
        try {
            dataBase = new DataBase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<List<String>> list;
        try {
            list = dataBase.getLoans(SSN);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        createCustomerTable(list);
    }

    //__________________________________________________________________________________________________________________

    private void createCustomerTable(List<List<String>> list) {
        // Create a custom table model
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells uneditable
            }
        };

        // Declare the columns of header
        String[] columns = {"Loan Number", "Branch Number", "Bank Code", "Loan Amount", "Loan Type", "SSN Of Emp", "FName Of Emp", "LName Of Emp"};

        // Add the columns to the model
        model.setColumnIdentifiers(columns);

        // Add the data to the model
        for (List<String> row : list) {
            model.addRow(row.toArray());
        }

        // Create the table
        LoansTable.setModel(model);

        JTableHeader tableHeader = LoansTable.getTableHeader();
        tableHeader.setBackground(new Color(0, 145, 201));

        Font font = new Font("Segoe Print", Font.BOLD, 18);
        Font tablefont = new Font("Segoe Print",Font.PLAIN, 12);
        LoansTable.setFont(tablefont);
        tableHeader.setFont(font);
        cancelButton.setFont(font);

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tableHeader.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
    }
    //__________________________________________________________________________________________________________________
}
