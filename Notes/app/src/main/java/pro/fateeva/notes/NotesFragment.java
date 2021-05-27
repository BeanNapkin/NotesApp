package pro.fateeva.notes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#createFragment} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment {

    public static final String TAG = "notesFragment";


    Note noteToSave;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_NOTES = "param1";
    private boolean isInitialized;

    public NotesFragment() {
        // Required empty public constructor
    }

    /**
     Создаётся фрагмент списка заметок, создаётмя бандл(конверт). В бандл кладём список заметок
     и этот бандл передаём в аргументы фрагмента.
     */
    public static NotesFragment createFragment(Notes notes) {
        NotesFragment fragment = new NotesFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_NOTES, notes);
        fragment.setArguments(bundle);
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

    public void noteClicked(Note note) {
        Class<?> activity;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            activity = NoteActivity.class;
        } else {
            activity = MainActivity.class;
        }
        Intent intent = new Intent(getContext(), activity);
        intent.putExtra("NOTE", note);
        startActivity(intent);
    }


}

