import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;
import java.util.List;
public class PendingLoansForm extends JDialog{
    private JPanel PendingLoansPannel;
    private JTable PendingLoansTable;
    private JButton cancelButton;
    private JButton rejectButton;
    private JButton acceptButton;
    private JTextField textField1;
    private JTextField textField2;

    private final int SSN;

    //__________________________________________________________________________________________________________________

    private void Update(String Str) throws SQLException {

        if(LoginForm.checkNumber(textField1.getText())&& LoginForm.checkNumber(textField2.getText())) {

            int CustomerSSN = Integer.parseInt(textField1.getText());
            int  LoanNumber= Integer.parseInt(textField2.getText());
            DataBase dataBase = new DataBase();
            if(!(dataBase.checkExistance(CustomerSSN, LoanNumber)))
            {
                JOptionPane.showMessageDialog(PendingLoansForm.this,
                        "This data doesn't exist",
                        "Login Failed!",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                String currentDir = java.lang.System.getProperty("user.dir");
                String url = "jdbc:sqlite:" + currentDir + "\\identifier.sqlite";
                Connection connection = DriverManager.getConnection(url);
                String selectQuery = "UPDATE LoanRequests SET Status = " +
                        "'" + Str + "' WHERE CustomerSSN = " + CustomerSSN + " AND LoanNumber = " + LoanNumber + ";";
                Statement statement = connection.createStatement();
                statement.executeUpdate(selectQuery);
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            JOptionPane.showMessageDialog(PendingLoansForm.this,
                    "Status Updated successfully",
                    "Successful Operation",
                    JOptionPane.INFORMATION_MESSAGE);
            new EmployeeForm(null, SSN);
            dispose();

        }
    }

    //__________________________________________________________________________________________________________________

    public PendingLoansForm(JFrame parent,int ssn)  // Constructor.
    {
        super(parent);
        setTitle("Pending Loans List");
        setContentPane(PendingLoansPannel);
        setMinimumSize(new Dimension(820, 820));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);

        SSN = ssn;

        try{
            this.showListPendingLoansTable();
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getErrorCode());
        }

        cancelButton.addActionListener(e -> {
            new EmployeeForm(null,SSN);
            dispose();
        });


        acceptButton.addActionListener(e -> {
            try {
                Update("Accepted");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        rejectButton.addActionListener(e -> {
            try {
                Update("Rejected");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    //__________________________________________________________________________________________________________________

    private void showListPendingLoansTable() throws SQLException {
        // Get the customers from the database
        // Then make the table
        DataBase dataBase = new DataBase();
        List<List<String>> list = dataBase.showListPendingLoansTable(SSN);
        createPendingLoansTable(list);

    }

    //__________________________________________________________________________________________________________________

    private void createPendingLoansTable(List<List<String>> list) {
        // Create a custom table model
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells uneditable
            }
        };
        String[] columns = {"CustomerSSN", "LoanNumber", "Status"};
        model.setColumnIdentifiers(columns);
        for (List<String> row : list) {
            model.addRow(row.toArray());
        }
        PendingLoansTable.setModel(model);

        JTableHeader tableHeader = PendingLoansTable.getTableHeader();
        tableHeader.setBackground(new Color(0, 145, 201));

        Font font = new Font("Segoe Print", Font.BOLD, 18);
        Font tablefont = new Font("Segoe Print", Font.PLAIN, 12);
        PendingLoansTable.setFont(tablefont);
        tableHeader.setFont(font);
        cancelButton.setFont(font);
        acceptButton.setFont(font);
        rejectButton.setFont(font);

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tableHeader.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
    }
}
