package com.example.notesapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.notesapp.R;

public class NoteListActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        findViewById(R.id.fab_addNote).setOnClickListener(this);
    }

    private void navigateToNoteDetailActivity() {
        Intent intent = new Intent(NoteListActivity.this, NoteDetailActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_addNote:
                navigateToNoteDetailActivity();
                break;
        }
    }
}
