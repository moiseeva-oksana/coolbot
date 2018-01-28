package models;

public class Note {
    private int id;
    private String content;
    private String hashTag;
    private int userId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(content);
        if(hashTag!=null) {
            result.append(hashTag);
        }
        result.append(" id=").append(id);
        return result.toString();
    }
}
