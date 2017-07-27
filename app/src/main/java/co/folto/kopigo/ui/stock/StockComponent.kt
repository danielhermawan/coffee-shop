package co.folto.kopigo.ui.stock

import co.folto.kopigo.dagger.Component.DataComponent
import co.folto.kopigo.dagger.annotation.ActivityScoped
import dagger.Component

/**
 * Created by rudys on 7/5/2017.
 */

@ActivityScoped
@Component(dependencies = arrayOf(DataComponent::class), modules = arrayOf(StockModule::class))
interface StockComponent {

    fun inject(stockActivity: StockActivity)
}