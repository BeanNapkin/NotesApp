package pro.fateeva.notes;

import java.io.Serializable;
import java.util.List;

public class Notes implements Serializable {

    List<Note> notes;

    public Notes(List<Note> notes) {
        this.notes = notes;
    }
}
