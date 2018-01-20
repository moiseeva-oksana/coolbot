package repositories;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapRepo {
    private static Map<Integer, List<String>> history = new HashMap<>();

    public void add(Message message) {
        history.putIfAbsent(message.getFrom().getId(), new ArrayList<>());
        history.get(message.getFrom().getId()).add(message.getText());
    }

    public List<String> getAllNotesOfUser(User user) {
        return history.getOrDefault(user.getId() , new ArrayList<>());
    }
}
