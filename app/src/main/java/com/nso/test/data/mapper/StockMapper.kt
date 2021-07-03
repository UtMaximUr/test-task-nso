package com.nso.test.data.mapper

import com.nso.test.data.remote.model.Stock
import com.nso.test.data.storage.`object`.StockRealmObject
import com.nso.test.data.extension.toRealmObjectList
import com.nso.test.data.extension.toStockEntityList
import com.nso.test.data.extension.toStockRealmObject
import com.nso.test.domain.entity.StockEntity

class StockMapper {

    fun stockEntityMapper(stockEntity: StockEntity): StockRealmObject {
        return stockEntity.toStockRealmObject()
    }

    fun stockToRealmObjectMapper(list: List<Stock>): List<StockRealmObject> {
        return list.toRealmObjectList()
    }

    fun realmObjectToEntityMapper(stockList: List<StockRealmObject>): List<StockEntity> {
        return stockList.toStockEntityList()
    }
}