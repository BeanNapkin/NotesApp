package pro.fateeva.notes;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {
    private String id = "";
    private String header = "";
    private String description = "";
    private Date date;
    private String text = "";

    public Note(String  id, String header, String description, Date date, String text) {
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

    public String getId() {
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

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
