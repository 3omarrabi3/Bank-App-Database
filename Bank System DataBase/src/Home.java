import javax.swing.*;
import java.awt.*;


public class Home extends JDialog{
    private JButton adminButton;    // Button that takes the user to the admin login.
    private JButton employeeButton; // Button that takes the user to the employee login.
    private JPanel HomePanel;   // The current panel.
    private JButton customerButton;
    private JButton exitButton;
    private JButton showDataButton;

    //__________________________________________________________________________________________________________________

    public Home(JFrame parent)  // Constructor.
    {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("BankSystem");
        setContentPane(HomePanel);
        setMinimumSize(new Dimension(820,420));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);
    //__________________________________________________________________________________________________________________

        adminButton.addActionListener(e -> {    // Gets the user to the admin login and passes true to the admin attribute.
            new LoginForm(null,"Admin");
            dispose();
        });

    //__________________________________________________________________________________________________________________

        employeeButton.addActionListener(e -> {
             new LoginForm(null,"Employee"); // Gets the user to the employee login and passes false to the admin attribute.
            dispose();
        });

    //__________________________________________________________________________________________________________________

        customerButton.addActionListener(e -> {
            new LoginForm(null,"Customer"); // Gets the user to the employee login and passes false to the admin attribute.
            dispose();
        });

    //__________________________________________________________________________________________________________________

        exitButton.addActionListener(e -> {     // Button to exit the application.
            dispose() ;
        });

    //__________________________________________________________________________________________________________________

        showDataButton.addActionListener(e ->{  // Button to show the data.
            new ShowDataForm(null);
            dispose(); });
    }

    //__________________________________________________________________________________________________________________
}
