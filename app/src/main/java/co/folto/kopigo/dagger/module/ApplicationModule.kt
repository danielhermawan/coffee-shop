package co.folto.kopigo.dagger.module

import android.app.Application
import android.content.Context
import co.folto.gitfinder.injection.annotation.ApplicationContext
import com.squareup.otto.Bus
import com.squareup.otto.ThreadEnforcer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Daniel on 6/12/2017 for Kopigo project.
 */
@Module
class ApplicationModule(val application: Application) {

    @Provides
    @Singleton
    fun providesApplication(): Application = application

    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext(): Context = application

    @Provides
    @Singleton
    fun providesBus(): Bus = Bus(ThreadEnforcer.ANY)

}