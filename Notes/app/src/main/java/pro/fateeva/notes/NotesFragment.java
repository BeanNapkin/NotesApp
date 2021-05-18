package pro.fateeva.notes;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment implements Serializable {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NOTES = "param1";
    private boolean isInitialized;

    public NotesFragment() {
        // Required empty public constructor
    }

    public static NotesFragment newInstance(Notes notes) {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_NOTES, notes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!isInitialized) {
            final Notes notes = (Notes) getArguments().getSerializable(ARG_NOTES);

            for (int i = 0; i < notes.notes.size(); i++) {
                ViewGroup noteLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.note, (ViewGroup) getView(), false);
                TextView header = noteLayout.findViewById(R.id.textViewHeader);
                TextView description = noteLayout.findViewById(R.id.textViewDescription);
                TextView date = noteLayout.findViewById(R.id.textViewDate);

                header.setText(notes.notes.get(i).getHeader());
                description.setText(notes.notes.get(i).getDescription());
                date.setText(notes.notes.get(i).getDate().toString());

                ((ViewGroup) getView()).addView(noteLayout);

                final int index = i;
                noteLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        noteClicked(notes.notes.get(index));
                    }
                });
            }
            isInitialized = true;
        }
    }

    private void noteClicked(Note note) {
        Intent intent = new Intent(getContext(), NoteActivity.class);
        intent.putExtra("NOTE", note);
        startActivity(intent);
    }
}

