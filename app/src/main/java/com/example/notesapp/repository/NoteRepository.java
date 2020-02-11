package com.example.notesapp.repository;

import androidx.lifecycle.LiveData;

import com.example.notesapp.AppExecuter;
import com.example.notesapp.repository.db.dao.NoteDao;
import com.example.notesapp.repository.db.NoteRoomDatabase;
import com.example.notesapp.repository.db.entity.Note;

import java.util.List;

public class NoteRepository {

    private static NoteRepository sInstance;
    private NoteDao mNoteDao;
    private LiveData<List<Note>> mAllNotes;
    private AppExecuter mAppExecuter;

    private NoteRepository(NoteRoomDatabase db, AppExecuter appExecuter) {
        mAppExecuter = appExecuter;
        mNoteDao = db.noteDao();
        mAllNotes = mNoteDao.getAllNotes();
    }

    public static NoteRepository getInstance(NoteRoomDatabase db, AppExecuter appExecuter) {
        if (sInstance == null) {
            synchronized (NoteRepository.class) {
                if (sInstance == null) {
                    sInstance = new NoteRepository(db, appExecuter);
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }

    public LiveData<Note> getNote(int noteId) {
        return mNoteDao.getNote(noteId);
    }

    public void insert(final Note note) {
        mAppExecuter.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mNoteDao.insert(note);
            }
        });
    }
}
