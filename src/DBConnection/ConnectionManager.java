package DBConnection;

import Model.TableTennisPlayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionManager {

    private static Connection connection;

    private static Connection connect() {
        try {
            connection = DriverManager.getConnection
                    ("jdbc:mysql://localhost:33067/btp", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insert(TableTennisPlayer tennisPlayer) {
        String sql = "INSERT INTO ratingliste(Placement,Id,Name,Rating,Difference,Matches) VALUES(?,?,?,?,?,?)";

        try (Connection conn = connect()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, tennisPlayer.getPlacement());
            preparedStatement.setString(2, tennisPlayer.getPlayerId());
            preparedStatement.setString(3, tennisPlayer.getName());
            preparedStatement.setInt(4, tennisPlayer.getRating());
            preparedStatement.setInt(5, tennisPlayer.getMatches());
            preparedStatement.setInt(6, tennisPlayer.getDifference());

            preparedStatement.executeUpdate();

            disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
