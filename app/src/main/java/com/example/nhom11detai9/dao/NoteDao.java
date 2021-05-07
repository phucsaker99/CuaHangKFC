package com.example.nhom11detai9.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nhom11detai9.model.Note;

import java.util.List;

@Dao
public interface NoteDao  {
    @Query("SELECT*FROM Note")
    List<Note> getAll();

    @Query("SELECT*FROM Note WHERE name like:name")
    List<Note> getNote(String name);

    @Query("SELECT*FROM Note WHERE id like:id")
    List<Note> getNote(int id);

    @Insert
    void insert(Note... notes);

    @Update
    void update(Note... notes);

    @Delete
    void delete(Note... notes);

    @Query("Delete from Note")
    void deleteAll();
}
