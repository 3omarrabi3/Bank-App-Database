import javax.swing.*;
import java.awt.*;

public class LoginForm extends JDialog {
    private JTextField tfUserName;
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
            String userName = tfUserName.getText();
            char[] passwordArr = pfPassword.getPassword();
            String password = new String(passwordArr);
            if (type.equals("Admin")) {
                checkLogin_Admin(userName, password);
            } else if (type.equals("Employee")) {
                checkLogin_Employee(userName, password);
            } else {
                checkLogin_Customer(userName, password);
            }
        });

        // __________________________________________________________________________________________________________________

        cancelButton.addActionListener(e -> {
            new Home(null);
            dispose();
        });
    }

    //__________________________________________________________________________________________________________________

    void checkLogin_Admin(String UserName, String Password) // method to check the login when the admin is logged in.
    {
        if (UserName.equals("Admin") && Password.equals("Admin")) {
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

    void checkLogin_Employee(String UserName, String Password)  // method to check the login when the employee is logged in.
    {
        // to be implemented
        // gets the username and password and search for them in the database.
        JOptionPane.showMessageDialog(LoginForm.this,
                "Didn't implement yet",
                "Please,choose cancel",
                JOptionPane.ERROR_MESSAGE);
        dispose();
        new EmployeeForm(null);

    }

    //__________________________________________________________________________________________________________________

    void checkLogin_Customer(String UserName, String Password)  // method to check the login when the customer is logged in.
    {
        // to be implemented
        // gets the username and password and search for them in the database.
        JOptionPane.showMessageDialog(LoginForm.this,
                "Didn't implement yet",
                "Please,choose cancel",
                JOptionPane.ERROR_MESSAGE);
        dispose();
        new CustomersForm(null);

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
        if (!email.matches(email)) {
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