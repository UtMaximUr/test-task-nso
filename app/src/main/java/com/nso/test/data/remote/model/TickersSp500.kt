package com.nso.test.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class TickersSp500(
    val constituents: List<String>,
    val symbol: String
)
