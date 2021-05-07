package com.example.nhom11detai9.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nhom11detai9.model.AccountClient;

import java.util.List;


@Dao
public interface ClientDao {
    @Query("SELECT*FROM AccountClient")
    List<AccountClient> getAll();

    @Query("SELECT*FROM AccountClient WHERE id like:id")
    AccountClient getClient(int id);

    @Query("SELECT*FROM AccountClient WHERE account like:account")
    AccountClient getClient(String account);

    @Insert
    void insert(AccountClient... clients);

    @Update
    void update(AccountClient... clients);

    @Delete
    void delete(AccountClient... clients);

    @Query("SELECT *from AccountClient Where account =:account and password =:password")
    AccountClient checkAccount(String account, String password);

}
