import java.sql.*;

public class ConnectUtil {
    String connectUrl = "jdbc:mysql://localhost:3306/sakila?";
    String driverName = "com.mysql.jdbc.Driver";
    String userId = "root";
    String password = "root";

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(driverName);
        return DriverManager.getConnection(connectUrl, userId, password);
    }

    public  Statement getStatement(Connection connection) throws SQLException {
        return connection.createStatement();
    }

    public ResultSet getResultSet(Statement statement, String query) throws SQLException {
        return statement.executeQuery(query);
    }

    public PreparedStatement getPreparedStatement(Connection connection,String query) throws SQLException {
        return connection.prepareStatement(query);
    }

}
