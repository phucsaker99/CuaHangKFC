package com.example.nhom11detai9.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nhom11detai9.model.AccountAdmin;

import java.util.List;


@Dao
public interface AdminDao {
    @Query("SELECT*FROM AccountAdmin")
    List<AccountAdmin> getAll();

    @Query("SELECT*FROM AccountAdmin WHERE account like:account")
    AccountAdmin getAdmin(String account);

    @Query("SELECT*FROM AccountAdmin WHERE id like:id")
    AccountAdmin getAdmin(int id);

    @Insert
    void insert(AccountAdmin... admins);

    @Update
    void update(AccountAdmin... admins);

    @Delete
    void delete(AccountAdmin... admins);

    @Query("SELECT *from AccountAdmin Where account =:account and password =:password")
    AccountAdmin checkAccount(String account, String password);
}
