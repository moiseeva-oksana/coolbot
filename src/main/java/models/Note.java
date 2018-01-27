package models;

public class Note {
    private static int count = 0;
    private int id;
    private String content;
    private int userId;

    public Note(String content, int userId) {
        this.id = ++count;
        this.content = content;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return content + " id=" + id;
    }
}
