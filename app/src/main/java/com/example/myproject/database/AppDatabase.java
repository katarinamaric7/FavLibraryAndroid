package com.example.myproject.database;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Item.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract ItemDao itemDao();

    public static AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            synchronized (AppDatabase.class) {
                appDatabase = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class,
                        "Items.db").build();
            }
        }
        return appDatabase;
    }
}

