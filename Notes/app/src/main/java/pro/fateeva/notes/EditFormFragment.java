package pro.fateeva.notes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditFormFragment#createFragment} factory method to
 * create an instance of this fragment.
 */
public class EditFormFragment extends Fragment {

    private static final String ARG_NOTE = "note_item";

    public EditFormFragment() {
        // Required empty public constructor
    }

    public static EditFormFragment createFragment(Note note) {
        EditFormFragment fragment = new EditFormFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_note, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Note note = (Note) getArguments().getSerializable(ARG_NOTE);

        EditText header = getView().findViewById(R.id.editTextHeader);
        EditText description = getView().findViewById(R.id.editTextDescription);
        EditText text = getView().findViewById(R.id.editTextText);

            header.setHint("Заголовок");
            description.setHint("Описание");
            text.setHint("Текст заметки");

            header.setText(note.getHeader());
            description.setText(note.getDescription());
            text.setText(note.getText());
    }
}