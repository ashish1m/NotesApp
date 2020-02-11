package com.example.notesapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.notesapp.R;
import com.example.notesapp.ui.note_list.NoteListActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToNoteListActivity();
            }
        }, 3000);
    }

    private void navigateToNoteListActivity() {
        Intent intent = new Intent(SplashActivity.this, NoteListActivity.class);
        startActivity(intent);
        finish();
    }
}
