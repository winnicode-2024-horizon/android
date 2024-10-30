package id.winnicode.horizon

import android.app.Application
import id.winnicode.horizon.di.Injection

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        injection = Injection(applicationContext)
    }

    companion object {
        lateinit var injection: Injection
    }

}