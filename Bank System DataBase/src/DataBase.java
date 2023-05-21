import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    public DataBase() throws SQLException {
        String currentDir=java.lang.System.getProperty("user.dir");
        String url="jdbc:sqlite:"+currentDir+"\\identifier.sqlite";
        Connection connection = DriverManager.getConnection(url);

    }


}
