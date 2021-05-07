package com.example.nhom11detai9.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nhom11detai9.model.Food;
import com.example.nhom11detai9.model.Order;

import java.util.List;

@Dao
public interface OrderDao {
    @Query("SELECT*FROM `Order`")
    List<Order> getAll();

    @Query("SELECT*FROM `Order` WHERE name like:name")
    List<Order> getOrder(String name);

    @Query("SELECT*FROM `Order` WHERE id like:id")
    List<Order> getOrder(int id);

    @Insert
    void insert(Order... orders);

    @Update
    void update(Order... orders);

    @Delete
    void delete(Order... orders);

    @Query("delete from `Order`")
    void deleteAll();
}
