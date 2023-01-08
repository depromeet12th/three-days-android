package com.depromeet.threedays.data.datasource.property

interface BuildConfigFieldDataSource {
    fun get(buildConfigFieldKey: BuildConfigFieldKey): String
    fun getOrNull(buildConfigFieldKey: BuildConfigFieldKey): String?
}
