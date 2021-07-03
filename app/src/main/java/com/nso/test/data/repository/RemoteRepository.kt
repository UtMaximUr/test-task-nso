package com.nso.test.data.repository

import com.nso.test.data.mapper.CompanyNewsMapper
import com.nso.test.data.remote.RemoteDataSource
import com.nso.test.data.remote.model.Stock
import com.nso.test.domain.entity.CompanyNewsEntity


class RemoteRepository(private val remoteDataSource: RemoteDataSource,
                       private val companyNewsMapper: CompanyNewsMapper
) {

    fun getStockList(count: Int, onSuccess: (List<Stock>?) -> Unit) {
        remoteDataSource.getStockList(count, onSuccess)
    }

    fun getNews(symbol: String, from: String, to: String, onSuccess: (List<CompanyNewsEntity>) -> Unit) {
        remoteDataSource.getNews(symbol, from, to) {
            onSuccess(companyNewsMapper.companyNewsEntityMapper(it))
        }
    }
}