package co.folto.kopigo.ui.stock

import co.folto.kopigo.data.model.Product
import co.folto.kopigo.data.model.ProductCategory
import co.folto.kopigo.ui.base.BasePresenter
import co.folto.kopigo.ui.base.BaseView

/**
 * Created by rudys on 7/5/2017.
 */
interface StockContract {
    interface View: BaseView {
        fun showLoading(boolean: Boolean)
        fun showMessage(string: String)
        fun navigateToHome()
        fun navigateToLogin()
        fun showProduct(categories: MutableList<ProductCategory>, products: MutableList<Product>)
        fun openSummaryDialog(products: List<Product>)
    }

    interface Presenter: BasePresenter {
        fun logout()
        fun loadProductAndCategory()
        fun makeOrder()
        fun modifyProduct(id: Int, qty: Int)
        fun requestStock(products: ArrayList<Product>)
    }
}