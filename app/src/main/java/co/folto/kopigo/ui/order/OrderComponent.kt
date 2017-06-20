package co.folto.kopigo.ui.order

import co.folto.kopigo.dagger.Component.DataComponent
import co.folto.kopigo.dagger.annotation.ActivityScoped
import dagger.Component

/**
 * Created by Daniel on 6/19/2017 for Kopigo project.
 */
@ActivityScoped
@Component(dependencies = arrayOf(DataComponent::class), modules = arrayOf(OrderModule::class))
interface OrderComponent {

    fun inject(orderActivity: OrderActivity)
}