import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartForm extends JDialog{
    private JButton cancelButton;
    private JButton startButton;
    private JList list1;
    private JPanel StartPanel;

    private int SSN;

    public StartForm(JFrame parent,int ssn)  // Constructor.
    {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Start Payment");
        setContentPane(StartPanel);
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
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomersForm(null,SSN);
                dispose();
            }
        });
    }
}
