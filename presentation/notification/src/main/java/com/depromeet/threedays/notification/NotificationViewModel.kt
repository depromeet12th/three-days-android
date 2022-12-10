package com.depromeet.threedays.notification

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.Status
import com.depromeet.threedays.domain.usecase.GetNotificationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
) : BaseViewModel() {

    private val _notifications: MutableStateFlow<List<NotificationUI>> =
        MutableStateFlow(emptyList())
    val notifications: StateFlow<List<NotificationUI>>
        get() = _notifications

    fun fetchNotifications() {
        viewModelScope.launch {
            getNotificationsUseCase().collect { response ->
                when (response.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        _notifications.value = response.data!!.map { NotificationUI.from(it) }
                    }
                    Status.ERROR -> {}
                    Status.FAIL -> {}
                }
            }
        }
    }
}
