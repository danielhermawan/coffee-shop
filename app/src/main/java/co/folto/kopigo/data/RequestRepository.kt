package co.folto.kopigo.data

import co.folto.kopigo.data.contract.RequestContract
import co.folto.kopigo.data.model.OrderRequest
import co.folto.kopigo.data.model.Product
import co.folto.kopigo.data.model.ProductOrder
import co.folto.kopigo.data.remote.KopigoService
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Daniel on 7/28/2017 for Kopigo project.
 */

@Singleton
class RequestRepository @Inject constructor(
    private val kopigoService: KopigoService
): RequestContract {
    override fun createRequest(products: List<Product>): Completable {
        val productsRequests = products.map {
            ProductOrder(it.id, it.orderQuantity)
        }
        return kopigoService.createRequest(OrderRequest(productsRequests))
    }
}