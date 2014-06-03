import java.sql.*;

public class Main {

    public static void main(String[] args) {
        String preparedStatement =
                        "SELECT f.title, count(*) " +
                        "FROM film f, inventory i " +
                        "WHERE f.film_id = i.film_id AND f.film_id = ? " +
                        "GROUP BY f.title " +
                        "HAVING count(*) > 7 " +
                        "ORDER BY count(*) DESC ";
        String simpleQuery =
                "SELECT f.title, count(*) " +
                        "FROM film f, inventory i " +
                        "WHERE f.film_id = i.film_id " +
                        "GROUP BY f.title " +
                        "HAVING count(*) > 7 " +
                        "ORDER BY count(*) DESC ";

        ConnectUtil connectUtil = new ConnectUtil();
        try {
            /**
             * Using a simple query to retrieve data
             */
            Connection connection = connectUtil.getConnection();
            Statement stmt = connectUtil.getStatement(connection);
            ResultSet resultSet = connectUtil.getResultSet(stmt,simpleQuery);
            getCount(resultSet);

            /**
             * Using a prepared statement to execute query
             */
            PreparedStatement pstmt = connectUtil.getPreparedStatement(connection, preparedStatement);
            pstmt.setInt(1, 1);
            ResultSet resultPrepared = pstmt.executeQuery();
            getCount(resultPrepared);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void getCount(ResultSet rset) throws SQLException {
        while (rset.next()) {
            String title = rset.getString("title");
            String count = rset.getString("count(*)");

            System.out.println("Title: " + title + " | " + count);
        }
        System.out.println();
    }
}
