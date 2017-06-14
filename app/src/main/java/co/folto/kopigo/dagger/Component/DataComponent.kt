package co.folto.kopigo.dagger.Component

import co.folto.kopigo.dagger.module.ApplicationModule
import co.folto.kopigo.dagger.module.LocalDataModule
import co.folto.kopigo.dagger.module.RemoteDataModule
import co.folto.kopigo.data.UserRepository
import co.folto.kopigo.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Daniel on 6/12/2017 for Kopigo project.
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class, LocalDataModule::class, RemoteDataModule::class))
interface DataComponent {

    fun userRepository(): UserRepository

    fun inject(mainActivity: MainActivity)
}