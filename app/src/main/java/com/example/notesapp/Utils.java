package com.example.notesapp;

import android.annotation.SuppressLint;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static void showToast(String message) {
        Toast.makeText(NotesApp.getInstance().getApplicationContext(),
                message,
                Toast.LENGTH_SHORT)
                .show();
    }

    @SuppressLint("SimpleDateFormat")
    public static String getFormattedDate(long duration) {
        Date date = new Date(duration);
        return new SimpleDateFormat("dd/MMM/yyyy hh:mm a").format(date);
    }
}
