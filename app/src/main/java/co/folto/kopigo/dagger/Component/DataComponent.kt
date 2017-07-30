package co.folto.kopigo.dagger.Component

import co.folto.kopigo.KopigoApplication
import co.folto.kopigo.dagger.module.ApplicationModule
import co.folto.kopigo.dagger.module.LocalDataModule
import co.folto.kopigo.dagger.module.RemoteDataModule
import co.folto.kopigo.data.OrderRepository
import co.folto.kopigo.data.ProductRepository
import co.folto.kopigo.data.RequestRepository
import co.folto.kopigo.data.UserRepository
import com.squareup.otto.Bus
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Daniel on 6/12/2017 for Kopigo project.
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class, LocalDataModule::class, RemoteDataModule::class))
interface DataComponent {

    fun inject(application: KopigoApplication)

    fun userRepository(): UserRepository
    fun orderRepository(): OrderRepository
    fun productRepository(): ProductRepository
    fun requestRepository(): RequestRepository
    fun bus(): Bus
}