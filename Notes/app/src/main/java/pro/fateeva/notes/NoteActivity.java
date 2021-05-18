package pro.fateeva.notes;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class NoteActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView(){
        Note note =  (Note) getIntent().getSerializableExtra("NOTE");

        NoteFragment noteFragment = NoteFragment.newInstance(note);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main, noteFragment);
        fragmentTransaction.commit();
    }
}