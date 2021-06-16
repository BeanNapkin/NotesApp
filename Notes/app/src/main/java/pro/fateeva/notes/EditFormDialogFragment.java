package pro.fateeva.notes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class EditFormDialogFragment extends DialogFragment {

    private static final String ARG_NOTE = "note_item";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_form_dialog_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Note note = (Note) getArguments().getSerializable(ARG_NOTE);
        initView(note);
    }

    private void initView(Note note) {
        EditFormFragment editFormFragment = EditFormFragment.createFragment(note);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.dialogEditFragment, editFormFragment);
        fragmentTransaction.commit();

        getView().findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFormFragment.clickOnSaveButton();
            }
        });
    }

    public static EditFormDialogFragment createDialogFragment(Note note) {
        EditFormDialogFragment dialogFragment = new EditFormDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_NOTE, note);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }
}