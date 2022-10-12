package com.example.metachar.viewmodel_dataevent.step1

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.metachar.viewmodel_dataevent.databinding.ActivityStep1Binding

class Step1Activity : AppCompatActivity() {
    private val viewModel:Step1ViewModel by viewModels()
    private var mBinding: ActivityStep1Binding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityStep1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnState.setOnClickListener {
            viewModel.changeNextState()
        }

        viewModel.stateEvent.observe(this) { state ->
            Toast.makeText(this, state.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}