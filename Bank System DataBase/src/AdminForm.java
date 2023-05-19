import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminForm extends  JDialog{
    private JPanel AdminPanel;
    private JButton addBankButton;
    private JButton addBranchButton;
    private JButton LogOutButton;

    public AdminForm(JFrame parent)  // Constructor.
    {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Admin");
        setContentPane(AdminPanel);
        setMinimumSize(new Dimension(820, 420));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);

        addBankButton.addActionListener(e -> {

            new AddBankForm(null);
            dispose();

        });

        addBranchButton.addActionListener(e -> {

            new AddBranch(null);
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
