package com.nso.test.ui


import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nso.test.data.remote.model.CompanyNews
import com.nso.test.data.repository.RemoteRepository
import com.nso.test.data.repository.StorageRepository
import com.nso.test.domain.entity.StockEntity
import com.nso.test.utils.DEFAULT_COUNT


class SharedViewModel(
    private val storageRepository: StorageRepository,
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    val stockData = MutableLiveData<StockEntity>()
    var newsData: List<CompanyNews>? = null

    private var handler: Handler? = null
    private lateinit var runnable: Runnable

    fun setStock(stock: StockEntity) {
        stockData.postValue(stock)
    }

    fun getStockList(onSuccess: (List<StockEntity>) -> Unit) {
        if (storageRepository.getStockList(onSuccess).isEmpty()) {
            remoteRepository.getStockList(DEFAULT_COUNT) { stockList ->
                if (!stockList.isNullOrEmpty()) {
                    storageRepository.saveStockList(stockList, onSuccess)
                }
            }
        }
    }

    fun getRemoteStockList(onSuccess: (List<StockEntity>) -> Unit) {
        remoteRepository.getStockList(DEFAULT_COUNT) { stockList ->
            if (!stockList.isNullOrEmpty()) {
                storageRepository.saveStockList(stockList) {
                    storageRepository.getStockList(onSuccess)
                    if (handler != null) {
                        handler?.removeCallbacks(runnable)
                        handler = null
                    }
                }
            } else {
                handler = Handler(Looper.getMainLooper())
                runnable = Runnable {
                    getRemoteStockList(onSuccess)
                }
                handler?.postDelayed(runnable, 5000)
            }
        }
    }

    fun getFavoriteList() = storageRepository.getFavoriteStockList()

    fun getNews(symbol: String, from: String, to: String, onSuccess: (List<CompanyNews>) -> Unit) {
        if (newsData == null) {
            remoteRepository.getNews(symbol, from, to) { news ->
                newsData = news
                onSuccess(news)
            }
        } else {
            onSuccess(newsData!!)
        }
    }

    fun updateStock(stock: StockEntity) {
        storageRepository.updateStock(stock)
    }
}