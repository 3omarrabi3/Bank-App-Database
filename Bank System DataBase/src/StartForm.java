import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class StartForm extends JDialog {
    private JButton cancelButton;
    private JButton startButton;
    private JPanel StartPanel;
    private JTextField textField1;
    private JTable table1;

    private int SSN;

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

//        table1 = new JTable();
//        JScrollPane scrollPane = new JScrollPane(table1);
//        startButton = new JButton("Start");
//        startButton.setBackground(new Color(0, 145, 201));
//        cancelButton = new JButton("Cancel");
//        cancelButton.setBackground(Color.red);
//
//        textField1 = new JTextField(10);

        // Create a panel to hold the text field and the buttons
//        JPanel buttonPanel = new JPanel(new BorderLayout());
//        JLabel loanNumberLabel = new JLabel("please enter the loan number you want to start paying:");
//        buttonPanel.add(loanNumberLabel, BorderLayout.WEST);
//        buttonPanel.add(textField1, BorderLayout.CENTER);
//
//        // Create a panel to hold the buttons
//        JPanel buttonSubPanel = new JPanel(new GridLayout(1, 2, 5, 0));
//        buttonSubPanel.add(startButton);
//        buttonSubPanel.add(cancelButton);
//
//        buttonPanel.add(buttonSubPanel, BorderLayout.SOUTH);
//
//        // Create a panel to hold the table and the button panel
//        JPanel contentPanel = new JPanel(new BorderLayout());
//        contentPanel.add(scrollPane, BorderLayout.CENTER);
//        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
//
//        setContentPane(contentPanel);
        try {
            this.showAcceptedLoanList();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String loanNum = textField1.getText();
                if (LoginForm.checkNumber(textField1.getText())){
                    DataBase dataBase = null;
                    try {
                        dataBase =new DataBase();
                        dataBase.updateLoanstatu(ssn,loanNum);

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                new CustomersForm(null, SSN);
                dispose();
            }
        });

        cancelButton.addActionListener(e -> {
            new CustomersForm(null, SSN);
            dispose();
        });
    }

    public void showAcceptedLoanList() throws SQLException {
        // Get the customers from the database
        // Then make the table

        DataBase dataBase = new DataBase();
        List<List<String>> list = dataBase.getAcceptedList(SSN);
        createLoanTable(list);
    }

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
