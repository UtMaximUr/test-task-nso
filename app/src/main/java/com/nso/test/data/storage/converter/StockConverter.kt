package com.nso.test.data.storage.converter

import com.nso.test.data.remote.model.Stock
import com.nso.test.data.storage.`object`.StockRealmObject
import com.nso.test.domain.entity.StockEntity


fun StockEntity.toStockRealmObject(): StockRealmObject = StockRealmObject(
    id = this.id,
    name = this.name,
    logo = this.logo,
    ticker = this.ticker,
    price = this.price,
    delta = this.delta,
    currency = this.currency,
    isFavorite = this.isFavorite
)

fun List<Stock>.toRealmObjectList(): List<StockRealmObject> {
    val list = ArrayList<StockRealmObject>()
    for (item in this) {
        list.add(
            StockRealmObject(
                id = item.id,
                name = item.name,
                logo = item.logo,
                ticker = item.ticker,
                price = item.price,
                delta = item.delta,
                currency = item.currency,
                isFavorite = item.isFavorite
            )
        )
    }
    return list
}

fun List<StockRealmObject>.toStockEntityList(): List<StockEntity> {
    val list = ArrayList<StockEntity>()
    for (item in this) {
        list.add(
            StockEntity(
                id = item.id,
                name = item.name,
                logo = item.logo,
                ticker = item.ticker,
                price = item.price,
                delta = item.delta,
                currency = item.currency,
                isFavorite = item.isFavorite
            )
        )
    }
    return list
}