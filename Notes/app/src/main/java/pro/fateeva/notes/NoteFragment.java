package pro.fateeva.notes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#createFragment} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NOTE = "note";
    public static final String TAG = "noteFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NoteFragment() {
        // Required empty public constructor
    }

    public static NoteFragment createFragment(Note note) {
        NoteFragment fragment = new NoteFragment();
        fragment.changeNoteInNoteFragment(note);
        return fragment;
    }

    public void changeNoteInNoteFragment(Note note) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_NOTE, note);
        setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Note note = (Note) getArguments().getSerializable(ARG_NOTE);
        TextView header = getView().findViewById(R.id.textViewHeader);
        TextView date = getView().findViewById(R.id.textViewDate);
        TextView text = getView().findViewById(R.id.textViewText);

        header.setText(note.getHeader());
        date.setText(note.getDate().toString());
        text.setText(note.getText());
    }
}
