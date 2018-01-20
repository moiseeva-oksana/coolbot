package repositories;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;

import java.util.*;

public class NotesMapRepo {
    private static Map<Integer, List<String>> history = new HashMap<>();
    private static Map<Integer, Set<String>> hashtags = new HashMap<>();

    public void add(Message message) {
        history.putIfAbsent(message.getFrom().getId(), new ArrayList<>());
        String text = message.getText();
        history.get(message.getFrom().getId()).add(text);
    }

    public List<String> getAllNotesOfUser(User user) {
        return history.getOrDefault(user.getId(), new ArrayList<>());
    }
}
