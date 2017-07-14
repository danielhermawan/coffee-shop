package co.folto.kopigo.ui.order

import co.folto.kopigo.data.UserRepository
import co.folto.kopigo.data.model.Product
import co.folto.kopigo.data.model.ProductCategory
import co.folto.kopigo.util.replace
import co.folto.kopigo.util.start
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * Created by Daniel on 6/16/2017 for Kopigo project.
 */
class OrderPresenter @Inject constructor(
    private val userRepository: UserRepository,
    private val view: OrderContract.View
): OrderContract.Presenter {

    private var composite = CompositeDisposable()
    private var products: MutableList<Product> = ArrayList<Product>()
    private var categories: MutableList<ProductCategory> = ArrayList<ProductCategory>()

    override fun subscribe() {
        loadProductAndCategory()
    }

    override fun unsubscribe() {
        composite.dispose()
    }

    override fun logout() {
        composite.clear()
        view.showLoading(true)
        val request = userRepository.logout()
            .start()
            .subscribeBy(
                onComplete = {
                    view.showMessage("Successfully logout from kopigo")
                    view.navigateToLogin()
                },
                onError = {
                    Timber.e(it)
                    view.showLoading(false)
                    view.showMessage("There is some kind of network trouble")
                }
            )
        composite.add(request)
    }

    override fun loadProductAndCategory() {
        view.showLoading(true)
        val request = userRepository.getUserProducts()
            .start()
            .subscribeBy(
                {
                    products = it.toMutableList()
                    categories = products.map { it.category }.distinctBy { it.id }.toMutableList()
                    view.showProduct(categories, products)
                    if(products.count() != 0)
                        view.showLoading(false)
                },
                {
                    Timber.e(it)
                    view.showLoading(false)
                    view.showMessage("There is some kind of network trouble")
                }
            )
        composite.add(request)
    }

    override fun modifyProduct(id: Int, qty: Int) {
        val product = products.find { it.id == id }
        if(product != null){
            if(product.orderQuantity > 0 && qty < 0)
                product.orderQuantity += qty
            if(qty > 0)
                if(product.orderQuantity < product.quantity)
                    product.orderQuantity += qty
                else
                    view.showMessage("Stock ${product.name} sudah habis")
            products.replace(product)
        }
    }

    override fun makeOrder() {
        val orderedProduct = products.filter {
            it.orderQuantity != 0
        }
        if(orderedProduct.isEmpty())
            view.showMessage("Barang tidak boleh kosong")
        else
            view.navigateToSummary(orderedProduct)
    }
}