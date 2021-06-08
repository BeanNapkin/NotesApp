package pro.fateeva.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NotesSource notes = new NotesFirebaseSourceImpl();

    private Note selectedNote = null;
    private final static String CURRENT_NOTE = "note_item";
    private ActionBarDrawerToggle toggle;
    private static final String NOTE_TO_UPDATE = "note_item_to_update";
    NotesFragment notesFragment;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // При первом запуске тут будет null
        notesFragment = (NotesFragment) getSupportFragmentManager().findFragmentByTag(NotesFragment.TAG);

        // На старт
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (notesFragment != null) {
            fragmentTransaction.remove(notesFragment);
            // Для одной операции одна транзакция
            fragmentTransaction.commitNow(); // Моментальная операция
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
        } else {
            notesFragment = NotesFragment.createFragment(notes);
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (savedInstanceState != null) {
                selectedNote = (Note) savedInstanceState.getSerializable(CURRENT_NOTE);
                if (selectedNote != null) {
                    Intent intent = new Intent(this, NoteActivity.class);
                    intent.putExtra("NOTE", selectedNote);
                    startActivity(intent);
                }
            }
            // Куда добавить, что добавить и как его назвать, чтобы потом достать через findFragmentByTag
            fragmentTransaction.add(R.id.main, notesFragment, NotesFragment.TAG);
        } else {
            fragmentTransaction.add(R.id.left, notesFragment, NotesFragment.TAG);
        }
        // Марш
        fragmentTransaction.commit();

    }

    // Для первого запуска активити
    @Override
    protected void onStart() {
        super.onStart();
        notesFragment.showProgressBar(true);
        initView(selectedNote);
    }

    // Ждёт пока какой-нибудь интент придёт (для второго и остальных запусков)
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Note noteToUpdate = (Note) intent.getSerializableExtra(NOTE_TO_UPDATE);
        Note note = (Note) intent.getSerializableExtra("NOTE");
        Note noteToDelete = (Note) intent.getSerializableExtra("NOTE_TO_DELETE");
        Note noteToUpdateContext = (Note) intent.getSerializableExtra("NOTE_TO_UPDATE_CONTEXT");

        if (noteToUpdate != null) {
            updateNotesFragment(noteToUpdate);
        } else if (noteToDelete != null) {
            deleteNote(noteToDelete);
        } else if (noteToUpdateContext != null) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                intent = new Intent(this, NoteActivity.class);
                intent.putExtra("NOTE_TO_UPDATE_CONTEXT", noteToUpdateContext);
                startActivity(intent);
            } else {
                openEditForm(noteToUpdateContext);
            }
        } else {
            setNoteFromIntent(note);
            initView(selectedNote);
        }
    }

    private void deleteNote(Note noteToDelete) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.getNote(i).getId().equals(noteToDelete.getId())) {
                notes.deleteNote(i);
                notesFragment.changeNotes(notes);
                break;
            }
        }
    }

    private void updateNotesFragment(Note noteToUpdate) {
        if ("".equals(noteToUpdate.getId())) {
            noteToUpdate.setDate(new Date());
            notes.addNote(noteToUpdate);

            notesFragment.recyclerView.scrollToPosition(notes.size() - 1);
        } else {
            for (int i = 0; i < notes.size(); i++) {
                if (notes.getNote(i).getId().equals(noteToUpdate.getId())) {
                    notes.updateNote(i, noteToUpdate);
                }
            }
        }
        notesFragment.changeNotes(notes);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fab.show();
            selectedNote = noteToUpdate;
            initSelectedNote(selectedNote);
        }
    }

    private void setNoteFromIntent(Note note) {
        selectedNote = note;
    }

    private void initView(final Note selectedNote) {
        Toolbar toolbar = initToolbar();
        fab = findViewById(R.id.fab);
        initDrawer(toolbar);
        initToolbar();
        initSelectedNote(selectedNote);
    }

    private void initSelectedNote(final Note selectedNote) {
        NoteFragment noteFragment = (NoteFragment) getSupportFragmentManager().findFragmentByTag(NoteFragment.TAG);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (selectedNote != null) {
            if (noteFragment != null) {
                fragmentTransaction.detach(noteFragment);
                noteFragment.changeNoteInNoteFragment(selectedNote);
                fragmentTransaction.attach(noteFragment);
            } else {
                noteFragment = NoteFragment.createFragment(selectedNote);
                fragmentTransaction.replace(R.id.right, noteFragment, NoteFragment.TAG);
            }

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openEditForm(selectedNote);
                }
            });

            fragmentTransaction.commit();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CURRENT_NOTE, selectedNote);
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.action_addNew:
                        clickOnAddNewNote();
                        return true;
                    case R.id.action_search:
                        // функция
                        return true;
                    case R.id.action_settings:
                        // функция
                        return true;
                    case R.id.action_about:
                        // функция
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (id) {
            case R.id.action_addNew:
                clickOnAddNewNote();
                return true;
            case R.id.action_search:
                // функция
                return true;
            case R.id.action_settings:
                // функция
                return true;
            case R.id.action_about:
                // функция
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchText = (SearchView) search.getActionView();
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void clickOnAddNewNote() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Intent intent = new Intent(this, NoteActivity.class);
            intent.putExtra("NOTE", new Note());
            intent.putExtra("CREATE_NEW", true);
            startActivity(intent);
        } else {
            openEditForm(new Note());
        }
    }

    private void openEditForm(Note note) {
        EditFormFragment editNoteFragment = EditFormFragment.createFragment(note);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.right, editNoteFragment);
        fragmentTransaction.commit();
        fab.hide();
    }
}
