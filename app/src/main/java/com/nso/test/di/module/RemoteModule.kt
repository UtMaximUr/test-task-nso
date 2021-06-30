package com.nso.test.di.module

import com.nso.test.data.remote.impl.RemoteDataSource
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

    @Singleton
    @Provides
    fun providesRemoteRepository(remoteDataSource: RemoteDataSource): RemoteRepository {
        return RemoteRepository(remoteDataSource)
    }
}