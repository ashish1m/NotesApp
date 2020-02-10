package com.example.notesapp;

import android.app.Application;

import com.example.notesapp.repository.NoteRepository;
import com.example.notesapp.repository.db.NoteRoomDatabase;

public class NotesApp extends Application {

    private static NotesApp mInstance;
    private AppExecuter mAppExecuter;

    public static NotesApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        mAppExecuter = new AppExecuter();
    }

    public NoteRoomDatabase getDatabase() {
        return NoteRoomDatabase.getDatabase(this, mAppExecuter);
    }

    public NoteRepository getRepository() {
        return NoteRepository.getInstance(getDatabase(), mAppExecuter);
    }

}
