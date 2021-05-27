package pro.fateeva.notes;

import java.io.Serializable;
import java.util.List;

public class Notes implements Serializable {

    private List<Note> notes;

    public Notes(List<Note> notes) {
        this.notes = notes;
    }

    public List<Note> getNotes() {
        return notes;
    }
}

