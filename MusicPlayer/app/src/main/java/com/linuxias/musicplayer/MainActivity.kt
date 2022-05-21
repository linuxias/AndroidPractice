package com.linuxias.musicplayer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Button
import com.linuxias.musicplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    var musicService: MusicPlayerService? = null
    val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            musicService = (service as MusicPlayerService.MusicPlayerBinder).getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            musicService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(getLayoutInflater())
        setContentView(binding.root)

        binding.btnPlay.setOnClickListener(this)
        binding.btnPause.setOnClickListener(this)
        binding.btnStop.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btn_play -> {
                play()
            }
            R.id.btn_pause -> {
                pause()
            }
            R.id.btn_stop -> {
                stop()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (musicService == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(Intent(this, MusicPlayerService::class.java))
            } else {
                startService(Intent(applicationContext, MusicPlayerService::class.java))
            }
        }

        val intent = Intent(this, MusicPlayerService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onPause() {
        super.onPause()

        if (musicService != null) {
            if (musicService!!.isPlaying()) {
                musicService!!.stopSelf()
            }
            unbindService(serviceConnection)
            musicService = null
        }
    }

    private fun stop() {
        musicService?.stop()
    }

    private fun pause() {
        musicService?.pause()
    }

    private fun play() {
        musicService?.play()
    }
}