package com.linuxias.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.linuxias.stopwatch.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var isRunning = false
    private var timer: Timer? = null
    private var time = 0
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
        binding.btnStart.text = "Pause"
        binding.btnStart.setBackgroundColor(getColor(R.color.red))
        isRunning = true

        timer = timer(period = 10) {
            time++
            val msec = time % 100
            val sec = (time % 6000) / 100
            val min = time / 6000
            runOnUiThread {
                if (isRunning) {
                    binding.tvMillisecond.text = if (msec < 10) ".0$msec" else ".${msec}"
                    binding.tvSecond.text = if (sec < 10) ":0$sec" else ".${sec}"
                    binding.tvMinute.text = "${min}"
                }
            }
        };
    }

    private fun pause() {
        binding.btnStart.text = "Start"
        binding.btnStart.setBackgroundColor(getColor(R.color.blue))
        isRunning = false

        timer?.cancel()
    }

    private fun refresh() {
        timer?.cancel()

        binding.btnStart.text = "Start"
        binding.btnStart.setBackgroundColor(getColor(R.color.blue))
        isRunning = false

        time = 0
        binding.tvMillisecond.text = ".00"
        binding.tvSecond.text = ":00"
        binding.tvMinute.text = "00"
    }
}