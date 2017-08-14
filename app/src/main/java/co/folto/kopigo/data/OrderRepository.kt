package co.folto.kopigo.data

import co.folto.kopigo.data.contract.OrderCotract
import co.folto.kopigo.data.model.Order
import co.folto.kopigo.data.model.OrderRequest
import co.folto.kopigo.data.model.Product
import co.folto.kopigo.data.model.ProductOrder
import co.folto.kopigo.data.remote.KopigoService
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Daniel on 7/13/2017 for Kopigo project.
 */
@Singleton
class OrderRepository @Inject constructor(
    private val kopigoService: KopigoService
): OrderCotract {
    override fun createOrder(products: List<Product>): Flowable<Order> {
        val productOrders = products.map {
            ProductOrder(it.id, it.orderQuantity)
        }
        return kopigoService.createOrder(OrderRequest(productOrders))
    }
}