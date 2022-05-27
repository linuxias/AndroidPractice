package com.linuxias.qrcodereader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.linuxias.qrcodereader.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding : ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result = intent.getStringExtra("message") ?: "There is no data."
        updateResultText(result)
    }

    private fun updateResultText(result: String) {
        binding.textContent.text = result
        binding.btnGoBack.setOnClickListener {
            finish()
        }
    }
}