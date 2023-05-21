import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DataBase {
    Connection connection ;
    public DataBase() throws SQLException {
        String currentDir=java.lang.System.getProperty("user.dir");
        String url="jdbc:sqlite:"+currentDir+"\\identifier.sqlite";
        connection = DriverManager.getConnection(url);
    }

    public List<List<String>> showList() throws SQLException {
        String sql = "Select * from CUSTOMER;" ;
        Statement statement = connection.createStatement() ;
        ResultSet resultSet = statement.executeQuery(sql) ;
        List<List<String>> list = new LinkedList<>() ;

        while (resultSet.next()){
            List<String> customer = new LinkedList<>();

            customer.add(Integer.toString(resultSet.getInt("SSN"))) ;
            customer.add(resultSet.getString("FName")) ;
            customer.add(resultSet.getString("LName")) ;
            customer.add(resultSet.getString("Phone")) ;
            customer.add(resultSet.getString("Country")) ;
            customer.add(resultSet.getString("City")) ;
            customer.add(resultSet.getString("Street")) ;
            customer.add(Integer.toString(resultSet.getInt("BuildingNumber"))) ;
            customer.add(resultSet.getString("Email")) ;
            customer.add(resultSet.getString("Password")) ;

            this.getBranchesInfo(resultSet.getInt("BankCode"),
                    resultSet.getInt("BranchNumber"),
                    customer) ;

            list.add(customer) ;
        }

        return list ;
    }

    private void getBanksInfo(int bankId, List<String> customer) throws SQLException {
        String sql = "Select * from Bank where Code =" + bankId + ";";
        Statement statement = connection.createStatement() ;

        ResultSet resultSet = statement.executeQuery(sql);
        customer.add(resultSet.getString("Name"));
    }

    private void getBranchesInfo(int bankCode, int Id, List<String> customer) throws SQLException {
        String sql = "Select * from Branch where BranchNumber ="+ Id + " and BankCode = " + bankCode + ";";
        Statement statement = connection.createStatement() ;

        ResultSet resultSet = statement.executeQuery(sql);
        int bankId = 0 ;
        bankId = resultSet.getInt("BankCode") ;
        customer.add(resultSet.getString("Name"));

        getBanksInfo(bankId, customer) ;
    }
    public Connection getConnection()
    {
        return connection;
    }
}
