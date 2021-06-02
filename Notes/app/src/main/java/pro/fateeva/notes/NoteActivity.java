package pro.fateeva.notes;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteActivity extends AppCompatActivity {

    private Note note = null;
    private boolean isCreatingNew;
    FloatingActionButton fab;
    private boolean isUpdateNote = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
    }

    @Override
    protected void onStart() {
        super.onStart();
        note = (Note) getIntent().getSerializableExtra("NOTE");

        Note noteToUpdateContext = (Note) getIntent().getSerializableExtra("NOTE_TO_UPDATE_CONTEXT");

        if (noteToUpdateContext != null) {
            note = noteToUpdateContext;
            isUpdateNote = true;
        }

        isCreatingNew = getIntent().getBooleanExtra("CREATE_NEW", false);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("NOTE", note);
            startActivity(intent);
            finish();
        }
        initView(isUpdateNote);
    }

    private void initView(Boolean isUpdateNote) {
        fab = findViewById(R.id.fab);

        initToolbar();

        if (isUpdateNote) {
            openEditForm();
        } else {
            if (isCreatingNew) {
                createNewNote();
            } else {
                NoteFragment noteFragment = NoteFragment.createFragment(note);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main, noteFragment);
                fragmentTransaction.commit();

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openEditForm();
                    }
                });
            }
        }
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private void openEditForm() {
        EditFormFragment editNoteFragment = EditFormFragment.createFragment(note);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main, editNoteFragment);
        fragmentTransaction.commit();
        fab.hide();

    }

    private void createNewNote() {
        note = new Note();
        openEditForm();
    }
}