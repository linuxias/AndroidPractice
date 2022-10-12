package com.example.metachar.viewmodel_dataevent.step3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.metachar.viewmodel_dataevent.databinding.ActivityStep2Binding
import com.example.metachar.viewmodel_dataevent.databinding.ActivityStep3Binding
import com.example.metachar.viewmodel_dataevent.step2.Step2ViewModel
import kotlinx.coroutines.launch

class Step3Activity : AppCompatActivity() {
    private val viewModel: Step3ViewModel by viewModels()
    private var mBinding: ActivityStep3Binding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityStep3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnState.setOnClickListener {
            viewModel.changeNextState()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateEvent.collect { state ->
                    Toast.makeText(this@Step3Activity, state.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}