package com.linuxias.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.linuxias.todolist.databinding.ActivityAddTodoBinding
import com.linuxias.todolist.db.TodoDao
import com.linuxias.todolist.db.TodoDatabase
import com.linuxias.todolist.db.TodoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class AddTodoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTodoBinding
    private lateinit var db : TodoDatabase
    private lateinit var todoDao: TodoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TodoDatabase.getTodoDatabase(this)!!
        todoDao = db.getTodoDao()

        binding.btnComplete.setOnClickListener {
            insertTodo()
        }
    }

    private fun insertTodo() {
        val todoTitle = binding.edtTitle.text.toString()
        var todoImportance = binding.radioGroup.checkedRadioButtonId

        when (todoImportance) {
            R.id.btn_high -> {
                todoImportance = 1
            }
            R.id.btn_middle -> {
                todoImportance = 2
            }
            R.id.btn_low -> {
                todoImportance = 3
            }
        }

        if (todoImportance == -1 || todoTitle.isBlank()) {
            Toast.makeText(this,
                "Please fill title and importance",
                Toast.LENGTH_LONG).show()
        } else {
            Thread {
                todoDao.insertTodo(TodoEntity(null, todoTitle, todoImportance))
                runOnUiThread {
                    Toast.makeText(this, "Compelete to add todo",
                    Toast.LENGTH_SHORT).show()
                    finish()
                }
            }.start()
        }
    }
}