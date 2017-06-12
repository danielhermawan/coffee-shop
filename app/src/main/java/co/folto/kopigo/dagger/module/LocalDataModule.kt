package co.folto.kopigo.dagger.module

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import co.folto.gitfinder.injection.annotation.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Daniel on 6/12/2017 for Kopigo project.
 */
@Module
class LocalDataModule {

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
}