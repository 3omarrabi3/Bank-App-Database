import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ShowDataForm extends JDialog{
    private JPanel thisPanel;
    private JTextPane textPane;
    private JButton backButton;

    //__________________________________________________________________________________________________________________
    public ShowDataForm(JFrame parent)  // Constructor.
    {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Show Data");
        setContentPane(thisPanel);
        setMinimumSize(new Dimension(1200, 800));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);
        //______________________________________________________________________________________________________________

        try {
            DataBase dataBase = new DataBase();
            textPane.setText(dataBase.getData().toString());
            textPane.setEditable(false);
        }
        catch (SQLException s){
            System.out.println(s.getMessage());
        }

        //______________________________________________________________________________________________________________
        backButton.addActionListener(e -> {
            dispose();
            new Home (null);
        });
    }
}
