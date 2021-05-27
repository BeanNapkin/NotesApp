package pro.fateeva.notes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteActivity extends AppCompatActivity {

    private Note note = null;
    private boolean isCreatingNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        note = (Note) getIntent().getSerializableExtra("NOTE");
        isCreatingNew = getIntent().getBooleanExtra("CREATE_NEW", false);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("NOTE", note);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {

        if (isCreatingNew) {
            createNewNote();
        } else {
            NoteFragment noteFragment = NoteFragment.createFragment(note);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main, noteFragment);
            fragmentTransaction.commit();

            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openEditForm();
                }
            });
        }
    }

    private void openEditForm() {
        EditFormFragment editNoteFragment = EditFormFragment.createFragment(note);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main, editNoteFragment);
        fragmentTransaction.commit();
    }

    private void createNewNote() {
        openEditForm();
    }
}