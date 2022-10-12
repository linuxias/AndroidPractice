package com.example.metachar.viewmodel_dataevent.step3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.metachar.viewmodel_dataevent.other.State
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class Step3ViewModel() : ViewModel() {
    private var _currentState = State.EXIT
    private val _stateEvent = MutableSharedFlow<State>()
    var stateEvent: SharedFlow<State> = _stateEvent.asSharedFlow()

    fun changeNextState() =  viewModelScope.launch {
        when (_currentState) {
            State.READY -> _currentState = State.START
            State.START -> _currentState = State.RUNNING
            State.RUNNING -> _currentState = State.EXIT
            State.EXIT -> _currentState = State.READY
            else -> { }
        }

        _stateEvent.emit(_currentState)
    }
}