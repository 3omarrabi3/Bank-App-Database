import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class RequestForm extends JDialog {
    private JButton cancelButton;
    private JPanel RequestPanel;
    private JButton requestButton;
    private JTable table1;
    private JTextField textField1;

    private final int SSN;

    //__________________________________________________________________________________________________________________

    public RequestForm(JFrame parent, int ssn)  // Constructor.
    {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Request Loan");
        setContentPane(RequestPanel);
        setMinimumSize(new Dimension(820, 440));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);
        SSN = ssn;

        // Create the table
        table1 = new JTable();
        JScrollPane scrollPane = new JScrollPane(table1);
        requestButton = new JButton("Request");
        requestButton.setBackground(new Color(0, 145, 201));
        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.red);

        textField1 = new JTextField(10);

        // Create a panel to hold the text field and the buttons
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JLabel loanNumberLabel = new JLabel("please enter the loan number you want to request:");
        buttonPanel.add(loanNumberLabel, BorderLayout.WEST);
        buttonPanel.add(textField1, BorderLayout.CENTER);

        // Create a panel to hold the buttons
        JPanel buttonSubPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        buttonSubPanel.add(requestButton);
        buttonSubPanel.add(cancelButton);

        buttonPanel.add(buttonSubPanel, BorderLayout.SOUTH);

        // Create a panel to hold the table and the button panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPanel);

        try {
            this.showLoanList();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getErrorCode());
        }

        //__________________________________________________________________________________________________________________

        cancelButton.addActionListener(e -> {
            dispose();
            new CustomersForm(null, SSN);
        });
        pack();
        setVisible(true);

        //__________________________________________________________________________________________________________________

        requestButton.addActionListener(e -> {
            String loanNum = textField1.getText();
            DataBase dataBase;

            if (loanNum == null) {
                JOptionPane.showMessageDialog(null,
                        "You entered invalid inputs",
                        "Invalid inputs",
                        JOptionPane.INFORMATION_MESSAGE);

            } else if (LoginForm.checkNumber(textField1.getText())) {
                try {
                    dataBase = new DataBase();
                    if (dataBase.checkExistanceLoanNumber(Integer.parseInt(loanNum))) {
                        dataBase.sentrequestLoan(SSN, loanNum);
                        JOptionPane.showMessageDialog(null,
                                "the loan has been requested!",
                                "successful request",
                                JOptionPane.INFORMATION_MESSAGE);
                        new CustomersForm(null, SSN);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "the loan number is not found",
                                "Invalid inputs",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }

        });
    }

    //__________________________________________________________________________________________________________________

    private void showLoanList() throws SQLException {
        // Get the customers from the database
        // Then make the table

        DataBase dataBase = new DataBase();
        List<List<String>> list = dataBase.showLoanList(SSN);
        createLoanTable(list);
    }

    //__________________________________________________________________________________________________________________

    private void createLoanTable(List<List<String>> list) {
        // Create a custom table model
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells uneditable
            }
        };
        // Declare the columns of header
        String[] columns = {"LoanNumber", "BranchNumber", "BankCode", "LoanAmount", "LoanType"};

        // Add the columns to the model
        model.setColumnIdentifiers(columns);

        // Add the data to the model
        for (List<String> row : list) {
            model.addRow(row.toArray());
        }

        // Create the table
        table1.setModel(model);

        JTableHeader tableHeader = table1.getTableHeader();
        tableHeader.setBackground(new Color(0, 145, 201));

        Font font = new Font("Segoe Print", Font.BOLD, 18);
        Font tablefont = new Font("Segoe Print", Font.PLAIN, 12);
        table1.setFont(tablefont);
        tableHeader.setFont(font);
        cancelButton.setFont(font);

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tableHeader.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
    }
}
