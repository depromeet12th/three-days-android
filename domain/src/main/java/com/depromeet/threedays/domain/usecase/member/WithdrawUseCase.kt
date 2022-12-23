package com.depromeet.threedays.domain.usecase.member

import com.depromeet.threedays.domain.entity.DataState
import com.depromeet.threedays.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WithdrawUseCase @Inject constructor(
    private val memberRepository: MemberRepository,
) {
    operator fun invoke(): Flow<DataState<Unit>> {
        return memberRepository.withdraw()
        // TODO: accessToken, refreshToken 삭제
    }
}
