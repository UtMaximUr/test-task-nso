package com.nso.test.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class CompanyNews(
    val category: String?,
    val datetime: Long?,
    val headline: String?,
    val id: Long?,
    val image: String?,
    val related: String?,
    val source: String?,
    val summary: String?,
    val url: String?
)
