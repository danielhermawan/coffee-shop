package co.folto.kopigo.ui.stock

import dagger.Module
import dagger.Provides

/**
 * Created by rudys on 7/5/2017.
 */

@Module
class StockModule(val View: StockContract.View) {

    @Provides
    fun provideView(): StockContract.View = View
}