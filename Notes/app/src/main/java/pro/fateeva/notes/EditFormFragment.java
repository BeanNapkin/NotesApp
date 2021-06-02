package pro.fateeva.notes;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.SearchView;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditFormFragment#createFragment} factory method to
 * create an instance of this fragment.
 */
public class EditFormFragment extends Fragment {

    private static final String ARG_NOTE = "note_item";
    private static final String NOTE_TO_UPDATE = "note_item_to_update";

    EditText header;
    EditText description;
    EditText text;


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

        setHasOptionsMenu(true);

        Note note = (Note) getArguments().getSerializable(ARG_NOTE);

        header = getView().findViewById(R.id.editTextHeader);
        description = getView().findViewById(R.id.editTextDescription);
        text = getView().findViewById(R.id.editTextText);

        header.setHint("Заголовок");
        description.setHint("Описание");
        text.setHint("Текст заметки");

        header.setText(note.getHeader());
        description.setText(note.getDescription());
        text.setText(note.getText());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_save:
                clickOnSaveButton();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clickOnSaveButton() {
        Note note = (Note) getArguments().getSerializable(ARG_NOTE);
        note.setHeader(header.getText().toString());
        note.setDescription(description.getText().toString());
        note.setText(text.getText().toString());

        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.putExtra(NOTE_TO_UPDATE, note);
        getContext().startActivity(intent);
        finishActivity();
    }

    private void finishActivity() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            getActivity().finish();
        }
    }
}