package com.nso.test.data.remote.model

import java.io.Serializable

@kotlinx.serialization.Serializable
data class Stock(
    var id: String = "",
    val name: String = "",
    val logo: String = "",
    val ticker: String = "",
    val price: String = "",
    val delta: String = "",
    val currency: String = "",
    var isFavorite: Boolean = false
): Serializable

