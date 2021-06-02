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

    public Note() {
        this.header = "";
        this.description = "";
        this.date = new Date();
        this.text = "";
        this.id = 0;
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

    public void setHeader(String header) {
        this.header = header;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
