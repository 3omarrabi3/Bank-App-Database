import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomersForm extends JDialog{
    private JPanel CustomerPanel;
    private JButton requestButton;
    private JButton startButton;
    private JButton LogOutButton;

    private int SSN;

    public CustomersForm(JFrame parent,int ssn)  // Constructor.
    {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Admin");
        setContentPane(CustomerPanel);
        setMinimumSize(new Dimension(820, 420));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);
        SSN =ssn;

        requestButton.addActionListener(e -> {

            new RequestForm(null,SSN);
            dispose();

        });

        startButton.addActionListener(e -> {

            new StartForm(null,SSN);
            dispose();

        });

        LogOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Home(null);
                dispose();
            }
        });
    }
}
