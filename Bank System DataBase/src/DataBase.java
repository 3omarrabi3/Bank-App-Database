import javax.swing.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DataBase {
    Connection connection;

    //==================================================================================================================

    public DataBase() throws SQLException {     // Constructor that sets up the connection.
        String currentDir = java.lang.System.getProperty("user.dir");
        String url = "jdbc:sqlite:" + currentDir + "\\identifier.sqlite";
        connection = DriverManager.getConnection(url);
    }

    //==================================================================================================================

    public List<List<String>> showList(int employeeSSN) throws SQLException {   // Show Customer List.

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

        // Close the connection.
        connection.close();
        statement.close();
        return list ;
    }



    //==================================================================================================================

    public List<List<String>> showListPendingLoansTable(int ssn) throws SQLException {  // Show Loan List.
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

        // Close the connection.

        connection.close();
        statement.close();
        return list ;
    }

    //==================================================================================================================

    public void addAccount(int accountNumber, int ssn,
                           String accountType, double accountBalance,
                           int branchNumber, int bankCode) throws SQLException {    // add a new account to the database.

        String sql = "insert into Account values (" + accountNumber + "," + branchNumber + "," + bankCode + "," +
                " " + ssn + ", " + accountBalance + ", '" + accountType + "' );";   // SQL statement that insert into the table.

        // Execute the statement.
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);

        // Close the connection.
        connection.close();
        statement.close();

    }

    //==================================================================================================================

    public void addCustomer(int ssn, String firstName, String lastName, int phone,
                            String street, String city, String country, int buildingNumber,
                            String email, String password, int branchNumber,
                            int bankCode) throws SQLException {     // adds customer to database.

        String sql = "insert into Customer values(" + ssn + ",'" + firstName + "','" + lastName + "',"
                + phone + ",'" + street + "','" + city + "','" + country + "'," + buildingNumber + ",'" + email + "','"
                + password + "'," + branchNumber + "," + bankCode + ");";   // SQL Statement to INSERT into Customer Table.

        // Execute SQL statement.
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);

    }

    //==================================================================================================================
    public boolean addBranch(int branchNumber, int bankCode, String name, String street, String city, String country) throws SQLException {

        // Checks if the Bank and the branch are already exists.
        String selectQuery = "SELECT * FROM Branch WHERE BranchNumber = " + branchNumber + " AND BankCode = " + bankCode;
        Statement selectStatement = connection.createStatement();
        ResultSet resultSet = selectStatement.executeQuery(selectQuery);

        if (resultSet.next()) {
            // Branch with the same branch number and bank code already exists
            resultSet.close();
            selectStatement.close();
            return false;
        }

        else {
            String insertQuery = "INSERT INTO Branch (BranchNumber, BankCode, Name, Street, City, Country) VALUES ("
                    + branchNumber + ", "
                    + bankCode + ", '"
                    + name + "', '"
                    + street + "', '"
                    + city + "', '"
                    + country + "')";   // SQL statement that inserts in the table.

            // Execute the statement.
            Statement insertStatement = connection.createStatement();
            int rowsAffected = insertStatement.executeUpdate(insertQuery);
            insertStatement.close();

            // Close the connection.
            resultSet.close();
            selectStatement.close();

            return rowsAffected > 0;
        }


    }

    //==================================================================================================================

    public boolean Login(String email, String password, String Table) throws SQLException {   // Function of login operation.

        // Statement that check the existence of the input data.
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

            // Detect whether the user is Customer or Employee.
            if(Table.equals("Employee"))
            {
                new EmployeeForm(null, SSN);
            }
            else {
                new CustomersForm(null,SSN);
            }

            // Close the Connection.
            connection.close();
            statement.close();

            return true;
        }

        return false;

    }

    //==================================================================================================================

    public boolean UpdateCustomer(int employeeSSN, int CustomerSSN) // Update the customer in the database.
            throws SQLException {

        // Statement to select the customer in the database.
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Customer , Account  WHERE Customer.SSN = Account.CustomerSSN and SSN = '" + CustomerSSN + "'";

        String FirstName, Email, Password, LastName, Country, City, Street, AccountType;
        int Phone, BuildingNumber, BranchNumber, BankCode;
        double AccountBalance;

        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {

            // Getting the current values.
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

            // Passing the values to the Customer Info Form Constructor.
            new CustomerInfoForm(null, employeeSSN, CustomerSSN,
                    FirstName, LastName, Email, Password, Country, City, Street
                    , AccountType, BankCode, BranchNumber, BuildingNumber, Phone, AccountBalance);

            // Close the connection.
            connection.close();
            statement.close();

            return true;

        }

        // If the SSN was wrong.
        JOptionPane.showMessageDialog(null,
                "Couldn't find Customer",
                "Invalid SSN!!!",
                JOptionPane.ERROR_MESSAGE);

        return false;

    }

    //==================================================================================================================

    public void setCustomer(    // Update the customer in the database.
            int ssn, String firstName, String lastName, int phone,
            String street, String city, String country, int buildingNumber,
            String email, String password, int branchNumber,
            int bankCode) throws SQLException { // Get the new field values.

        // SQL statement that updates the customer with the new values.
        String sql = "UPDATE Customer SET FName = '" + firstName + "', LName = '" + lastName + "', Phone = " + phone + "," +
                " Street = '" + street + "', City = '" + city + "', Country = '" + country + "', BuildingNumber = " + buildingNumber + ", " +
                "Email = '" + email + "', Password = '" + password + "', BranchNumber = " + branchNumber + ", BankCode = " + bankCode + " WHERE SSN = " + ssn;

        // Execute the statement.
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);

    }

    //==================================================================================================================

    public void setAccount(int ssn,
                           String accountType, double accountBalance,
                           int branchNumber, int bankCode) throws SQLException {    // Update the account in the database.

        // Sql statement to update the account in the database.
        String sql = "UPDATE Account SET Type = '" + accountType + "', Balance = " + accountBalance + "" +
                ", BranchNumber = " + branchNumber + ", BankCode = " + bankCode + "" +
                " WHERE CustomerSSN = " + ssn;

        // Execute the statement.
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);

        // Close the connection.
        connection.close();
        statement.close();

    }

    //==================================================================================================================

    public Boolean addBank(int code, String name, String Street, String City, String Country) throws SQLException {

        // SQL statement to select the bank with the specified code.
        String selectQuery = "SELECT * FROM Bank WHERE Code = " + code;
        Statement selectStatement = connection.createStatement();
        ResultSet resultSet = selectStatement.executeQuery(selectQuery);

        if (resultSet.next()) {
            // Bank with the same bank code already exists
            resultSet.close();
            selectStatement.close();
            return false;
        }

        else {
            // SQL statement that insert into the bank table.
            String sql = "INSERT INTO Bank(Code,Name,Street,City,Country) " +
                    "VALUES(" + code + ",'" + name + "','" + Street + "','" + City + "','" + Country + "');";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

            // Close the connection.
            statement.close();
            resultSet.close();
            selectStatement.close();

            return true;

        }
    }

    //==================================================================================================================

    public List<List<String>> getLoans(int ss) throws SQLException {    // Show Loan List.
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
        // Status of loan.

        String sql = "update LoanRequests set Status = 'Payed' " +
                "where CustomerSSN = " + customerSSN + " and LoanNumber = " + loanNumber + ";";

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);

        statement.close() ;
        connection.close();
    }

    //==================================================================================================================
    public void getStartedLoans(List<List<String>> info, int empSSN) throws SQLException {
        // get the started loans.
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

        // SQL statement to get the employee with the specified ssn.
        String selectQuery = "SELECT * FROM Employee WHERE SSN = " + SSN;
        Statement selectStatement = connection.createStatement();
        ResultSet resultSet = selectStatement.executeQuery(selectQuery);

        // Check if the employee exists.
        if (resultSet.next()) {
            resultSet.close();
            selectStatement.close();

            return false;

        }

        // SQL statement to get the branch with the specified branch number and bank code.
        selectQuery = "SELECT * FROM Branch WHERE BranchNumber = " + branchNumber + " AND BankCode = " + bankCode;
        selectStatement = connection.createStatement();
        resultSet = selectStatement.executeQuery(selectQuery);

        // Check if the branch exists.
        if(!resultSet.next()) {
            resultSet.close();
            selectStatement.close();

            return false;

        }

        // SQL statement to insert into the employee table.
        String insertQuery = "INSERT INTO Employee (SSN, Salary, FName, LName, Email,Password,BranchNumber,BankCode) VALUES ("
                + SSN + ", "
                + salary + ", '"
                + firstName + "', '"
                + lastName + "', '"
                + email + "', '"
                + password + "', '"
                + branchNumber + "', '"
                + bankCode + "')";

        // Execute the statement.
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

        // SQL statement that select the customer and the account by joining the 2 tables.
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Customer , Account  WHERE Customer.SSN = Account.CustomerSSN and SSN = '" + CustomerSSN + "'";

        // if we find the 2 rows we delete them.
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

        // Else we return false with a message.
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
        // Sent request to employee for a loan.
        String sql = "insert into LoanRequests values(" + ssn + "," + loanNum + ", 'Pending' )";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    //==================================================================================================================

    public void updateLoanstatu(int ssn, String loanNum) throws SQLException {
        // update the status of the loan.
        String sql = "UPDATE LoanRequests " +
                "SET Status = 'Started' " +
                "WHERE CustomerSSN= "+ssn+" and LoanNumber= "+loanNum+" ;";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    //==================================================================================================================

    public List<List<String>> showLoanList(int ssn) throws SQLException {
        // Show loan list.

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
        // Get the accepted loans.
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

    //==================================================================================================================
    public boolean checkExistance(int x, int y) throws SQLException {
        // function to check the existence of the loan .
        String selectQuery = "SELECT * FROM LoanRequests WHERE LoanNumber = " + y + " AND CustomerSSN = " + x;
        Statement selectStatement = connection.createStatement();
        ResultSet resultSet = selectStatement.executeQuery(selectQuery);
        if (!resultSet.next()) {
            connection.close();
            resultSet.close();
            selectStatement.close();
            return false;
        }
        connection.close();
        selectStatement.close();
        return true;
    }

    //==================================================================================================================

    public StringBuilder getData() throws SQLException {
        // function to return data.
        StringBuilder data = new StringBuilder();

        {
            String sql = """
                    SELECT count(SSN)
                    from Customer ;
                    """;

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            data.append("There are ").append(resultSet.getInt("count(SSN)")).append(" Customers");
        }

        {
            String sql = """
                        select DISTINCT SSN, FName, LName
                        from Customer ;
                    """ ;

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            int counter = 1 ;
            while (resultSet.next()){
                data.append("\n\tCustomer #").append(counter++).append(" SSN -> ").append(resultSet.getString("SSN")).
                        append(" & Name -> ").append(resultSet.getString("FName")).append(resultSet.getString("LName"));
            }
        }

        {
            data.append("\n\nThe customers who make a loans") ;

            String sql = """
                        select DISTINCT SSN, FName, LName
                        from Customer
                        where SSN in (select CustomerSSN from LoanRequests) ;
                    """;

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            int counter = 1 ;
            while (resultSet.next()){
                data.append("\n\tCustomer #").append(counter++).append(" SSN -> ").append(resultSet.getString("SSN")).
                        append(" & Name -> ").append(resultSet.getString("FName")).append(resultSet.getString("LName"));
            }
        }

        {
            String sql = """
                    SELECT count(SSN)
                    from Employee ;
                    """;

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            data.append("\n\nThere are ").append(resultSet.getInt("count(SSN)")).append(" Employees");
        }

        {
            String sql = """
                        select DISTINCT SSN, FName, LName
                        from Employee ;
                    """;

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            int counter = 1 ;
            while (resultSet.next()){
                data.append("\n\tEmployee #").append(counter++).append(" SSN -> ").append(resultSet.getString("SSN")).
                        append(" & Name -> ").append(resultSet.getString("FName")).append(resultSet.getString("LName"));
            }
        }

        {
            data.append("\n") ;

            String sql = """
                    SELECT count(Code)
                    from Bank ;
                    """;

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            data.append("\nThere are ").append(resultSet.getInt("count(Code)")).append(" Banks");
        }

        {
            String sql = """
                        SELECT Bank.Name, COUNT(Branch.BranchNumber) AS NumBranches
                        FROM Bank
                        LEFT JOIN Branch ON Bank.Code = Branch.BankCode
                        GROUP BY Bank.Code, Bank.Name;
                    """;

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                data.append("\n\tBank ").append(resultSet.getString("Name")).append(" has ");
                data.append(resultSet.getString("NumBranches")) ;
            }
        }

        {
            String sql = """
                        SELECT Bank.Name, COUNT(Branch.BranchNumber) AS NumBranches
                        FROM Bank
                        LEFT JOIN Branch ON Bank.Code = Branch.BankCode
                        GROUP BY Bank.Code, Bank.Name;
                    """;

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                data.append("\n\tBank ").append(resultSet.getString("Name")).append(" has ");
                data.append(resultSet.getString("NumBranches")) ;
            }
        }

        {
            data.append("\n\nThe Loans in the banks") ;

            String sql = """
                     select LoanType, LoanAmount, Bank.Name as BankName, Branch.Name as BranchName
                     from Loan, Bank, Branch
                     where Loan.BankCode = Bank.Code
                     and Loan.BranchNumber = Branch.BranchNumber ;
                    """;

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            int Counter = 1 ;
            while (resultSet.next()){
                data.append("\n\tLoan# ").append(Counter++).append(" Loan Type -> ").
                        append(resultSet.getString("LoanType")).
                        append(" & LoanAmount -> ").append(resultSet.getDouble("LoanAmount")).
                        append(" & Bank Name ").append(resultSet.getString("BankName")).
                        append(" & -> Branch Name -> ").append(resultSet.getString("BranchName"));
            }
        }

        return data ;
    }

    //==================================================================================================================
    public boolean checkExistanceAddCustomer(int x, int y) throws SQLException {
        // function to check of the existence of the branch and the bank.
        String selectQuery = "SELECT * FROM Branch WHERE BranchNumber = " + x + " AND BankCode = " + y;
        Statement selectStatement = connection.createStatement();
        ResultSet resultSet = selectStatement.executeQuery(selectQuery);
        return resultSet.next();
    }

    //==================================================================================================================
    public boolean addLoan(int SSN,int loanNumber,int branchNumber, int bankCode , double amount , String type) throws SQLException {

        // SQL statement that select employee with specified data.
        String selectQuery = "SELECT * FROM Employee WHERE SSN = " + SSN + " AND BranchNumber = " + branchNumber + " AND BankCode = " + bankCode;
        Statement selectStatement = connection.createStatement();
        ResultSet resultSet = selectStatement.executeQuery(selectQuery);

        // if the employee was not found then return false.
        if (!resultSet.next()) {
            resultSet.close();
            selectStatement.close();
            return false;
        }

        // else create the loan into the database.
        String insertQuery = "INSERT INTO Loan (LoanNumber, BranchNumber, BankCode, LoanAmount, LoanType) VALUES ("
                + loanNumber + ", '"
                + branchNumber + "', '"
                + bankCode + "', '"
                + amount + "', '"
                + type + "')";

        Statement insertStatement = connection.createStatement();
        int rowsAffected = insertStatement.executeUpdate(insertQuery);
        insertStatement.close();

        resultSet.close();
        selectStatement.close();

        return rowsAffected > 0;
    }

    //==================================================================================================================
    public boolean checkExistanceLoanNumber(int x) throws SQLException {
        // function to check of the existence of the branch and the bank.
        String selectQuery = "SELECT * FROM Loan WHERE LoanNumber = " + x ;
        Statement selectStatement = connection.createStatement();
        ResultSet resultSet = selectStatement.executeQuery(selectQuery);
        return resultSet.next();
    }
}

