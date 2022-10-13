package com.example.metachar.viewmodel_dataevent.step4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.metachar.viewmodel_dataevent.databinding.ActivityStep4Binding
import kotlinx.coroutines.launch

class Step4Activity : AppCompatActivity() {
    private val viewModel: Step4ViewModel by viewModels()
    private var mBinding: ActivityStep4Binding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityStep4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnState.setOnClickListener {
            viewModel.changeNextState()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateEvent.collect { state ->
                    Toast.makeText(this@Step4Activity, state.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}