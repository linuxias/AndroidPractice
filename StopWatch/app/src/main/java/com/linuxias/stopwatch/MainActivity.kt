package com.linuxias.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.linuxias.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var isRunning = false
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener(this)
        binding.btnRefresh.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_start -> {
                if (isRunning)
                    pause()
                else
                    start()
            }
            R.id.btn_refresh -> {
                refresh()
            }
        }
    }

    private fun start() {

    }

    private fun pause() {

    }

    private fun refresh() {

    }
}