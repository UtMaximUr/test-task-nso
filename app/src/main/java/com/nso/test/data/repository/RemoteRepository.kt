package com.nso.test.data.repository

import com.nso.test.data.remote.impl.RemoteDataSource
import com.nso.test.data.remote.model.CompanyNews
import com.nso.test.data.remote.model.Stock


class RemoteRepository(private val remoteDataSource: RemoteDataSource) {

    fun getStockList(count: Int, onSuccess: (List<Stock>?) -> Unit) {
        remoteDataSource.getStockList(count, onSuccess)
    }

    fun getNews(symbol: String, from: String, to: String, onSuccess: (List<CompanyNews>) -> Unit) {
        remoteDataSource.getNews(symbol, from, to, onSuccess)
    }
}