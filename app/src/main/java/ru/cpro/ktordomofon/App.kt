package ru.cpro.ktordomofon

import android.app.Application
import ru.cpro.ktordomofon.di.AppModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appModule = AppModule.Base(this)
    }

    companion object {
        lateinit var appModule: AppModule
    }
}