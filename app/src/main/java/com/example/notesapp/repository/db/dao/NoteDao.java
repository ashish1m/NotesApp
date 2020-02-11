package com.example.notesapp.repository.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.notesapp.repository.db.entity.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Query("SELECT * from note_table WHERE id = :noteId")
    LiveData<Note> getNote(int noteId);

    @Query("DELETE FROM note_table")
    void deleteAll();

    @Query("SELECT * from note_table ORDER BY time DESC")
    LiveData<List<Note>> getAllNotes();
}
