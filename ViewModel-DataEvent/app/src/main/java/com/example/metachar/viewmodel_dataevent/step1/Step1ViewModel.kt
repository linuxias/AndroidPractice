package com.example.metachar.viewmodel_dataevent.step1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.metachar.viewmodel_dataevent.other.State
import kotlinx.coroutines.launch

class Step1ViewModel: ViewModel() {
    private val _stateEvent = MutableLiveData(State.READY)
    var stateEvent: LiveData<State> = _stateEvent

    fun changeNextState() = viewModelScope.launch {
        when (_stateEvent.value) {
            State.READY -> _stateEvent.value = State.START
            State.START -> _stateEvent.value = State.RUNNING
            State.RUNNING -> _stateEvent.value = State.EXIT
            State.EXIT -> _stateEvent.value = State.READY
            else -> {}
        }
    }
}