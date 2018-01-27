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

    public String deleteNoteById(int noteId, User user) {
        List<Note> allNotesOfUser = getAllNotesOfUser(user);
        String content = null;
        for (Note note : allNotesOfUser) {
            if (note.getId() == noteId) {
                content = note.getContent();
                break;
            }
        }
        allNotesOfUser.removeIf(t -> t.getId() == noteId);
        return content;
    }
}
