package repositories;

import models.Note;
import org.telegram.telegrambots.api.objects.User;

import java.util.*;

public class NotesMapRepo {
    private static Map<Integer, List<Note>> notes = new HashMap<>();

    public void add(Note note) {
        notes.putIfAbsent(note.getUserId(), new ArrayList<>());
        notes.get(note.getUserId()).add(note);
    }

    public List<Note> getAllNotesOfUser(User user) {
        return notes.getOrDefault(user.getId(), new ArrayList<>());
    }

    public void deleteNoteById(int noteId, User user) {
        List<Note> allNotesOfUser = getAllNotesOfUser(user);
        allNotesOfUser.removeIf(t -> t.getId() == noteId);
    }
}
