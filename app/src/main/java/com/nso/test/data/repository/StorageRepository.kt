package com.nso.test.data.repository


import com.nso.test.data.remote.model.Stock
import com.nso.test.data.storage.impl.LocalDataSource
import com.nso.test.data.storage.mapper.StockMapper
import com.nso.test.domain.entity.StockEntity

class StorageRepository(
    private val localDataSource: LocalDataSource,
    private val stockMapper: StockMapper
) {

    fun updateStock(stockEntity: StockEntity) {
        localDataSource.updateStock(stockMapper.stockEntityMapper(stockEntity))
    }

    fun saveStockList(list: List<Stock>, onSuccess: (List<StockEntity>) -> Unit) {
        localDataSource.saveStockList(stockMapper.stockToRealmObjectMapper(list)) {
            onSuccess(stockMapper.realmObjectToEntityMapper(it))
        }
    }

    fun getStockList(onSuccess: (List<StockEntity>) -> Unit): List<StockEntity> {
        return stockMapper.realmObjectToEntityMapper(localDataSource.getStockList {
            onSuccess(stockMapper.realmObjectToEntityMapper(it))
        })
    }

    fun getFavoriteStockList(): List<StockEntity> {
        return stockMapper.realmObjectToEntityMapper(localDataSource.getFavoriteStockList())
    }
}