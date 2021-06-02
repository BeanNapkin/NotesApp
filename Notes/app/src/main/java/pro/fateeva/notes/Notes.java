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

    public int addNote(Note note){
        notes.add(note);
        return notes.indexOf(note);
    }

    public void updateNote(int position, Note note){
        notes.set(position, note);
    }

    public void deleteNote(int position){
        notes.remove(position);
    }

    public void clearNote(){
        notes.clear();
    }
}

