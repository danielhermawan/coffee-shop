package co.folto.kopigo

import android.app.Application
import co.folto.kopigo.dagger.Component.DaggerDataComponent
import co.folto.kopigo.dagger.Component.DataComponent
import co.folto.kopigo.dagger.module.ApplicationModule
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber


/**
 * Created by Daniel on 6/12/2017 for Kopigo project.
 */
class KopigoApplication: Application() {

    companion object {
        @JvmStatic lateinit var dataComponent: DataComponent
    }
    //todo: Make event logout when unautheticate
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) return
            LeakCanary.install(this)
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
        }
        dataComponent = DaggerDataComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}