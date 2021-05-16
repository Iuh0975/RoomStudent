package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface IMainDao {
    @Insert(onConflict = REPLACE)
    void insert(Student us);

    @Delete
    void delete(Student user);

    @Delete
    void deleteAll(List<Student> lstUs);

    @Query("SELECT * FROM Student")
    List<Student> getAllUser();
}
