package com.example.nhom11detai9.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nhom11detai9.model.Food;

import java.util.List;

@Dao
public interface FoodDao {
    @Query("SELECT*FROM Food")
    List<Food> getAll();

    @Query("SELECT*FROM Food WHERE name like:name")
    List<Food> getFood(String name);

    @Query("SELECT*FROM Food WHERE id like:id")
    List<Food> getFood(int id);

    @Insert
    void insert(Food... foods);

    @Update
    void update(Food... foods);

    @Delete
    void delete(Food... foods);
}
