package co.folto.kopigo.ui.summaryOrder

import co.folto.kopigo.data.model.Product
import co.folto.kopigo.ui.base.BasePresenter
import co.folto.kopigo.ui.base.BaseView

/**
 * Created by Daniel on 6/20/2017 for Kopigo project.
 */
interface SummaryContract {
    interface View: BaseView {
        fun showLoading(active: Boolean)
        fun showSnack(message: String)
        fun navigateToHome()
        fun openSummaryDialog(products: MutableList<Product>)
        fun navigateToAddItem()
        fun showSummary(products: MutableList<Product>)
        fun showModifiedProduct(products: MutableList<Product>)
        fun printReceipt(products: MutableList<Product>, id: Int)
    }

    interface Presenter: BasePresenter {
        fun checkout()
        fun addMoreItem()
        fun loadSummary()
        fun modifyProduct(id: Int, qty: Int)
        fun createOrder()
    }
}