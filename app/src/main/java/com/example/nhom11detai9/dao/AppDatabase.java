package com.example.nhom11detai9.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.nhom11detai9.model.AccountAdmin;
import com.example.nhom11detai9.model.AccountClient;
import com.example.nhom11detai9.model.Food;
import com.example.nhom11detai9.model.Note;
import com.example.nhom11detai9.model.Order;


@Database(entities = {AccountAdmin.class, AccountClient.class, Food.class, Order.class, Note.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase;

    public static AppDatabase getInstance(Context context){
        if (appDatabase == null){
            appDatabase = Room.databaseBuilder(
                    context,
                    AppDatabase.class,
                    "KFC_management"
            )
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabase;
    }

    public abstract ClientDao getClientDao();

    public abstract AdminDao getAdminDao();

    public abstract FoodDao getFoodDao();

    public abstract OrderDao getOrderDao();

    public abstract NoteDao getNoteDao();
}
