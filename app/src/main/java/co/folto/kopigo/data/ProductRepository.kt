package co.folto.kopigo.data

import co.folto.kopigo.data.contract.ProductContract
import co.folto.kopigo.data.local.PreferenceHelper
import co.folto.kopigo.data.model.ProductCategory
import co.folto.kopigo.data.remote.KopigoService
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Daniel on 6/16/2017 for Kopigo project.
 */

@Singleton
class ProductRepository @Inject constructor(
    private val kopigoService: KopigoService,
    private val preferenceHelper: PreferenceHelper
): ProductContract {
    override fun getProductCategories(): Flowable<List<ProductCategory>> {
        return kopigoService.getCategories()
    }
}