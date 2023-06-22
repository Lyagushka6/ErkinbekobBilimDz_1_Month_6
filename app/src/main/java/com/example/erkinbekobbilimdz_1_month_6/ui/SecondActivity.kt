package com.example.erkinbekobbilimdz_1_month_6.ui
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.erkinbekobbilimdz_1_month_6.databinding.ActivitySecondBinding


class SecondActivity : AppCompatActivity() {

    lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
    }

    private fun initListeners() {
        binding.btnAddTask.setOnClickListener {
            val title = binding.etTask.text.toString()
            if (title.isNotEmpty()) {
                val intent = Intent()
                intent.putExtra("KEY_TASK", title)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                binding.etTask.error = "Заполняйте >:("
            }
        }
    }
}