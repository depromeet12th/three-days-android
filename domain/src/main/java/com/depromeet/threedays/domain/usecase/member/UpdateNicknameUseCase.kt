package com.depromeet.threedays.domain.usecase.member

import com.depromeet.threedays.domain.entity.member.Member
import com.depromeet.threedays.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateNicknameUseCase @Inject constructor(
    private val memberRepository: MemberRepository,
) {
    operator fun invoke(nickname: String): Flow<Result<Member>> {
        return memberRepository.updateNickname(nickname = nickname)
    }
}
