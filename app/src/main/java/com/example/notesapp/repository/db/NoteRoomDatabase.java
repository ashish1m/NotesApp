package com.example.notesapp.repository.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.notesapp.NotesApp;
import com.example.notesapp.repository.db.dao.NoteDao;
import com.example.notesapp.repository.db.entity.Note;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteRoomDatabase extends RoomDatabase {

    @VisibleForTesting
    public static final String DATABASE_NAME = "note.db";
    private static volatile NoteRoomDatabase INSTANCE;
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            NotesApp.getInstance()
                    .getAppExecuter()
                    .diskIO()
                    .execute(new Runnable() {
                        @Override
                        public void run() {
                            // Populate the database in the background
                            // If you want to start with more words, just add them.
                            NoteDao dao = INSTANCE.noteDao();
                            dao.deleteAll();

                            Note note = new Note("Sample Note", "Sample description");
                            dao.insert(note);

                        }
                    });
        }
    };

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static NoteRoomDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (NoteRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context.getApplicationContext());
                    INSTANCE.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    private static NoteRoomDatabase buildDatabase(Context applicationContext) {

        return Room.databaseBuilder(applicationContext, NoteRoomDatabase.class, DATABASE_NAME)
                .addCallback(sRoomDatabaseCallback)
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
