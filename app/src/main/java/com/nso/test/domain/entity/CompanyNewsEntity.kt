package com.nso.test.domain.entity

import java.io.Serializable

@kotlinx.serialization.Serializable
data class CompanyNewsEntity(
    val category: String?,
    var datetime: Long?,
    var headline: String?,
    var id: Long?,
    var image: String?,
    val url: String?
) : Serializable
