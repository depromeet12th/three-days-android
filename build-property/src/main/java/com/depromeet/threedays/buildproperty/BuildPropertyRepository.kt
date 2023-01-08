package com.depromeet.threedays.buildproperty

interface BuildPropertyRepository {
    fun get(buildProperty: BuildProperty): String
    fun getOrNull(buildProperty: BuildProperty): String?
}
