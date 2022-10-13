package com.example.metachar.viewmodel_dataevent.step4

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.metachar.viewmodel_dataevent.other.State
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class Step4ViewModel() : ViewModel() {
    private val _stateEvent = MutableStateFlow<State>(State.READY)
    var stateEvent: StateFlow<State> = _stateEvent

    fun changeNextState() =  viewModelScope.launch {
        when (_stateEvent.value) {
            State.READY -> _stateEvent.emit(State.START)
            State.START -> _stateEvent.emit(State.RUNNING)
            State.RUNNING -> _stateEvent.emit(State.EXIT)
            State.EXIT -> _stateEvent.emit(State.READY)
            else -> { }
        }
    }
}