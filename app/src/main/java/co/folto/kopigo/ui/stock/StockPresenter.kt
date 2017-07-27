package co.folto.kopigo.ui.stock

import co.folto.kopigo.data.ProductRepository
import co.folto.kopigo.data.UserRepository
import co.folto.kopigo.data.model.Product
import co.folto.kopigo.data.model.ProductCategory
import co.folto.kopigo.util.replace
import co.folto.kopigo.util.start
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by rudys on 7/5/2017.
 */
class StockPresenter @Inject constructor(
        private val productRepository: ProductRepository,
        private val userRepository: UserRepository,
        private val view: StockContract.View
) : StockContract.Presenter {

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


    // error logic
    override fun modifyProduct(id: Int, qty: Int) {
        val product = products.find { it.id == id}
        if (product != null) {
            if (product.orderQuantity > 0 && qty < 0)
                product.orderQuantity += qty
            if (qty > 0)
                product.orderQuantity += qty
            products.replace(product)
        }
    }


    override fun makeOrder() {
        val orderedStockProduct = products.filter {
            it.orderQuantity != 0
        }
       //  view.openSummaryDialog()
    }


}