package com.nso.test

import android.app.Application
import com.nso.test.di.component.AppComponent
import com.nso.test.di.component.DaggerAppComponent
import com.nso.test.di.module.StorageModule

open class App : Application() {

    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this

        appComponent = DaggerAppComponent
            .builder()
            .storageModule(StorageModule(this))
            .build()
    }
}