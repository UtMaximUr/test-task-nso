package com.nso.test.domain.entity

import java.io.Serializable

@kotlinx.serialization.Serializable
data class StockEntity(
    var id: String = "",
    val name: String = "",
    val logo: String = "",
    val ticker: String = "",
    val price: String = "",
    val delta: String = "",
    val currency: String = "",
    var isFavorite: Boolean = false
) : Serializable

