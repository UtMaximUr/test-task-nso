package com.nso.test.di.component

import com.nso.test.di.module.RemoteModule
import com.nso.test.ui.news_stock.NewsFragment
import com.nso.test.ui.stock.StockFragment
import com.nso.test.ui.stock_list.common.CommonFragment
import com.nso.test.ui.stock_list.favorite.FavoriteFragment
import com.nso.test.data.repository.RemoteRepository
import com.nso.test.data.repository.StorageRepository
import com.nso.test.di.module.StorageModule
import com.nso.test.ui.news_stock.NewsWebViewFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [RemoteModule::class, StorageModule::class])
interface AppComponent {

    fun provideStorageRepository(): StorageRepository

    fun provideRemoteRepository(): RemoteRepository

    fun inject(stockFragment: StockFragment)

    fun inject(commonFragment: CommonFragment)

    fun inject(favoriteFragment: FavoriteFragment)

    fun inject(newsFragment: NewsFragment)

    fun inject(newsWebViewFragment: NewsWebViewFragment)

}