package com.nso.test.data.remote

import android.util.Log
import com.nso.test.data.remote.model.CompanyNews
import com.nso.test.data.remote.model.Stock
import com.nso.test.data.remote.model.StocksInfo
import com.nso.test.utils.API_TOKEN
import com.nso.test.utils.STOCK_EXCHANGE
import com.nso.test.utils.refactoringCompany
import io.finnhub.api.apis.DefaultApi
import io.finnhub.api.infrastructure.ApiClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.schedulers.Schedulers

class RemoteDataSource {

    fun getStockList(count: Int, onSuccess: (List<Stock>?) -> Unit) {
        val listElements: ArrayList<Stock> = ArrayList()
        val apiClient = DefaultApi()
        val stock = Observable.create { subscriber: ObservableEmitter<List<Stock>> ->
            ApiClient.apiKey["token"] = API_TOKEN
            apiClient.indicesConstituents(STOCK_EXCHANGE).constituents?.take(count)
                ?.forEach { symbol ->
                    val newAny = StocksInfo(
                        item = apiClient.companyProfile2(symbol, null, null),
                        price = apiClient.quote(symbol)
                    )
                    val company: StocksInfo = newAny
                    val delta: String = company.refactoringCompany()
                    listElements.add(
                        Stock(
                            name = company.item.name.toString(),
                            logo = company.item.logo.toString(),
                            ticker = company.item.ticker.toString(),
                            price = company.price.c.toString()
                                    + " " + company.item.currency.toString(),
                            delta = delta,
                            currency = company.item.currency.toString(),
                            isFavorite = false
                        )
                    )
                }
            subscriber.onNext(listElements)
            subscriber.onComplete()
        }
        stock
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    onSuccess(list)
                }, { e: Throwable ->
                    onSuccess(null)
                    Log.e("RemoteDataSourceImpl", "Error: $e")
                }, {
                }
            )
    }

    fun getNews(symbol: String, from: String, to: String, onSuccess: (List<CompanyNews>) -> Unit) {
        val listNews: ArrayList<CompanyNews> = ArrayList()
        val apiClient = DefaultApi()
        val news = Observable.create { subscriber: ObservableEmitter<List<CompanyNews>> ->
            ApiClient.apiKey["token"] = API_TOKEN
            apiClient.companyNews(symbol, from, to).forEach {
                listNews.add(
                    CompanyNews(
                        category = it.category,
                        datetime = it.datetime,
                        headline = it.headline,
                        id = it.id,
                        image = it.image,
                        related = it.related,
                        source = it.source,
                        summary = it.summary,
                        url = it.url
                    )
                )
                subscriber.onNext(listNews)
            }
            subscriber.onComplete()
        }
        news
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    onSuccess(list)
                }, { e: Throwable ->
                    Log.e("RemoteDataSourceImpl", "Error: $e")
                }, {

                }
            )
    }
}