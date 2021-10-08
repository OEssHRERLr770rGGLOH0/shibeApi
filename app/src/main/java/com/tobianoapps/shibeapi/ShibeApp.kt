package com.tobianoapps.shibeapi

import android.app.Application
import com.tobianoapps.shibeapi.di.Koin.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.logger.Level

class ShibeApp : Application() {

    override fun onCreate() {
        super.onCreate()

        /**
         * Koin needs to be initialized here.
         */
        GlobalContext.startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@ShibeApp)
            modules(listOf(appModule))
        }
    }
}
