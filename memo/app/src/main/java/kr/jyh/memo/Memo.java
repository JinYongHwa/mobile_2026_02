package kr.jyh.memo;

public class Memo {
    private int id;
    private String content;
    private String date;

    public Memo(int id, String content, String date) {
        this.id = id;
        this.content = content;
        this.date = date;
    }

    public int getId() { return id; }
    public String getContent() { return content; }
    public String getDate() { return date; }
}
