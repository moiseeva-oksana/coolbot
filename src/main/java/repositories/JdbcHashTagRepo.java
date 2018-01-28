package repositories;

import org.telegram.telegrambots.api.objects.User;
import repositories.interfaces.HashTagsRepo;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class JdbcHashTagRepo implements HashTagsRepo {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bot_schema" +
            "?verifyServerCertificate=false" +
            "&useSSL=false" +
            "&requireSSL=false" +
            "&useLegacyDatetimeCode=false" +
            "&amp" +
            "&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "root";

    private static final String INSERT = "insert into hash_tags(hashTag, userId) values (?, ?)";
    private static final String GET_ALL_BY_USER_ID = "select * from hash_tags where userId = ?";

    public JdbcHashTagRepo() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(User user, String hash) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setString(1, hash);
            preparedStatement.setInt(2, user.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Set<String> getAllHastgsByUser(User user) {
        Set<String> result = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BY_USER_ID)
        ) {
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString("hashTag"));
            }
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
