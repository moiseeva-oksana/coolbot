package repositories.interfaces;

import models.Note;
import org.telegram.telegrambots.api.objects.User;

import java.util.List;

public interface NotesRepo {
    void add(Note note);

    List<Note> getAllNotesOfUser(User user);

    String deleteNoteById(int noteId, User user);
}
