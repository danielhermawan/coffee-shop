package co.folto.kopigo.ui.summaryOrder

import co.folto.kopigo.data.OrderRepository
import co.folto.kopigo.data.model.Product
import co.folto.kopigo.util.replace
import co.folto.kopigo.util.start
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Daniel on 6/20/2017 for Kopigo project.
 */
class SummaryPresenter @Inject constructor(
    private var products: MutableList<Product>,
    private val view: SummaryContract.View,
    private val orderRepository: OrderRepository
): SummaryContract.Presenter {

    private var composite = CompositeDisposable()

    override fun subscribe() {
        Timber.d(products.count().toString())
        loadSummary()
    }

    override fun unsubscribe() {
        composite.dispose()
    }

    override fun loadSummary() {
        view.showSummary(products)
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
                    view.showSnack("Stock ${product.name} sudah habis")
            products.replace(product)
        }
        view.showModifiedProduct(products)
    }

    override fun checkout(payment: Int) {
        val total = products.sumBy { it.price * it.orderQuantity }
        if(payment >= total)
            view.openSummaryDialog(products)
        else
            view.showSnack("Uang yang dibayar tidak cukup")
    }

    override fun createOrder() {
        view.showLoading(true)
        view.disableCheckout(false)
        val request = orderRepository.createOrder(products)
            .start()
            .subscribe(
                {
                    view.printReceipt(products, it.id)
                },
                {
                    Timber.e(it)
                    view.showLoading(false)
                    view.showSnack("There is some kind of network trouble")
                    view.disableCheckout(true)
                }
            )
        composite.add(request)
    }

    override fun addMoreItem() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}