package com.example.notesapp.ui.note_detail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.notesapp.R;
import com.example.notesapp.repository.db.entity.Note;

public class NoteDetailActivity extends AppCompatActivity {

    public static final String NOTE_ID = "NOTE_ID";
    public int noteId = -1;
    private NoteViewModel mNoteViewModel;
    private Button mSaveBtn;
    private TextView mTitleTv;
    private TextView mDescriptionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        noteId = getIntent().getIntExtra(NOTE_ID, -1);
        mNoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        initView();
    }

    private void initView() {
        mTitleTv = findViewById(R.id.tv_title);
        mDescriptionTv = findViewById(R.id.tv_description);
        mSaveBtn = findViewById(R.id.btn_save);

        if (noteId != -1) {
            mNoteViewModel.getNote(noteId).observe(this, new Observer<Note>() {
                @Override
                public void onChanged(Note note) {
                    updateUI(note);
                }
            });
        }
    }

    private void updateUI(Note note) {
        mTitleTv.setText(note.getTitle());
        mDescriptionTv.setText(note.getNote());
    }
}
