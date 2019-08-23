package com.loicngou.todoapp.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.loicngou.todoapp.entities.Todo;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    void insert(Todo todo);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);

    @Query("SELECT * FROM todos_table")
    LiveData<List<Todo>> getAllTodo();

    @Query("DELETE FROM todos_table")
    void deleteAllTodo();

    @Query("SELECT * FROM todos_table WHERE done=:done")
    LiveData<List<Todo>> getTodoByState(Boolean done);

}
