package ru.webant.app

import android.app.Application
import com.airbnb.mvrx.Mavericks
import com.airbnb.mvrx.MavericksViewModelConfigFactory
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm

@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Mavericks.initialize(this, MavericksViewModelConfigFactory(debugMode = false))
        Realm.init(this)
    }
}