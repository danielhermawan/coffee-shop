package co.folto.kopigo.ui.order

import dagger.Module
import dagger.Provides

/**
 * Created by Daniel on 6/19/2017 for Kopigo project.
 */

@Module
class OrderModule(val view: OrderContract.View) {

    @Provides
    fun provideView(): OrderContract.View = view
}