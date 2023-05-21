import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartForm extends JDialog{
    private JButton cancelButton;
    private JButton startButton;
    private JList list1;
    private JPanel StartPanel;

    public StartForm(JFrame parent)  // Constructor.
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

        cancelButton.addActionListener(e -> {
            new CustomersForm(null);
            dispose();
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomersForm(null);
                dispose();
            }
        });
    }
}
