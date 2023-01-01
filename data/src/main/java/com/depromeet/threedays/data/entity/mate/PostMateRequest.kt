package com.depromeet.threedays.data.entity.mate

import com.depromeet.threedays.domain.entity.mate.CreateMate

data class PostMateRequest(
    val title: String,
    val characterType: String,
)

fun CreateMate.toPostMateRequest(): PostMateRequest {
    return PostMateRequest(
        title = this.title,
        characterType = this.characterType,
    )
}
