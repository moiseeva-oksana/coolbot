package repositories;

import models.Note;
import org.telegram.telegrambots.api.objects.User;
import repositories.interfaces.NotesRepo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcNotesRepo implements NotesRepo {
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
    private static final String SELECT_BY_USER_ID = "select * from notes where userId = ?";
    private static final String DELETE_BY_ID = "delete from notes where id = ?";
    private static final String INSERT = "insert into notes(content, userId, hashTag) values(?,?,?)";

    public JdbcNotesRepo() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Note note) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setString(1, note.getContent());
            preparedStatement.setInt(2, note.getUserId());
            preparedStatement.setString(3, note.getHashTag());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Note> getAllNotesOfUser(User user) {
        List<Note> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_USER_ID)
        ) {
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Note note = new Note();
                note.setId(resultSet.getInt("id"));
                note.setContent(resultSet.getString("content"));
                note.setUserId(resultSet.getInt("userId"));
                note.setHashTag(resultSet.getString("hashTag"));
                result.add(note);
            }
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String deleteNoteById(int noteId, User user) {
        String content = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_USER_ID)) {
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt("id") == noteId) {
                    content = resultSet.getString("content");
                    break;
                }
            }
            resultSet.close();
            try (PreparedStatement deleteStatement = connection.prepareStatement(DELETE_BY_ID)) {
                deleteStatement.setInt(1, noteId);
                deleteStatement.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return content;
    }
}
