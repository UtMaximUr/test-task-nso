package com.nso.test.data.storage.impl


import com.nso.test.data.remote.model.Stock
import com.nso.test.data.storage.`object`.StockRealmObject
import io.realm.Realm

class LocalDataSource(private val realm: Realm) {

    fun updateStock(stock: StockRealmObject) {

        realm.executeTransactionAsync({ transitionRealm ->
            val result = transitionRealm.where(StockRealmObject::class.java)
                .equalTo("ticker", stock.ticker)
                .findFirst()
            checkNotNull(result).apply {
                result.isFavorite = !result.isFavorite
                transitionRealm.insertOrUpdate(result)
            }
        }, {

        }, {
            it.printStackTrace()
        })
    }

    fun saveStockList(list: List<StockRealmObject>, onSuccess: (List<StockRealmObject>) -> Unit) {
        realm.executeTransactionAsync({ transitionRealm ->
            transitionRealm.insertOrUpdate(list)
        }, {
            onSuccess(list)
        }) {
            it.printStackTrace()
        }
    }

    fun getStockList(onSuccess: (List<StockRealmObject>) -> Unit): List<StockRealmObject> {
        val result = realm.where(StockRealmObject::class.java)
            .findAll()
        onSuccess(result)
        return result
    }

    fun getStockList(): List<StockRealmObject> {
        return realm.where(StockRealmObject::class.java)
            .findAll()
    }


    fun getFavoriteStockList(): List<StockRealmObject> {
        return realm.where(StockRealmObject::class.java)
            .equalTo("isFavorite", true)
            .findAll()
    }

}