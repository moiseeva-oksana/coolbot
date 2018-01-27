package repositories;

import models.Note;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;

import java.util.*;

public class NotesMapRepo {
    private static Map<Integer, List<Note>> history = new HashMap<>();

    public void add(Note note) {
        history.putIfAbsent(note.getUserId(), new ArrayList<>());
        history.get(note.getUserId()).add(note);
    }

    public List<Note> getAllNotesOfUser(User user) {
        return history.getOrDefault(user.getId(), new ArrayList<>());
    }
}
