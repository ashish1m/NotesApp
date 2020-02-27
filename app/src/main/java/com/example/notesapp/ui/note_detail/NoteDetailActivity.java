package com.example.notesapp.ui.note_detail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.notesapp.R;
import com.example.notesapp.repository.db.entity.Note;
import com.example.notesapp.util.Utils;

public class NoteDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String NOTE_ID = "NOTE_ID";
    private int noteId = -1;
    private Note mNote;
    private NoteViewModel mNoteViewModel;
    private Button mSaveBtn;
    private EditText mTitleEt;
    private EditText mDescriptionEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        noteId = getIntent().getIntExtra(NOTE_ID, -1);
        mNoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        initView();
    }

    private void initView() {
        mTitleEt = findViewById(R.id.et_title);
        mDescriptionEt = findViewById(R.id.et_description);
        mSaveBtn = findViewById(R.id.btn_save);

        mSaveBtn.setOnClickListener(this);

        if (noteId != -1) {
            mNoteViewModel.getNote(noteId).observe(this, new Observer<Note>() {
                @Override
                public void onChanged(Note note) {
                    mNote = note;
                    updateUI(note);
                }
            });
        }
    }

    private void updateUI(Note note) {
        mTitleEt.setText(note.getTitle());
        mDescriptionEt.setText(note.getNote());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                saveNote();
                break;

            default:
                break;
        }
    }

    private void saveNote() {
        if (mNote != null) {
            mNote.setTitle(mTitleEt.getText().toString());
            mNote.setNote(mDescriptionEt.getText().toString());
            mNote.setTime(System.currentTimeMillis());
        } else {
            mNote = new Note(mTitleEt.getText().toString(),
                    mDescriptionEt.getText().toString());
        }

        if (isNoteValid(mNote)) {
            mNoteViewModel.insertNote(mNote);
            Utils.showToast("Note saved successfully.");
            finish();
        } else {
            Utils.showToast("Title or Description is empty.");
        }
    }

    private boolean isNoteValid(Note note) {
        boolean isValid = false;
        if (!note.getTitle().isEmpty()
                && !note.getNote().isEmpty()) {
            isValid = true;
        }

        return isValid;
    }
}
