package co.folto.kopigo.dagger.module

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import co.folto.gitfinder.injection.annotation.ApplicationContext
import co.folto.kopigo.data.local.AppDatabase
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

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application)
            = Room.databaseBuilder(app, AppDatabase::class.java, "kopigo.db").build()
}