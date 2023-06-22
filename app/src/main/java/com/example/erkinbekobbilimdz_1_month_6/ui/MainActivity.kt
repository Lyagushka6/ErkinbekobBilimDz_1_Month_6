package com.example.erkinbekobbilimdz_1_month_6.ui


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.erkinbekobbilimdz_1_month_6.model.Task
import com.example.erkinbekobbilimdz_1_month_6.databinding.ActivityMainBinding
import com.example.erkinbekobbilimdz_1_month_6.ui.adapter.TaskAdapter
import com.example.erkinbekobbilimdz_1_month_6.viewModel.TaskViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TaskAdapter
    private lateinit var viewModel: TaskViewModel
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        adapter = TaskAdapter(::onLongClick)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        viewModel.taskList.observe(this) { taskList ->
            if (taskList != null) {
                adapter.addListTasks(taskList)
            }
            binding.btnAdd.setOnClickListener {
                openAddTaskActivity()
            }
        }

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val data: Intent? = result.data
                    val taskTitle = data?.getStringExtra(SecondActivity.KEY_TASK)
                    if (!taskTitle.isNullOrEmpty()) {
                        viewModel.addTask(taskTitle)
                    }
                }
            }
    }

    private fun openAddTaskActivity() {
        val intent = Intent(this@MainActivity, SecondActivity::class.java)
        resultLauncher.launch(intent)
    }

    private fun onLongClick(task: Task) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage("Не удаляйте!")
        alertDialog.setNegativeButton("Не буду") { d, _ ->
            d?.cancel()
        }
        alertDialog.setPositiveButton("同意將您的數據傳輸給中國政府") { _, _ ->
            val position = adapter.getPosition(task)
            viewModel.removeTask(position)
            adapter.notifyItemRemoved(position)
        }
        alertDialog.create().show()
    }
}
