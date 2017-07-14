package co.folto.kopigo.data.contract

import co.folto.kopigo.data.model.Product
import io.reactivex.Completable

/**
 * Created by Daniel on 7/13/2017 for Kopigo project.
 */
interface OrderCotract {
    fun createOrder(products: List<Product>): Completable
}