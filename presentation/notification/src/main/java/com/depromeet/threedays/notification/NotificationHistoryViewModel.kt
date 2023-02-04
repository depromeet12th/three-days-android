package com.depromeet.threedays.notification

import androidx.lifecycle.viewModelScope
import com.depromeet.threedays.core.BaseViewModel
import com.depromeet.threedays.domain.exception.ThreeDaysException
import com.depromeet.threedays.domain.usecase.notification.GetNotificationHistoriesUseCase
import com.depromeet.threedays.domain.usecase.notification.MarkAsReadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationHistoryViewModel @Inject constructor(
    private val getNotificationHistoriesUseCase: GetNotificationHistoriesUseCase,
    private val markAsReadUseCase: MarkAsReadUseCase,
) : BaseViewModel() {

    private val _notifications: MutableStateFlow<List<NotificationHistoryUI>> =
        MutableStateFlow(emptyList())
    val notifications: StateFlow<List<NotificationHistoryUI>>
        get() = _notifications

    /**
     * 알림 페이지 > 알림 이력 목록 조회
     */
    fun fetchNotifications() {
        viewModelScope.launch {
            getNotificationHistoriesUseCase().collect { response ->
                response.onSuccess { notifications ->
                    _notifications.value = notifications.map { NotificationHistoryUI.from(it) }
                }.onFailure { throwable ->
                    throwable as ThreeDaysException

                    sendErrorMessage(throwable.message)
                }
            }
        }
    }

    /**
     * 알림 페이지 > 알림 이력 읽음 처리
     */
    fun markAsRead(notificationHistoryId: Long) {
        viewModelScope.launch {
            markAsReadUseCase(notificationHistoryId).collect { response ->
                response.onSuccess { notificationHistory ->
                    _notifications.value = _notifications.value.map {
                        if (notificationHistory.notificationHistoryId != it.id) {
                            it
                        } else {
                            it.copyOf(
                                status = notificationHistory.status,
                            )
                        }
                    }
                }.onFailure { throwable ->
                    throwable as ThreeDaysException

                    sendErrorMessage(throwable.message)
                }
            }
        }
    }
}
