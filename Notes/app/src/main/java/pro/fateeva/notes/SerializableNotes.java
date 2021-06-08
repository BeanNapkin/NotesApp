package pro.fateeva.notes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SerializableNotes implements Serializable {

    private final List<Note> notes;

    public SerializableNotes(List<Note> notes) {
        this.notes = notes;
    }

    public List<Note> getNotes() {
        return notes;
    }

}
