import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class PayForm extends JDialog{
    private JPanel PayPanel;
    private JLabel Heading;
    private JTable loansTable;
    private JButton cancelButton;
    private JButton payButton;
    private JLabel Amount;
    private JTextField textFieldSSN;
    private JTextField textFieldAmount;
    private JTextField textFieldLoanNumber;
    int SSN;
    public PayForm(JFrame parent , int ssn)  // Constructor.
    {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Pay for loan");
        setContentPane(PayPanel);
        setMinimumSize(new Dimension(820, 820));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);
        SSN = ssn;

        List<List<String>> list = new LinkedList<>() ;
        createTable(list) ;

        cancelButton.addActionListener(e -> {
            new EmployeeForm(null, SSN);
            dispose();
        });

        payButton.addActionListener(e -> {
            try{
                if (!LoginForm.checkDouble(textFieldAmount.getText()) ||
                    !LoginForm.checkNumber(textFieldSSN.getText()) ||
                    !LoginForm.checkNumber(textFieldLoanNumber.getText())){
                    return;
                }

                List<String> temp = null ;
                for (List<String> list1: list) {
                    if (list1.get(0).equals(textFieldSSN.getText()) && list1.get(1).equals(textFieldLoanNumber.getText())){
                        temp = list1 ;
                    }
                }

                if (temp == null){
                    JOptionPane.showMessageDialog(null,
                            "You entered invalid inputs",
                            "Invalid inputs",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                double amount = Double.parseDouble(textFieldAmount.getText()) ;
                double change = amount - Double.parseDouble(temp.get(2)) ;

                if (change >= 0) {
                    JOptionPane.showMessageDialog(null,
                            "You paid: " + amount + " and change : " + change,
                            "Successful Payment",
                            JOptionPane.INFORMATION_MESSAGE);

                    DataBase dataBase = new DataBase();
                    dataBase.updateStateToPayed(textFieldSSN.getText(), textFieldLoanNumber.getText());
                }
                else{
                    JOptionPane.showMessageDialog(null,
                            "You paid: " + amount + " and remaining : " + change * -1 +
                                    "\nYour loan still opened",
                            "Successful Payment",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                new EmployeeForm(null, SSN);
                dispose();
            }
            catch (SQLException s){
                System.out.println(s.getMessage()) ;
            }
        });
    }

    private void createTable(List<List<String>> list) {
        try {
            DataBase dataBase = new DataBase();
            dataBase.getStartedLoans(list, SSN);

            String[] columns = {"Customer SSN", "Loan Number", "Loan Amount", "Loan Type"};
            String[][] data = new String[list.size()][] ;

            for (int i = 0; i < list.size(); i++) {
                data[i] = new String[list.get(i).size()] ;

                for (int j = 0; j < list.get(i).size(); j++) {
                    data[i][j] = list.get(i).get(j) ;
                }
            }

            loansTable.setModel(new DefaultTableModel(
                    data,
                    columns
            ));

            JTableHeader tableHeader = loansTable.getTableHeader();
            tableHeader.setBackground(new Color(0, 145, 201));

            Font font = new Font("Segoe Print", Font.BOLD, 18);
            Font tablefont = new Font("Segoe Print",Font.PLAIN, 12);
            loansTable.setFont(tablefont);
            tableHeader.setFont(font);
            cancelButton.setFont(font);

            DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tableHeader.getDefaultRenderer();
            headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        }
        catch (SQLException s){
            System.out.println(s.getMessage());
        }
    }
}
