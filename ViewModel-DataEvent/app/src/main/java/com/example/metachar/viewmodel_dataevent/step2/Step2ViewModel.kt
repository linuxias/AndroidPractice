package com.example.metachar.viewmodel_dataevent.step2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.metachar.viewmodel_dataevent.other.State
import kotlinx.coroutines.launch

class Step2ViewModel : ViewModel() {
    private val _stateEvent = MutableLiveData<Event<State>>(Event(State.READY))
    var stateEvent: LiveData<Event<State>> = _stateEvent

    fun changeNextState() = viewModelScope.launch {
        when (_stateEvent.value?.peekContent()) {
            State.READY -> _stateEvent.value = Event(State.START)
            State.START -> _stateEvent.value = Event(State.RUNNING)
            State.RUNNING -> _stateEvent.value = Event(State.EXIT)
            State.EXIT -> _stateEvent.value = Event(State.READY)
            else -> { }
        }
    }
}