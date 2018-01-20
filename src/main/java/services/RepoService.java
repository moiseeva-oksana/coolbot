package services;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import repositories.MapRepo;

import java.util.List;
import java.util.stream.Collectors;

public class RepoService {
    private MapRepo mapRepo = new MapRepo();

    public String allNotesWithHashTag(Message message) {
        String text = message.getText();
        String hash = text.substring(text.indexOf("#"));
        return hash + ": " + getAllNotesOfUser(message.getFrom())
                .stream()
                .filter(t -> t.endsWith(hash))
                .map(t->t.substring(0, t.indexOf("#")))
                .collect(Collectors.toList()).toString();
    }

    public void add(Message message) {
        mapRepo.add(message);
    }

    public List<String> getAllNotesOfUser(User user) {
        return mapRepo.getAllNotesOfUser(user);
    }
}
