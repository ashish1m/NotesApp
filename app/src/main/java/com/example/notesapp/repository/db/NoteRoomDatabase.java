package com.example.notesapp.repository.db;

import android.content.Context;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notesapp.AppExecuter;
import com.example.notesapp.repository.db.entity.Note;
import com.example.notesapp.repository.db.dao.NoteDao;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteRoomDatabase extends RoomDatabase {

    @VisibleForTesting
    public static final String DATABASE_NAME = "note.db";
    private static volatile NoteRoomDatabase INSTANCE;
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static NoteRoomDatabase getDatabase(Context context, AppExecuter executor) {
        if (INSTANCE == null) {
            synchronized (NoteRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context.getApplicationContext(), executor);
                    INSTANCE.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    private static NoteRoomDatabase buildDatabase(Context applicationContext, AppExecuter executor) {

        return Room.databaseBuilder(applicationContext, NoteRoomDatabase.class, DATABASE_NAME)
                .addCallback(null)
                .build();
    }


    public abstract NoteDao noteDao();

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
