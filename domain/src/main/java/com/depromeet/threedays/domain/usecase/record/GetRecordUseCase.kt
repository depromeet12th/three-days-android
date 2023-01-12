package com.depromeet.threedays.domain.usecase.record

import com.depromeet.threedays.domain.entity.record.Record
import com.depromeet.threedays.domain.repository.RecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecordUseCase @Inject constructor(
    private val recordRepository: RecordRepository
) {
    suspend operator fun invoke(to: String, from: String): Flow<Result<Record>> {
        return recordRepository.getRecord(to = to, from = from)
    }

}
