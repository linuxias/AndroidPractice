package com.linuxias.todolist

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.linuxias.todolist.databinding.ActivityMainBinding
import com.linuxias.todolist.db.TodoDao
import com.linuxias.todolist.db.TodoDatabase
import com.linuxias.todolist.db.TodoEntity

class MainActivity : AppCompatActivity(), OnItemLongClinkListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db : TodoDatabase
    private lateinit var todoDao : TodoDao
    private lateinit var todoList : ArrayList<TodoEntity>
    private lateinit var adapter: TodoRecyclerbViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this, AddTodoActivity::class.java))
        }

        db = TodoDatabase.getTodoDatabase(this)!!
        todoDao = db.getTodoDao()

        getAllTodoList()
    }

    private fun getAllTodoList() {
        Thread {
            todoList = todoDao.getAll()
            setRecycleView()
        }.start()
    }

    private fun setRecycleView() {
        runOnUiThread {
            adapter = TodoRecyclerbViewAdapter(todoList, this)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
        }
    }

    override fun onRestart() {
        super.onRestart()
        getAllTodoList()
    }

    override fun onLongClick(position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Delete todo item")
        builder.setMessage("Would you want to delete item?")
        builder.setNegativeButton("No", null)
        builder.setPositiveButton("Yes",
            object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    deleteTodo(position)
                }
            }
        )
        builder.show()
    }

    private fun deleteTodo(position: Int) {
        Thread {
            todoDao.deleteTodo(todoList[position])
            todoList.removeAt(position)
            runOnUiThread {
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }
}