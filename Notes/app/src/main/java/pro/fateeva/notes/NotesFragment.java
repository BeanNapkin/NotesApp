package pro.fateeva.notes;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.LightingColorFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

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
     * Создаётся фрагмент списка заметок, создаётмя бандл(конверт). В бандл кладём список заметок
     * и этот бандл передаём в аргументы фрагмента.
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

            RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);

            LinearLayoutManager manager = new LinearLayoutManager(getView().getContext());
            recyclerView.setLayoutManager(manager);

            DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),  LinearLayoutManager.VERTICAL);
            itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
            recyclerView.addItemDecoration(itemDecoration);

            recyclerView.setAdapter(new MyAdapter(notes.getNotes()));
        }
        isInitialized = true;
    }
}


