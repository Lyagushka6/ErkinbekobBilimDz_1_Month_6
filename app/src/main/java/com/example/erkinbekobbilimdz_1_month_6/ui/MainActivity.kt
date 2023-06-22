package com.example.erkinbekobbilimdz_1_month_6.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.erkinbekobbilimdz_1_month_6.databinding.ActivityMainBinding
import com.example.erkinbekobbilimdz_1_month_6.model.Task
import com.example.erkinbekobbilimdz_1_month_6.ui.adapter.TaskAdapter
import com.example.erkinbekobbilimdz_1_month_6.viewModel.TaskViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter : TaskAdapter
    private var viewModel = TaskViewModel()
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>


    companion object{
        private const val REQUEST_CODE_ADD_TASK = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        adapter = TaskAdapter(this::onLongClick)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        viewModel.taskList.observe(this) {taskList ->
            if (taskList != null){
                adapter.addListTasks(taskList)
            }
            binding.apply {
                recyclerView.post {
                    adapter.notifyDataSetChanged()
                }
                btnAdd.setOnClickListener {
                    val intent = Intent(this@MainActivity, SecondActivity::class.java)
                    startActivityForResult(intent, REQUEST_CODE_ADD_TASK)
                }
            }
        }
    }
    private fun onLongClick(task: Task) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage("Не удаляйте!")
        alertDialog.setNegativeButton("Не буду") { d, _ ->
            d?.cancel()
        }
            alertDialog.setPositiveButton("同意將您的數據傳輸給中國政府") { _, _ ->
                viewModel.removeTask(0)
            }
            alertDialog.create().show()
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == REQUEST_CODE_ADD_TASK && resultCode == RESULT_OK) {
                val taskTitle = data?.getStringExtra("KEY_TASK")
                if (!taskTitle.isNullOrEmpty()) {
                    viewModel.addTask(taskTitle)
                }
            }
        }
    }