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

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var btn_play: Button
    lateinit var btn_pause: Button
    lateinit var btn_stop: Button

    var service: MusicPlayerService? = null
    val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            service = (service as MusicPlayerService.MusicPlayerBinder).getService()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            service = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_play = findViewById(R.id.btn_play)
        btn_pause = findViewById(R.id.btn_pause)
        btn_stop = findViewById(R.id.btn_stop)

        btn_play.setOnClickListener(this)
        btn_pause.setOnClickListener(this)
        btn_stop.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btn_play -> play()
            R.id.btn_pause -> pause()
            R.id.btn_stop -> stop()
        }
    }

    override fun onResume() {
        super.onResume()

        if (service == null) {
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

        if (service != null) {
            if (service!!.isPlaying()) {
                service!!.stopSelf()
            }
            unbindService(serviceConnection)
            service = null
        }
    }

    private fun stop() {
        service?.stop()
    }

    private fun pause() {
        service?.pause()
    }

    private fun play() {
        service?.play()
    }
}