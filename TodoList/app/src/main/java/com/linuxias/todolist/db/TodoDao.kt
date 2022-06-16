package com.linuxias.todolist.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM TodoEntity")
    fun getAll() : Flow<List<TodoEntity>>

    @Insert
    fun insertTodo(todo: TodoEntity)

    @Delete
    fun deleteTodo(todo: TodoEntity)
}