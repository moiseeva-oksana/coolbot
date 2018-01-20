package repositories;

import org.telegram.telegrambots.api.objects.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HashTagsMapRepo {
    private static Map<Integer, Set<String>> hashtags = new HashMap<>();

    public void add(User user, String hash) {
        hashtags.putIfAbsent(user.getId(), new HashSet<>());
        hashtags.get(user.getId()).add(hash.substring(hash.indexOf('#')));
    }

    public Set<String> getAllHastgsByUser(User user) {
        return hashtags.getOrDefault(user.getId(), new HashSet<>());
    }
}
