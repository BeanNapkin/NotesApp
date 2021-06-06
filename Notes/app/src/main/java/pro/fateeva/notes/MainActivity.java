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

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Note> notes = new ArrayList<Note>();
    private Note selectedNote = null;
    private final static String CURRENT_NOTE = "note";
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Note note = new Note(0, "Пирожки с клубникой", "Рецепт от бабушки", new Date(), "Мягкие вкусные сдобные пирожочки с яркой и ароматной клубникой! Пробуй. Я очень люблю ягоды и фрукты, ведь это море витаминов и они всегда не только радуют глаз, но и очень вкусные! Выпечка с ягодами, а особенно с клубникой не исключение!!! Наверное, при виде таких пирожков никто не останется равнодушным и захочет попробовать хотя бы один-два пирожочка!");
        notes.add(note);

        note = new Note(1, "Поезд", "Планы на мой отпуск", new Date(2021, 1, 10), "Выезжаем в 15.00 с Курского вокзала. Возьми паспорт.");
        notes.add(note);

        note = new Note(2, "План на выходные", "Дела", new Date(2021, 3, 15), "Уборка, спортзал, помыть собаку, купить продукты.");
        notes.add(note);

        // При первом запуске тут будет null
        NotesFragment notesFragment = (NotesFragment) getSupportFragmentManager().findFragmentByTag(NotesFragment.TAG);

        // На старт
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (notesFragment != null) {
            fragmentTransaction.remove(notesFragment);
            // Для одной операции одна транзакция
            fragmentTransaction.commitNow(); // Моментальная операция
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
        } else {
            notesFragment = NotesFragment.createFragment(new Notes(notes));
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (savedInstanceState != null){
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
        initView(selectedNote);
    }

    // Ждёт пока какой-нибудь интент придёт (для второго и остальных запусков)
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setNoteFromIntent(intent);
        initView(selectedNote);
    }

    private void setNoteFromIntent(Intent intent){
        Note note = (Note) intent.getSerializableExtra("NOTE");
        selectedNote = note;
    }

    private void initView(Note selectedNote) {
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
        initToolbar();

        NoteFragment noteFragment = (NoteFragment) getSupportFragmentManager().findFragmentByTag(NoteFragment.TAG);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (selectedNote != null) {
            if (noteFragment != null) {
                fragmentTransaction.detach(noteFragment);
                noteFragment.changeNoteInNoteFragment(selectedNote);
                fragmentTransaction.attach(noteFragment);
            } else {
                noteFragment = NoteFragment.createFragment(selectedNote);
                fragmentTransaction.add(R.id.right, noteFragment, NoteFragment.TAG);
            }
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CURRENT_NOTE, selectedNote);
    }

    private Toolbar initToolbar(){
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

                switch (id){
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

        if (toggle.onOptionsItemSelected(item)){
            return true;
        }

        switch (id){
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
        MenuItem search = menu.findItem(R.id. action_search);
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

    public void clickOnAddNewNote(){
        Intent intent = new Intent(this, AddNewNoteActivity.class);
        startActivity(intent);
    }
}
