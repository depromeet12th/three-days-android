package com.depromeet.threedays.domain.usecase.today_visit

import com.depromeet.threedays.domain.repository.TodayFirstVisitRepository
import com.depromeet.threedays.domain.usecase.today_visit.ReadTodayFirstVisitUseCase.Companion.TODAY_FIRST_VISIT
import javax.inject.Inject

class WriteTodayFirstVisitUseCase @Inject constructor(val repository: TodayFirstVisitRepository) {
    suspend fun execute(today: String) {
        return repository.writeTodayFirstVisit(key = TODAY_FIRST_VISIT, value = today)
    }
}
