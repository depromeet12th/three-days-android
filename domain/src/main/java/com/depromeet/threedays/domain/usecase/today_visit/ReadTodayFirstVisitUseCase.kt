package com.depromeet.threedays.domain.usecase.today_visit

import com.depromeet.threedays.domain.repository.TodayFirstVisitRepository
import javax.inject.Inject

class ReadTodayFirstVisitUseCase @Inject constructor(val repository: TodayFirstVisitRepository) {
    suspend fun execute(): String? {
        return repository.readTodayFirstVisit(TODAY_FIRST_VISIT)
    }

    companion object {
        const val TODAY_FIRST_VISIT = "TODAY_FIRST_VISIT"
    }
}
