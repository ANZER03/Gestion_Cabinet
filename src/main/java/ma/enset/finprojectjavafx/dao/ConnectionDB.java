package ma.enset.finprojectjavafx.ConnectionDB;

import java.sql.*;

public class ConnectionDB {

    private static final Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bdcc_tp1_cr",
                    "root", "anoirzerrik2003");
            connection.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }


    public static void main(String[] args) throws SQLException {
        int resultSet = 0;
//        PreparedStatement preparedStatement = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM travailler where idEmploye = ?");
            preparedStatement.setInt(1 , 4);
            resultSet = preparedStatement.executeUpdate();
//            connection.commit();
            if (resultSet > 0) {
//                System.out.println(resultSet.getString(1) + " " + resultSet.getInt(2));
                System.out.println("Delete success! " + resultSet);
            }else {
                System.out.println("No items for delete");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
}
