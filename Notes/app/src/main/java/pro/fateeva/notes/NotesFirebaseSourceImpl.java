package pro.fateeva.notes;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotesFirebaseSourceImpl implements NotesSource {

    public static final String NOTE_COLLECTION = "notes";
    public static final String TAG = "[NotesSourceFirebaseImpl]";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference collection = db.collection(NOTE_COLLECTION);

    final private List<Note> noteData = new ArrayList<>();

    @Override
    public void downloadNotesFromServer(final Consumer<NotesSource> onDownloaded) {
        collection.orderBy(NoteDataMapping.Fields.DATE, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        noteData.clear();

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> doc = document.getData();

                            Note note = NoteDataMapping.toNote(document.getId(), doc);

                            noteData.add(note);
                        }
                        onDownloaded.accept(this);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure", e);
                    }
                });
    }

    @Override
    public int size() {
        return noteData.size();
    }

    @Override
    public void addNote(final Note note) {
        noteData.add(note);
        collection.add(NoteDataMapping.toDocumnet(note)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                note.setId(documentReference.getId());
            }
        })
                .addOnFailureListener(e -> Log.e(null, "error saving note", e))
        ;

    }

    @Override
    public void updateNote(int position, Note note) {
        collection.document(note.getId()).set(NoteDataMapping.toDocumnet(note));
        noteData.set(position, note);
    }

    @Override
    public void deleteNote(int position) {
        collection.document(noteData.get(position).getId()).delete();
        noteData.remove(position);
    }

    @Override
    public Note getNote(int position) {
        return noteData.get(position);
    }

    @Override
    public List<Note> getAllNotes() {
        return noteData;
    }
}
