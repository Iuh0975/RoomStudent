package com.example.myapplication;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Student.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public static UserDatabase db;
    private static String DB_Name = "database.sqlite";
    public synchronized static UserDatabase getInstance(Context context)
    {
        if(db == null)
        {
            db = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, DB_Name)
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return db;
    }
    public abstract IMainDao mainDao();
}