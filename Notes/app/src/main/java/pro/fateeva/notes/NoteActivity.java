package pro.fateeva.notes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class NoteActivity extends AppCompatActivity {

    private TextView mTextView;
    private Note note = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        note =  (Note) getIntent().getSerializableExtra("NOTE");

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
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

    private void initView(){
        NoteFragment noteFragment = NoteFragment.createFragment(note);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main, noteFragment);
        fragmentTransaction.commit();
    }
}