package co.folto.kopigo

import android.app.Application
import co.folto.kopigo.dagger.Component.DaggerDataComponent
import co.folto.kopigo.dagger.Component.DataComponent
import co.folto.kopigo.dagger.module.ApplicationModule
import com.facebook.stetho.Stetho
import timber.log.Timber


/**
 * Created by Daniel on 6/12/2017 for Kopigo project.
 */
class KopigoApplication: Application() {

    companion object {
        @JvmStatic lateinit var dataComponent: DataComponent
    }

    override fun onCreate() {
        super.onCreate()
        /*if (LeakCanary.isInAnalyzerProcess(this)) return
        LeakCanary.install(this)*/
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
        }
        dataComponent = DaggerDataComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}