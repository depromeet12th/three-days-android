package com.depromeet.threedays.domain.entity.mate

data class Mate(
    val mateId: Int,
    val level: Int,
    val characterType: String,
    val createDate: String? = null
)
