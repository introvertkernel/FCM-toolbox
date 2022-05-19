package com.gb.fcm

import android.app.Application
import com.gb.fcm.di.database
import com.gb.fcm.di.firebase
import com.gb.fcm.di.json
import com.gb.fcm.di.main
import com.gb.fcm.di.token
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


@Suppress("unused")
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(main, token, json, database, firebase))
        }
    }

}
