package com.example.metachar.viewmodel_dataevent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.metachar.viewmodel_dataevent.databinding.ActivityMainBinding
import com.example.metachar.viewmodel_dataevent.databinding.ActivityStep1Binding
import com.example.metachar.viewmodel_dataevent.step1.Step1Activity

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStep1.setOnClickListener {
            startActivity(Step1Activity::class.java)
        }
    }

    private fun <T> startActivity(clazz: Class<T>) {
        val intent = Intent(this, clazz)
        startActivity(intent)
    }
}