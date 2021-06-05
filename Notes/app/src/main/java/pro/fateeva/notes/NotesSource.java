package pro.fateeva.notes;

import java.io.Serializable;

public interface NotesSource extends Serializable {
    int size();

    int addNote(Note note);

    void updateNote(int position, Note note);

    void deleteNote(int position);

    Note getNote(int position);

}
