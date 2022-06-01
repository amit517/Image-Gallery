package com.assessment.base.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assessment.base.event.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    val dataLoading = ObservableBoolean(false)

    private val _showMessage = MutableSharedFlow<String>()
    val showMessage = _showMessage.asSharedFlow()

    val navigateToDestination: LiveData<Event<Pair<String, Any>>>
        get() = _navigateToDestination
    protected val _navigateToDestination = MutableLiveData<Event<Pair<String, Any>>>()

    fun emitMessage(message: String?) {
        viewModelScope.launch(Dispatchers.Default) {
            message?.let {
                _showMessage.emit(it)
            }
        }
    }
}
