package services;

import models.Note;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import repositories.HashTagsMapRepo;
import repositories.NotesMapRepo;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class RepoService {
    private NotesMapRepo notesMapRepo = new NotesMapRepo();
    private HashTagsMapRepo hashTagsMapRepo = new HashTagsMapRepo();

    public String allNotesWithHashTag(Message message) {
        String text = message.getText();
        String hashTag = text.substring(text.indexOf("#"));
        StringBuilder result = new StringBuilder(hashTag).append(":\n");
        notesMapRepo.getAllNotesOfUser(message.getFrom())
                .stream()
                .filter(t -> Objects.equals(hashTag, t.getHashTag()))
                .map(Note::getContent)
                .forEach(t -> result.append(t).append("\n"));
        return result.toString();
    }

    public void add(Message message) {
        Note note = new Note();
        note.setUserId(message.getFrom().getId());
        String text = message.getText();
        String content = text;
        if (text.contains("#")) {
            String hashTag = text.substring(text.indexOf('#'));
            content = text.substring(0, text.indexOf('#'));
            note.setHashTag(hashTag);
            hashTagsMapRepo.add(message.getFrom(), text.substring(text.indexOf('#')));
        }
        note.setContent(content);
        notesMapRepo.add(note);
    }

    public String getAllNotesOfUser(User user) {
        StringBuffer result = new StringBuffer();
        List<Note> notes = notesMapRepo.getAllNotesOfUser(user);
        notes.forEach(t -> result.append(t).append("\n"));
        return result.toString();
    }

    public String getAllHashTagsByUser(User user) {
        StringBuffer result = new StringBuffer();
        Set<String> hashTags = hashTagsMapRepo.getAllHastgsByUser(user);
        hashTags.forEach(t -> result.append("/").append(t).append("\n"));
        return result.toString();
    }

    public String deleteNoteById(Message message) {
        int noteId = Integer.parseInt(message.getText().substring(2));
        return notesMapRepo.deleteNoteById(noteId, message.getFrom());
    }
}
