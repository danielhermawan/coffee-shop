package co.folto.kopigo.data.contract

import co.folto.kopigo.data.model.ProductCategory
import io.reactivex.Flowable

/**
 * Created by Daniel on 6/16/2017 for Kopigo project.
 */
interface ProductContract {
    fun getProductCategories(): Flowable<List<ProductCategory>>
}