package pro.fateeva.notes;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {
    private int id = 0;
    private String header = "";
    private String description = "";
    private Date date;
    private String text = "";

    public Note(int id, String header, String description, Date date, String text) {
        this.header = header;
        this.description = description;
        this.date = date;
        this.text = text;
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }
}
