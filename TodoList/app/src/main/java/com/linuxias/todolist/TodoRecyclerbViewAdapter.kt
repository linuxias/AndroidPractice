package com.linuxias.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.linuxias.todolist.databinding.ItemTodoBinding
import com.linuxias.todolist.db.TodoEntity

class TodoRecyclerbViewAdapter(private val todoList: ArrayList<TodoEntity>)
    :RecyclerView.Adapter<TodoRecyclerbViewAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(binding : ItemTodoBinding) :
    RecyclerView.ViewHolder(binding.root) {
        val tv_importance = binding.tvImportance
        val tv_title = binding.tvTitle
        val root = binding.root
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodoRecyclerbViewAdapter.TodoViewHolder {
        val binding: ItemTodoBinding =
            ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoRecyclerbViewAdapter.TodoViewHolder, position: Int) {
        val todoData = todoList[position]
        when (todoData.importance) {
            1 -> {
                holder.tv_importance.setBackgroundResource(R.color.red)
            }
            2 -> {
                holder.tv_importance.setBackgroundResource(R.color.yellow)
            }
            3 -> {
                holder.tv_importance.setBackgroundResource(R.color.green)
            }
        }

        holder.tv_importance.text = todoData.importance.toString()
        holder.tv_title.text = todoData.title
    }

    override fun getItemCount(): Int {
        return todoList.size
    }
}