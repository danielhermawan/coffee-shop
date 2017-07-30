package co.folto.kopigo.data.contract

import co.folto.kopigo.data.model.Product
import io.reactivex.Completable

/**
 * Created by Daniel on 7/28/2017 for Kopigo project.
 */
interface RequestContract {
    fun createRequest(products: List<Product>): Completable;
}