package com.linuxias.todolist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(TodoEntity::class), version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun getTodoDao() : TodoDao

    companion object {
        val dbName = "Todo"
        var appDatabase : TodoDatabase? = null

        fun getTodoDatabase(context : Context) : TodoDatabase? {
            if (appDatabase == null) {
                appDatabase = Room.databaseBuilder(context,
                    TodoDatabase::class.java,
                    dbName).build()
            }

            return appDatabase
        }
    }
}