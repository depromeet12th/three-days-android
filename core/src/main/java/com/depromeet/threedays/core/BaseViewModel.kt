package com.depromeet.threedays.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.domain.exception.ThreeDaysException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean>
        get() = _loading

    private val _error = MutableSharedFlow<ThreeDaysException>()
    val error: SharedFlow<ThreeDaysException>
        get() = _error

    protected fun startLoading() {
        _loading.value = true
    }

    protected fun stopLoading() {
        _loading.value = false
    }

    protected fun sendError(throwable: ThreeDaysException) {
        if (throwable.message.equals(ThreeDaysException.UNKNOWN_EXCEPTION)) {
            throwable.message = throwable.defaultMessage
        }

        viewModelScope.launch {
            _error.emit(throwable)
        }
    }
}
