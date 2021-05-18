package pro.fateeva.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String NOTES_PARAM = "HorizontalActivity";
    List<Note> notes = new ArrayList<Note>();
    NotesFragment notesFragment = NotesFragment.newInstance(new Notes(notes));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Note note = new Note("Пирожки с клубникой", "Рецепт от бабушки", new Date(), "Мягкие вкусные сдобные пирожочки с яркой и ароматной клубникой! Пробуй. Я очень люблю ягоды и фрукты, ведь это море витаминов и они всегда не только радуют глаз, но и очень вкусные! Выпечка с ягодами, а особенно с клубникой не исключение!!! Наверное, при виде таких пирожков никто не останется равнодушным и захочет попробовать хотя бы один-два пирожочка!");
        notes.add(note);

        note = new Note("Поезд", "Планы на мой отпуск", new Date(2021, 1, 10), "Выезжаем в 15.00 с Курского вокзала. Возьми паспорт.");
        notes.add(note);

        note = new Note("План на выходные", "Дела", new Date(2021, 3, 15), "Уборка, спортзал, помыть собаку, купить продукты.");
        notes.add(note);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main, notesFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(NOTES_PARAM, notesFragment);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        notesFragment = (NotesFragment) savedInstanceState.getSerializable(NOTES_PARAM);
    }

}
