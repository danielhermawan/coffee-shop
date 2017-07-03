package co.folto.kopigo.ui.summaryOrder

import co.folto.kopigo.data.model.Product
import dagger.Module
import dagger.Provides

/**
 * Created by Daniel on 6/20/2017 for Kopigo project.
 */

@Module
class SummaryModule(val view: SummaryContract.View, val products: MutableList<Product>) {

    @Provides
    fun provideView(): SummaryContract.View = view

    @Provides
    fun provideProducts(): MutableList<Product> = products
}