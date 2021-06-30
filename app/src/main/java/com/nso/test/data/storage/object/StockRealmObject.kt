package com.nso.test.data.storage.`object`

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class StockRealmObject(
    var id: String = "",
    var name: String = "",
    var logo: String = "",
    @PrimaryKey
    var ticker: String = "",
    var price: String = "",
    var delta: String = "",
    var currency: String = "",
    var isFavorite: Boolean = false
) : RealmObject()

