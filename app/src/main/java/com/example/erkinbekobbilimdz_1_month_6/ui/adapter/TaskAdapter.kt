package com.example.erkinbekobbilimdz_1_month_6.ui.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.erkinbekobbilimdz_1_month_6.databinding.ItemTaskBinding
import com.example.erkinbekobbilimdz_1_month_6.model.Task

class TaskAdapter(private var onLongClick:(Task) -> Unit): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private val taskList: ArrayList<Task> = arrayListOf()

    fun addTask(task: Task){
        taskList.add(0,task)
        notifyItemChanged(0)
    }

    fun addListTasks(tasks: List<Task>) {
        taskList.clear()
        taskList.addAll(tasks)
        notifyDataSetChanged()
    }
    fun getPosition(task: Task): Int {
        return taskList.indexOf(task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding): ViewHolder(binding.root){
        fun bind(task: Task) {
            binding.apply {
                checkBox.isChecked = task.isComplete
                tvTitle.text = task.title

                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    taskCheck(adapterPosition, isChecked)
                }
                binding.root.setOnLongClickListener {
                    onLongClick(task)
                    true
                }
             }
        }
        private fun taskCheck(position: Int, isChecked: Boolean){
            taskList[position].toggleDone()
            notifyItemChanged(position, isChecked)
        }
    }
}