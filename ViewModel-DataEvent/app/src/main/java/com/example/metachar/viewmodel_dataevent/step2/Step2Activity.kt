package com.example.metachar.viewmodel_dataevent.step2

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.metachar.viewmodel_dataevent.databinding.ActivityStep2Binding

class Step2Activity : AppCompatActivity() {
    private val viewModel:Step2ViewModel by viewModels()
    private var mBinding: ActivityStep2Binding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityStep2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnState.setOnClickListener {
            viewModel.changeNextState()
        }

        viewModel.stateEvent.observe(this) { it ->
            it.getContentIfNotHandled()?.let { state ->
                Toast.makeText(this, state.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}