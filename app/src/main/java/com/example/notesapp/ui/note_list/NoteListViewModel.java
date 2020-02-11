package com.example.notesapp.ui.note_list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notesapp.NotesApp;
import com.example.notesapp.repository.db.entity.Note;

import java.util.List;

public class NoteListViewModel extends AndroidViewModel {

    private LiveData<List<Note>> mAllNotes;

    public NoteListViewModel(@NonNull Application application) {
        super(application);
        mAllNotes = NotesApp.getInstance().getRepository().getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }
}
