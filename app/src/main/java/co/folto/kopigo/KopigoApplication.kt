package co.folto.kopigo

import android.app.Application
import android.content.Intent
import co.folto.kopigo.dagger.Component.DaggerDataComponent
import co.folto.kopigo.dagger.Component.DataComponent
import co.folto.kopigo.dagger.module.ApplicationModule
import co.folto.kopigo.data.BusEvent
import co.folto.kopigo.data.UserRepository
import co.folto.kopigo.ui.login.LoginActivity
import co.folto.kopigo.util.showToast
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import com.squareup.otto.Bus
import com.squareup.otto.Subscribe
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by Daniel on 6/12/2017 for Kopigo project.
 */
class KopigoApplication: Application() {

    @Inject lateinit var bus: Bus
    @Inject lateinit var userRepository: UserRepository

    companion object {
        @JvmStatic lateinit var dataComponent: DataComponent
    }

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
        dataComponent.inject(this)
        bus.register(this)
    }

    @Subscribe
    fun onAuthenticationError(event: BusEvent.AuthenticationError) {
        userRepository.clearData().subscribe {
            showToast("Your session expired")
            val intent = LoginActivity.newIntent(this)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}