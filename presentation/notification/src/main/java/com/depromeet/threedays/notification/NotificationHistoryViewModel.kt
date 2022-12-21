package com.depromeet.threedays.notification

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.entity.Status
import com.depromeet.threedays.domain.usecase.GetNotificationHistoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationHistoryViewModel @Inject constructor(
    private val getNotificationHistoriesUseCase: GetNotificationHistoriesUseCase,
) : BaseViewModel() {

    private val _notifications: MutableStateFlow<List<NotificationHistoryUI>> =
        MutableStateFlow(emptyList())
    val notifications: StateFlow<List<NotificationHistoryUI>>
        get() = _notifications

    fun fetchNotifications() {
        viewModelScope.launch {
            getNotificationHistoriesUseCase().collect { response ->
                when (response.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        _notifications.value = response.data!!.map { NotificationHistoryUI.from(it) }
                    }
                    Status.ERROR -> {}
                    Status.FAIL -> {}
                }
            }
        }
    }
}
