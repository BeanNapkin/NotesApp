package pro.fateeva.notes;

import androidx.core.util.Consumer;

import java.io.Serializable;
import java.util.List;

public interface NotesSource extends Serializable {

    void downloadNotesFromServer(Consumer<NotesSource> onDownloaded);

    int size();

    void addNote(Note note);

    void updateNote(int position, Note note);

    void deleteNote(int position);

    Note getNote(int position);

    List<Note> getAllNotes();
}
