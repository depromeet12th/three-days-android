package com.depromeet.threedays.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean>
        get() = _loading

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String>
        get() = _error

    protected fun startLoading() {
        _loading.value = true
    }

    protected fun stopLoading() {
        _loading.value = false
    }

    protected fun sendErrorMessage(throwable: Throwable) {
        sendErrorMessage(throwable.message.toString())
    }

    protected fun sendErrorMessage(message: String?) {
        viewModelScope.launch {
            _error.emit(message.orEmpty())
        }
    }
}
