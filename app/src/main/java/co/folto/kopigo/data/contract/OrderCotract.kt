package co.folto.kopigo.data.contract

import co.folto.kopigo.data.model.Order
import co.folto.kopigo.data.model.Product
import io.reactivex.Flowable

/**
 * Created by Daniel on 7/13/2017 for Kopigo project.
 */
interface OrderCotract {
    fun createOrder(products: List<Product>): Flowable<Order>
}