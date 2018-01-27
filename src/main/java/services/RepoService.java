package services;

import models.Note;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import repositories.HashTagsMapRepo;
import repositories.NotesMapRepo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RepoService {
    private NotesMapRepo notesMapRepo = new NotesMapRepo();
    private HashTagsMapRepo hashTagsMapRepo = new HashTagsMapRepo();

    public String allNotesWithHashTag(Message message) {
        String text = message.getText();
        String hash = text.substring(text.indexOf("#"));
        StringBuilder result = new StringBuilder(hash).append(":\n");
        notesMapRepo.getAllNotesOfUser(message.getFrom())
                .stream()
                .filter(t -> t.getContent().endsWith(hash))
                .map(t -> t.getContent().substring(0, t.getContent().indexOf("#")))
                .forEach(t -> result.append(t).append("\n"));
        return result.toString();
    }

    public void add(Message message) {
        Note note = new Note(message.getText(), message.getFrom().getId());
        String text = message.getText();
        if (text.contains("#")) {
            hashTagsMapRepo.add(message.getFrom(), text.substring(text.indexOf('#')));
        }
        notesMapRepo.add(note);
    }

    public String getAllNotesOfUser(User user) {
        StringBuffer result = new StringBuffer();
        List<Note> notes = notesMapRepo.getAllNotesOfUser(user);
        notes.forEach(t -> result.append(t).append("\n"));
        return result.toString();
    }

    public String getAllHashtgsByUser(User user) {
        StringBuffer result = new StringBuffer();
        Set<String> hashtags = hashTagsMapRepo.getAllHastgsByUser(user);
        hashtags.forEach(t -> result.append("/").append(t).append("\n"));
        return result.toString();
    }
}
