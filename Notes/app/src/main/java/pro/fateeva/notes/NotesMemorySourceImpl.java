package pro.fateeva.notes;

import androidx.core.util.Consumer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotesMemorySourceImpl implements NotesSource{

    private List<Note> notes = new ArrayList<>();

    public NotesMemorySourceImpl() {
        Note note = new Note("1", "Пирожки с клубникой", "Рецепт от бабушки", new Date(), "Мягкие вкусные сдобные пирожочки с яркой и ароматной клубникой! Пробуй. Я очень люблю ягоды и фрукты, ведь это море витаминов и они всегда не только радуют глаз, но и очень вкусные! Выпечка с ягодами, а особенно с клубникой не исключение!!! Наверное, при виде таких пирожков никто не останется равнодушным и захочет попробовать хотя бы один-два пирожочка!");
        notes.add(note);

        note = new Note("2", "Поезд", "Планы на мой отпуск", new Date(2021, 1, 10), "Выезжаем в 15.00 с Курского вокзала. Возьми паспорт.");
        notes.add(note);

        note = new Note("3", "План на выходные", "Дела", new Date(2021, 3, 15), "Уборка, спортзал, помыть собаку, купить продукты.");
        notes.add(note);

        note = new Note("4", "Ещё заметка", "Заметка", new Date(2021, 12, 15), "Уборка, спортзал, помыть собаку, купить продукты.");
        notes.add(note);

        note = new Note("5", "План на выходные", "Дела", new Date(2021, 3, 15), "Уборка, спортзал, помыть собаку, купить продукты.");
        notes.add(note);
    }

    @Override
    public void downloadNotesFromServer(Consumer<NotesSource> onDownloaded) {

    }

    @Override
    public int size() {
        return notes.size();
    }

    public void addNote(Note note){
        notes.add(note);
        note.setId(String.valueOf(notes.size() + 1));
    }

    public void updateNote(int position, Note note){
        notes.set(position, note);
    }

    public void deleteNote(int position){
        notes.remove(position);
    }

    @Override
    public Note getNote(int position) {
        return notes.get(position);
    }

    @Override
    public List<Note> getAllNotes() {
        return notes;
    }
}

