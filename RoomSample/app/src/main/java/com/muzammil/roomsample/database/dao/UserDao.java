package com.muzammil.roomsample.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.muzammil.roomsample.model.User;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Muzammil on 01-Apr-18.
 */

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();
    
    @Query("SELECT COUNT(*) FROM user")
    int countUsers();
    
    @Insert(onConflict = REPLACE)
    void insertAll(User... users);
    
    @Delete
    void delete(User user);
}
