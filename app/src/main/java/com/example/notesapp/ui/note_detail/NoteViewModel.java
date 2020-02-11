package com.example.notesapp.ui.note_detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notesapp.NotesApp;
import com.example.notesapp.repository.db.entity.Note;

public class NoteViewModel extends AndroidViewModel {

    public NoteViewModel(@NonNull Application application) {
        super(application);
    }

    public void insertNote(Note note) {
        NotesApp.getInstance().getRepository().insert(note);
    }

    public LiveData<Note> getNote(int noteId) {
        return NotesApp.getInstance().getRepository().getNote(noteId);
    }
}
