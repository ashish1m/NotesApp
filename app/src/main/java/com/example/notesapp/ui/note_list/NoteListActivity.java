package com.example.notesapp.ui.note_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.R;
import com.example.notesapp.repository.db.entity.Note;
import com.example.notesapp.ui.note_detail.NoteDetailActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NoteListActivity extends AppCompatActivity implements View.OnClickListener, NoteListAdapter.OnItemClickListener {

    private RecyclerView mNoteListRv;
    private FloatingActionButton mFabAddNote;
    private NoteListAdapter mNoteListAdapter;
    private NoteListViewModel mNoteListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        mNoteListViewModel = new ViewModelProvider(this).get(NoteListViewModel.class);
        mNoteListAdapter = new NoteListAdapter(this);
        mNoteListAdapter.addOnItemClickListener(this);
        initView();
    }

    private void initView() {
        mFabAddNote = findViewById(R.id.fab_addNote);
        mNoteListRv = findViewById(R.id.rv_noteList);
        mNoteListRv.setHasFixedSize(true);
        mNoteListRv.setLayoutManager(new LinearLayoutManager(this));
        mNoteListRv.setAdapter(mNoteListAdapter);

        mFabAddNote.setOnClickListener(this);
        mNoteListViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                mNoteListAdapter.updateList(notes);
            }
        });
    }

    private void navigateToNoteDetailActivity(int noteId) {
        Intent intent = new Intent(NoteListActivity.this, NoteDetailActivity.class);
        intent.putExtra(NoteDetailActivity.NOTE_ID, noteId);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_addNote:
                navigateToNoteDetailActivity(-1);
                break;
        }
    }

    @Override
    public void onItemClick(int noteId) {
        navigateToNoteDetailActivity(noteId);
    }
}
