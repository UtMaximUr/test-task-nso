package com.nso.test.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nso.test.data.repository.RemoteRepository
import com.nso.test.data.repository.StorageRepository
import com.nso.test.ui.SharedViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedViewModelFactory @Inject constructor(
    private var storageRepository: StorageRepository,
    private var remoteRepository: RemoteRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SharedViewModel(storageRepository, remoteRepository) as T
    }
}