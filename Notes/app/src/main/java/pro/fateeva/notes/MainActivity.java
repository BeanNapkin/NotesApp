package pro.fateeva.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Note> notes = new ArrayList<Note>();
    Note selectedNote = null;
    private final static String CURRENT_NOTE = "note";
    
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
                Intent intent = new Intent(this, NoteActivity.class);
                intent.putExtra("NOTE", selectedNote);
                startActivity(intent);
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
}
