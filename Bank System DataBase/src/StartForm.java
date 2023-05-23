import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

import java.sql.SQLException;
import java.util.List;

public class StartForm extends JDialog {
    private JButton cancelButton;
    private JButton startButton;
    private JPanel StartPanel;
    private JTextField textField1;
    private JTable table1;

    private final int SSN;

    //__________________________________________________________________________________________________________________
    public StartForm(JFrame parent, int ssn)  // Constructor.
    {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Start Payment");
        setContentPane(StartPanel);
        setMinimumSize(new Dimension(820, 440));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);

        SSN = ssn;

        //__________________________________________________________________________________________________________________

        try {
            this.showAcceptedLoanList();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        startButton.addActionListener(e -> {
            String loanNum = textField1.getText();
            DataBase dataBase;
            if (loanNum == null) {
                JOptionPane.showMessageDialog(null,
                        "You entered invalid inputs",
                        "Invalid inputs",
                        JOptionPane.INFORMATION_MESSAGE);

            }
            else if (LoginForm.checkNumber(textField1.getText())){
                try {
                    dataBase = new DataBase();
                    if (dataBase.checkExistanceLoanNumber(Integer.parseInt(loanNum))) {
                        dataBase.updateLoanstatu(ssn,loanNum);
                        JOptionPane.showMessageDialog(null,
                                "the loan has been started!",
                                "successful start",
                                JOptionPane.INFORMATION_MESSAGE);
                        new CustomersForm(null, SSN);
                        dispose();
                    }
                    else {
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
        //__________________________________________________________________________________________________________________

        cancelButton.addActionListener(e -> {
            new CustomersForm(null, SSN);
            dispose();
        });
    }
    //__________________________________________________________________________________________________________________

    public void showAcceptedLoanList() throws SQLException {
        // Get the customers from the database
        // Then make the table

        DataBase dataBase = new DataBase();
        List<List<String>> list = dataBase.getAcceptedList(SSN);
        createLoanTable(list);
    }

    //__________________________________________________________________________________________________________________

    private void createLoanTable(List<List<String>> list) {

        String[] columns = {"Customer SSN", "Loan Number", "Status"};
        String[][] data = new String[list.size()][];

        for (int i = 0; i < list.size(); i++) {
            data[i] = new String[list.get(i).size()];

            for (int j = 0; j < list.get(i).size(); j++) {
                data[i][j] = list.get(i).get(j);
            }
        }

        table1.setModel(new DefaultTableModel(
                data,
                columns
        ));

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