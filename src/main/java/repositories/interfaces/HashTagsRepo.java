package repositories.interfaces;

import org.telegram.telegrambots.api.objects.User;

import java.util.Set;

public interface HashTagsRepo {
    void add(User user, String hash);

    Set<String> getAllHastgsByUser(User user);
}
