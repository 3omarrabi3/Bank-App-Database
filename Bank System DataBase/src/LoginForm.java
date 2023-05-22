import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginForm extends JDialog {
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JButton loginButton;
    private JButton cancelButton;
    private JPanel LoginPanel;
    private final String type;
    private static String phoneNumberRegex = "^01\\d{8}$";
    private static String numbersRegex = "^[0-9]+$";
    private static String nameRegex = "^[A-Za-z\\s]+$";
    private static String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    //__________________________________________________________________________________________________________________

    public LoginForm() {
        type = null;
    }

    public LoginForm(JFrame parent, String Type)     // constructor that sets the suitable attributes for the form.
    {
        super(parent);
        setTitle("Login");
        setContentPane(LoginPanel);
        setMinimumSize(new Dimension(820, 440));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);
        type = Type;

        //__________________________________________________________________________________________________________________

        loginButton.addActionListener(e -> {
            String email = tfEmail.getText();
            char[] passwordArr = pfPassword.getPassword();
            String password = new String(passwordArr);
            if (type.equals("Admin")) {
                checkLogin_Admin(email, password);
            } else if (type.equals("Employee")) {
                try {
                    checkLogin_Employee(email, password);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    checkLogin_Customer(email, password);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // __________________________________________________________________________________________________________________

        cancelButton.addActionListener(e -> {
            new Home(null);
            dispose();
        });
    }

    //__________________________________________________________________________________________________________________

    void checkLogin_Admin(String email, String Password) // method to check the login when the admin is logged in.
    {
        if (email.equals("Admin") && Password.equals("Admin")) {
            new AdminForm(null);
            dispose();
        } else {
            JOptionPane.showMessageDialog(LoginForm.this,
                    "Invalid Email or Password!!!",
                    "Please,Try Again",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    //__________________________________________________________________________________________________________________

    void checkLogin_Employee(String email, String Password)  // method to check the login when the employee is logged in.
     throws SQLException {
         DataBase database = new DataBase();
         Connection connection = database.getConnection();
         Statement statement = connection.createStatement();
         String query = "SELECT * FROM Employee WHERE Email = '" + email + "' AND password = '" + Password + "'";

         ResultSet resultSet = statement.executeQuery(query);


         if (resultSet.next()) {
             int SSN =resultSet.getInt("SSN");
             String Name = resultSet.getString("FName") + " " + resultSet.getString("LName");
             JOptionPane.showMessageDialog(LoginForm.this,
                     "Welcome, "+Name,
                     "Successful Login!",
                     JOptionPane.INFORMATION_MESSAGE);
             dispose();
             new EmployeeForm(null,SSN);
         }
         else {
             JOptionPane.showMessageDialog(LoginForm.this,
                     "Invalid Credentials",
                     "Login Failed!",
                     JOptionPane.ERROR_MESSAGE);
         }

         resultSet.close();
         statement.close();
         connection.close();

    }

    //__________________________________________________________________________________________________________________

    void checkLogin_Customer(String email, String Password)  // method to check the login when the customer is logged in.
       throws SQLException {
        DataBase database = new DataBase();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Customer WHERE Email = '" + email + "' AND password = '" + Password + "'";

        ResultSet resultSet = statement.executeQuery(query);


        if (resultSet.next()) {
            int SSN =resultSet.getInt("SSN");
            String Name = resultSet.getString("FName") + " " + resultSet.getString("LName");
            JOptionPane.showMessageDialog(LoginForm.this,
                    "Welcome, "+Name,
                    "Successful Login!",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new CustomersForm(null,SSN);
        }
        else {
            JOptionPane.showMessageDialog(LoginForm.this,
                    "Invalid Credentials",
                    "Login Failed!",
                    JOptionPane.ERROR_MESSAGE);
        }

        resultSet.close();
        statement.close();
        connection.close();

    }

    //__________________________________________________________________________________________________________________

    public static boolean strongPassword(String password) {
        if (!password.matches(passwordRegex)) {
            JOptionPane.showMessageDialog(null,
                    "Please,Choose a Strong Password",
                    "Weak password",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean checkNumber(String number) {
        if (!number.matches(numbersRegex)) {
            JOptionPane.showMessageDialog(null,
                    "Please,Enter valid Data",
                    "Invalid Data",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean checkWords(String word) {
        if (!word.matches(nameRegex)) {
            JOptionPane.showMessageDialog(null,
                    "Please,Enter valid Data",
                    "Invalid Data",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean checkEmail(String email) {
        if (!email.matches(emailRegex)) {
            JOptionPane.showMessageDialog(null,
                    "Please,Enter valid e-mail address",
                    "Invalid Email",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean checkPhoneNumber(String number) {
        if (!number.matches(phoneNumberRegex)) {
            JOptionPane.showMessageDialog(null,
                    "Please,Enter valid phone number",
                    "Invalid phone number",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}