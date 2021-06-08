package pro.fateeva.notes;

import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Note> notes;
    private final Fragment fragment;

    private Note longClickedNote;

    public MyAdapter(List<Note> notes, Fragment fragment) {
        this.notes = notes;
        this.fragment = fragment;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public Note getLongClickedNote() {
        return longClickedNote;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(notes.get(position));
    }

    @Override
    public int getItemCount() {
        if (notes == null) {
            return 0;
        } else {
            return notes.size();
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView header;
        private final TextView description;
        private final TextView date;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.textViewHeader);
            description = itemView.findViewById(R.id.textViewDescription);
            date = itemView.findViewById(R.id.textViewDate);

            registerContextMenu(itemView);
        }

        private void registerContextMenu(@NonNull View itemView) {
            if (fragment != null) {
                fragment.registerForContextMenu(itemView);
            }
        }

        void bind(final Note note) {
            header.setText(note.getHeader());
            description.setText(note.getDescription());
            date.setText(note.getDate().toString());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Class<?> activity;
                    if (v.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        activity = NoteActivity.class;
                    } else {
                        activity = MainActivity.class;
                    }
                    Intent intent = new Intent(v.getContext(), activity);
                    intent.putExtra("NOTE", note);
                    v.getContext().startActivity(intent);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longClickedNote = note;
                    return false;
                }
            });
        }
    }
}
