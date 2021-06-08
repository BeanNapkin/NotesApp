package pro.fateeva.notes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#createFragment} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment {

    public static final String TAG = "notesFragment";
    public RecyclerView recyclerView;
    private MyAdapter myAdapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_NOTES = "param1";
    private boolean isInitialized;

    public NotesFragment() {
        // Required empty public constructor
    }

    /**
     * Создаётся фрагмент списка заметок, создаётмя бандл(конверт). В бандл кладём список заметок
     * и этот бандл передаём в аргументы фрагмента.
     */
    public static NotesFragment createFragment(NotesSource notes) {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_NOTES, new SerializableNotes(notes.getAllNotes()));
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
            final SerializableNotes notes = (SerializableNotes) getArguments().getSerializable(ARG_NOTES);

            recyclerView = getView().findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);

            LinearLayoutManager manager = new LinearLayoutManager(getView().getContext());
            recyclerView.setLayoutManager(manager);

            DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
            itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
            recyclerView.addItemDecoration(itemDecoration);

            myAdapter = new MyAdapter(notes.getNotes(), this);

            recyclerView.setAdapter(myAdapter);
        }
        isInitialized = true;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_card, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        final Note contextNote = myAdapter.getLongClickedNote();
        int id = item.getItemId();

        switch (id) {
            case R.id.action_update:
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("NOTE_TO_UPDATE_CONTEXT", contextNote);
                getContext().startActivity(intent);
                return true;
            case R.id.action_delete:
                intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("NOTE_TO_DELETE", contextNote);
                getContext().startActivity(intent);
                return true;
        }
        return false;
    }

    public void showProgressBar(boolean isShowing){
        ProgressBar progressBar = getView().findViewById(R.id.progressBar);

        if (isShowing){
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void changeNotes(NotesSource notes) {
        myAdapter.setNotes(notes.getAllNotes());
        myAdapter.notifyDataSetChanged();
    }
}


