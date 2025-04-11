package com.example.myproject.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ItemDao {

    @Query("SELECT * FROM item")
    List<Item> getAll();

    @Insert()
    void insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);

    @Query("DELETE FROM item")
    public void deleteAll();

}

