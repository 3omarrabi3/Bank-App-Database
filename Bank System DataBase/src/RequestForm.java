import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RequestForm extends JDialog{
    private JTable LoansTable;
    private JButton cancelButton;
    private JPanel RequestPanel;
    private JButton requestButton;

    private int SSN;

    public RequestForm(JFrame parent,int ssn)  // Constructor.
    {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Request Loan");
        setContentPane(RequestPanel);
        setMinimumSize(new Dimension(820, 820));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);
        SSN = ssn;

        cancelButton.addActionListener(e -> {
            new CustomersForm(null,SSN);
            dispose();
        });
        requestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomersForm(null,SSN);
                dispose();
            }
        });
    }
}
