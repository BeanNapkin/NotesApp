package pro.fateeva.notes;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class NotesFirebaseSourceImpl implements NotesSource {

    public static final String NOTE_COLLECTION = "notes";
    public static final String TAG = "[NotesSourceFirebaseImpl]";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference collection = db.collection(NOTE_COLLECTION);

    private List<Note> noteData = new ArrayList<>();

    @Override
    public int size() {
        if (noteData == null) {
            return 0;
        }
        return noteData.size();
    }

    @Override
    public int addNote(Note note) {
        return 0;
    }

    @Override
    public void updateNote(int position, Note note) {

    }

    @Override
    public void deleteNote(int position) {

        collection.document(noteData.get(position).getId()).delete();
    }

    @Override
    public Note getNote(int position) {
        return null;
    }
}
