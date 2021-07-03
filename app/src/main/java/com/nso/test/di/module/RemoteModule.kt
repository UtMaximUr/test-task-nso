package com.nso.test.di.module

import com.nso.test.data.mapper.CompanyNewsMapper
import com.nso.test.data.mapper.StockMapper
import com.nso.test.data.remote.RemoteDataSource
import com.nso.test.data.repository.RemoteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteModule {

    @Provides
    fun provideRemote(): RemoteDataSource {
        return RemoteDataSource()
    }

    @Provides
    fun provideMapper(): CompanyNewsMapper {
        return CompanyNewsMapper()
    }

    @Singleton
    @Provides
    fun providesRemoteRepository(
        remoteDataSource: RemoteDataSource,
        companyNewsMapper: CompanyNewsMapper
    ): RemoteRepository {
        return RemoteRepository(remoteDataSource, companyNewsMapper)
    }
}