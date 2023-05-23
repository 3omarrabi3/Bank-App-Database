import javax.swing.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DataBase {
    Connection connection;

    //==================================================================================================================

    public DataBase() throws SQLException {
        String currentDir = java.lang.System.getProperty("user.dir");
        String url = "jdbc:sqlite:" + currentDir + "\\identifier.sqlite";
        connection = DriverManager.getConnection(url);
    }

    //==================================================================================================================

    public List<List<String>> showList(int employeeSSN) throws SQLException {
        String sql = "SELECT DISTINCT\n" +
                "    C.SSN, C.FName, C.LName, C.Phone, C.Email, C.Password, C.Country,\n" +
                "    C.City, C.Street, C.BuildingNumber, B.Name AS BranchName, BN.Name AS BankName\n" +
                "FROM\n" +
                "    CUSTOMER AS C\n" +
                "        JOIN Employee AS E ON C.BranchNumber = E.BranchNumber AND C.BankCode = E.BankCode\n" +
                "        JOIN Branch AS B ON C.BranchNumber = B.BranchNumber\n" +
                "        JOIN Bank AS BN ON C.BankCode = BN.Code\n" +
                "WHERE\n" +
                "        E.SSN = " + employeeSSN + ";" ;

        Statement statement = connection.createStatement() ;
        ResultSet resultSet = statement.executeQuery(sql) ;
        List<List<String>> list = new LinkedList<>() ;

        while (resultSet.next()){
            List<String> customer = new LinkedList<>();

            customer.add(Integer.toString(resultSet.getInt("SSN"))) ;
            customer.add(resultSet.getString("FName")) ;
            customer.add(resultSet.getString("LName")) ;
            customer.add(resultSet.getString("Phone")) ;
            customer.add(resultSet.getString("Email")) ;
            customer.add(resultSet.getString("Password")) ;
            customer.add(resultSet.getString("Country")) ;
            customer.add(resultSet.getString("City")) ;
            customer.add(resultSet.getString("Street")) ;
            customer.add(Integer.toString(resultSet.getInt("BuildingNumber"))) ;
            customer.add(resultSet.getString("BranchName")) ;
            customer.add(resultSet.getString("BankName")) ;

            list.add(customer) ;
        }

        connection.close();
        statement.close();
        return list ;
    }



    //==================================================================================================================

    public List<List<String>> showListPendingLoansTable(int ssn) throws SQLException {
        String sql = "Select LoanRequests.CustomerSSN, LoanRequests.LoanNumber, LoanRequests.Status\n" +
                "from LoanRequests ,Loan where LoanRequests.LoanNumber = Loan.LoanNumber\n" +
                "and Status = 'Pending' and BranchNumber = (select BranchNumber from Employee\n" +
                "                                                               where SSN = "+ssn+")\n" +
                "and BankCode = (select BankCode from Employee where "+ssn+");";

        Statement statement = connection.createStatement() ;
        ResultSet resultSet = statement.executeQuery(sql) ;
        List<List<String>> list = new LinkedList<>() ;

        while (resultSet.next()){
            List<String> LoanRequests = new LinkedList<>();
            LoanRequests.add(Integer.toString(resultSet.getInt("CustomerSSN")));
            LoanRequests.add(Integer.toString(resultSet.getInt("LoanNumber"))) ;
            LoanRequests.add(resultSet.getString("Status")) ;

            list.add(LoanRequests) ;
        }
        connection.close();
        statement.close();
        return list ;
    }

    //==================================================================================================================

    public void addAccount(int accountNumber, int ssn,
                           String accountType, double accountBalance,
                           int branchNumber, int bankCode) throws SQLException {

        String sql = "insert into Account values (" + accountNumber + "," + branchNumber + "," + bankCode + "," +
                " " + ssn + ", " + accountBalance + ", '" + accountType + "' );";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);

        connection.close();
        statement.close();
    }

    //==================================================================================================================

    public void addCustomer(int ssn, String firstName, String lastName, int phone,
                            String street, String city, String country, int buildingNumber,
                            String email, String password, int branchNumber,
                            int bankCode) throws SQLException {

        String sql = "insert into Customer values(" + ssn + ",'" + firstName + "','" + lastName + "',"
                + phone + ",'" + street + "','" + city + "','" + country + "'," + buildingNumber + ",'" + email + "','"
                + password + "'," + branchNumber + "," + bankCode + ");";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    //==================================================================================================================
    public boolean addBranch(int branchNumber, int bankCode, String name, String street, String city, String country) throws SQLException {
        String selectQuery = "SELECT * FROM Branch WHERE BranchNumber = " + branchNumber + " AND BankCode = " + bankCode;
        Statement selectStatement = connection.createStatement();
        ResultSet resultSet = selectStatement.executeQuery(selectQuery);

        if (resultSet.next()) {
            // Branch with the same branch number and bank code already exists
            resultSet.close();
            selectStatement.close();
            return false;
        } else {
            String insertQuery = "INSERT INTO Branch (BranchNumber, BankCode, Name, Street, City, Country) VALUES ("
                    + branchNumber + ", "
                    + bankCode + ", '"
                    + name + "', '"
                    + street + "', '"
                    + city + "', '"
                    + country + "')";
            Statement insertStatement = connection.createStatement();
            int rowsAffected = insertStatement.executeUpdate(insertQuery);
            insertStatement.close();

            resultSet.close();
            selectStatement.close();

            return rowsAffected > 0;
        }


    }

    //==================================================================================================================

    public boolean Login(String email, String password, String Table)
            throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM " + Table + " WHERE Email = '" + email + "' AND password = '" + password + "'";

        ResultSet resultSet = statement.executeQuery(query);


        if (resultSet.next()) {
            int SSN = resultSet.getInt("SSN");
            String Name = resultSet.getString("FName") + " " + resultSet.getString("LName");
            JOptionPane.showMessageDialog(null,
                    "Welcome, " + Name,
                    "Successful Login!",
                    JOptionPane.INFORMATION_MESSAGE);
            if(Table.equals("Employee"))
            {
                new EmployeeForm(null, SSN);
            }
            else {
                new CustomersForm(null,SSN);
            }

            connection.close();
            statement.close();
            return true;
        }
        return false;
    }

    //==================================================================================================================

    public boolean UpdateCustomer(int employeeSSN, int CustomerSSN)
            throws SQLException {

        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Customer , Account  WHERE Customer.SSN = Account.CustomerSSN and SSN = '" + CustomerSSN + "'";

        String FirstName, Email, Password, LastName, Country, City, Street, AccountType;
        int Phone, BuildingNumber, BranchNumber, BankCode;
        double AccountBalance;

        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            Phone = resultSet.getInt("Phone");
            BuildingNumber = resultSet.getInt("BuildingNumber");
            BranchNumber = resultSet.getInt("BranchNumber");
            FirstName = resultSet.getString("FName");
            LastName = resultSet.getString("LName");
            Street = resultSet.getString("Street");
            City = resultSet.getString("City");
            Country = resultSet.getString("Country");
            Email = resultSet.getString("Email");
            Password = resultSet.getString("Password");
            BankCode = resultSet.getInt("BankCode");
            AccountBalance = resultSet.getDouble("Balance");
            AccountType = resultSet.getString("Type");
            new CustomerInfoForm(null, employeeSSN, CustomerSSN,
                    FirstName, LastName, Email, Password, Country, City, Street
                    , AccountType, BankCode, BranchNumber, BuildingNumber, Phone, AccountBalance);

            connection.close();
            statement.close();
            return true;
        }

        JOptionPane.showMessageDialog(null,
                "Couldn't find Customer",
                "Invalid SSN!!!",
                JOptionPane.ERROR_MESSAGE);
        return false;

    }

    //==================================================================================================================

    public void setCustomer(
            int ssn, String firstName, String lastName, int phone,
            String street, String city, String country, int buildingNumber,
            String email, String password, int branchNumber,
            int bankCode) throws SQLException {

        String sql = "UPDATE Customer SET FName = '" + firstName + "', LName = '" + lastName + "', Phone = " + phone + "," +
                " Street = '" + street + "', City = '" + city + "', Country = '" + country + "', BuildingNumber = " + buildingNumber + ", " +
                "Email = '" + email + "', Password = '" + password + "', BranchNumber = " + branchNumber + ", BankCode = " + bankCode + " WHERE SSN = " + ssn;
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    //==================================================================================================================

    public void setAccount(int ssn,
                           String accountType, double accountBalance,
                           int branchNumber, int bankCode) throws SQLException {

        String sql = "UPDATE Account SET Type = '" + accountType + "', Balance = " + accountBalance + "" +
                ", BranchNumber = " + branchNumber + ", BankCode = " + bankCode + "" +
                " WHERE CustomerSSN = " + ssn;
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);

        connection.close();
        statement.close();
    }

    //==================================================================================================================

    public Boolean addBank(int code, String name, String Street, String City, String Country) throws SQLException {
        String selectQuery = "SELECT * FROM Bank WHERE Code = " + code;
        Statement selectStatement = connection.createStatement();
        ResultSet resultSet = selectStatement.executeQuery(selectQuery);
        if (resultSet.next()) {
            // Bank with the same bank code already exists
            resultSet.close();
            selectStatement.close();
            return false;
        } else {
            String sql = "INSERT INTO Bank(Code,Name,Street,City,Country) " +
                    "VALUES(" + code + ",'" + name + "','" + Street + "','" + City + "','" + Country + "');";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);


            statement.close();
            resultSet.close();
            selectStatement.close();
            return true;
        }
    }

    //==================================================================================================================

    public List<List<String>> getLoans(int ss) throws SQLException {
        String selectQuery = "select Loan.LoanNumber,Loan.BranchNumber ,Loan.BankCode\n" +
                "     , Loan.LoanAmount, Loan.LoanType, Employee.SSN\n" +
                "     , Employee.FName, Employee.LName\n" +
                "from Loan , Employee\n" +
                "where Loan.BranchNumber = Employee.BranchNumber\n" +
                "and Loan.BankCode = Employee.BankCode and Employee.SSN =" +ss+ " ;";

        Statement selectStatement = connection.createStatement();
        ResultSet resultSet = selectStatement.executeQuery(selectQuery);
        List<List<String>> list = new LinkedList<>();

        while (resultSet.next()){
            List<String> loan = new LinkedList<>();
            loan.add(resultSet.getString("LoanNumber"));
            loan.add(resultSet.getString("BranchNumber"));
            loan.add(resultSet.getString("BankCode"));
            loan.add(resultSet.getString("LoanAmount"));
            loan.add(resultSet.getString("LoanType"));
            loan.add(resultSet.getString("SSN"));
            loan.add(resultSet.getString("FName"));
            loan.add(resultSet.getString("LName"));

            list.add(loan);

        }
        resultSet.close();
        selectStatement.close();
        connection.close();

        return list;

    }

    //==================================================================================================================

    public void updateStateToPayed(String customerSSN, String loanNumber) throws SQLException {
        String sql = "update LoanRequests set Status = 'Payed' " +
                "where CustomerSSN = " + customerSSN + " and LoanNumber = " + loanNumber + ";";

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);

        statement.close() ;
        connection.close();
    }

    //==================================================================================================================
    public void getStartedLoans(List<List<String>> info, int empSSN) throws SQLException {
        String sql = "Select CustomerSSN, LoanRequests.LoanNumber, LoanAmount, LoanType\n" +
                "from LoanRequests, Loan\n" +
                "where LoanRequests.LoanNumber = Loan.LoanNumber\n" +
                "and Status = 'Started'  and BranchNumber = (SELECT BranchNumber from Employee\n" +
                "                                           where SSN = " + empSSN + ")\n" +
                "                        and BankCode = (SELECT BankCode from Employee\n" +
                "                                           where SSN = " + empSSN + ") ;" ;

        Statement statement = connection.createStatement() ;
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()){
            List<String> row = new LinkedList<>();

            row.add(Integer.toString((resultSet.getInt("CustomerSSN")))) ;
            row.add(Integer.toString((resultSet.getInt("LoanNumber")))) ;
            row.add(Integer.toString((resultSet.getInt("LoanAmount")))) ;
            row.add((resultSet.getString("LoanType"))) ;

            info.add(row) ;
        }

        statement.close() ;
        connection.close();
    }

    //==================================================================================================================

    public boolean addEmployee(int SSN,double salary,String firstName,String lastName,String email,String password,int branchNumber,int bankCode) throws SQLException {
        String selectQuery = "SELECT * FROM Employee WHERE SSN = " + SSN;
        Statement selectStatement = connection.createStatement();
        ResultSet resultSet = selectStatement.executeQuery(selectQuery);

        if (resultSet.next()) {
            resultSet.close();
            selectStatement.close();
            return false;
        }

        selectQuery = "SELECT * FROM Branch WHERE BranchNumber = " + branchNumber + " AND BankCode = " + bankCode;
        selectStatement = connection.createStatement();
        resultSet = selectStatement.executeQuery(selectQuery);

        if(!resultSet.next()) {
            resultSet.close();
            selectStatement.close();
            return false;
        }
        String insertQuery = "INSERT INTO Employee (SSN, Salary, FName, LName, Email,Password,BranchNumber,BankCode) VALUES ("
                + SSN + ", "
                + salary + ", '"
                + firstName + "', '"
                + lastName + "', '"
                + email + "', '"
                + password + "', '"
                + branchNumber + "', '"
                + bankCode + "')";
        Statement insertStatement = connection.createStatement();
        int rowsAffected = insertStatement.executeUpdate(insertQuery);
        insertStatement.close();

        resultSet.close();
        selectStatement.close();

        return rowsAffected > 0;
    }

    //==================================================================================================================
    public boolean DeleteCustomer(int employeeSSN, int CustomerSSN)
            throws SQLException {

        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Customer , Account  WHERE Customer.SSN = Account.CustomerSSN and SSN = '" + CustomerSSN + "'";

        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            String sql = "DELETE FROM Customer WHERE  SSN = '" + CustomerSSN + "'";
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            sql = "DELETE FROM Account WHERE  CustomerSSN = '" + CustomerSSN + "'";
            statement.executeUpdate(sql);

            JOptionPane.showMessageDialog(null,
                    "Customer deleted successfully!",
                    "Successful Operation!",
                    JOptionPane.INFORMATION_MESSAGE);
            new EmployeeForm(null,employeeSSN);
            return true;
        }

        JOptionPane.showMessageDialog(null,
                "Couldn't find Customer",
                "Invalid SSN!!!",
                JOptionPane.ERROR_MESSAGE);
        connection.close();
        statement.close();
        return false;

    }

    //==================================================================================================================
    public void sentrequestLoan(int ssn, String loanNum) throws SQLException {
        String sql = "insert into LoanRequests values(" + ssn + "," + loanNum + ", 'Pending' )";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }
    //==================================================================================================================

    public void updateLoanstatu(int ssn, String loanNum) throws SQLException {
        String sql = "UPDATE LoanRequests " +
                "SET Status = 'Started' " +
                "WHERE CustomerSSN= "+ssn+" and LoanNumber= "+loanNum+" ;";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }
    //==================================================================================================================

    public List<List<String>> showLoanList(int ssn) throws SQLException {

        String sql = "SELECT Loan.LoanNumber, Loan.BranchNumber, Loan.BankCode, Loan.LoanAmount, Loan.LoanType " +
                "FROM Loan, Customer " +
                "WHERE Customer.SSN = " + ssn + " AND " +
                "Customer.BranchNumber = Loan.BranchNumber AND " +
                "Customer.BankCode = Loan.BankCode";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        List<List<String>> list = new LinkedList<>();

        while (resultSet.next()) {
            List<String> Loan = new LinkedList<>();

            Loan.add(Integer.toString(resultSet.getInt("LoanNumber")));
            Loan.add(Integer.toString(resultSet.getInt("BranchNumber")));
            Loan.add(Integer.toString(resultSet.getInt("BankCode")));
            Loan.add(Integer.toString(resultSet.getInt("LoanAmount")));
            Loan.add(resultSet.getString("LoanType"));

            list.add(Loan);
        }

        return list;
    }
    //==================================================================================================================

    public List<List<String>> getAcceptedList(int ssn) throws SQLException {
        String sql = "SELECT * from LoanRequests where CustomerSSN = " + ssn +
                " and Status='Accepted';";


        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        List<List<String>> list = new LinkedList<>();

        while (resultSet.next()) {
            List<String> Loan = new LinkedList<>();

            Loan.add(Integer.toString(resultSet.getInt("CustomerSSN")));
            Loan.add(Integer.toString(resultSet.getInt("LoanNumber")));
            Loan.add(resultSet.getString("Status"));

            list.add(Loan);
        }

        return list;

    }
}

