package com.nso.test.di.module


import android.content.Context
import com.nso.test.data.repository.StorageRepository
import com.nso.test.data.storage.impl.LocalDataSource
import com.nso.test.data.storage.mapper.StockMapper
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import java.lang.Exception
import javax.inject.Singleton

@Module
class StorageModule(private val context: Context) {

    @Provides
    fun provideStorage(realm: Realm): LocalDataSource {
        return LocalDataSource(realm)
    }

    @Provides
    fun provideMapper(): StockMapper {
        return StockMapper()
    }

    @Singleton
    @Provides
    fun provideRealm(): Realm =
        try {
            Realm.init(context)
            Realm.getDefaultInstance()
        } catch (e: Exception) {
            Realm.getInstance(provideRealmConfig())
        }

    private fun provideRealmConfig(): RealmConfiguration = RealmConfiguration.Builder().build()

    @Singleton
    @Provides
    fun providesStorageRepository(localDataSource: LocalDataSource, stockMapper: StockMapper): StorageRepository {
        return StorageRepository(localDataSource, stockMapper)
    }

}