package co.folto.kopigo.ui.order

import co.folto.kopigo.data.model.Product
import co.folto.kopigo.data.model.ProductCategory
import co.folto.kopigo.ui.base.BasePresenter
import co.folto.kopigo.ui.base.BaseView

/**
 * Created by Daniel on 6/16/2017 for Kopigo project.
 */
interface OrderContract {
    interface View: BaseView {
        fun showLoading(active: Boolean)
        fun showMessage(message: String)
        fun navigateToLogin()
        fun showProduct(categories: MutableList<ProductCategory>, products: MutableList<Product>)
        fun navigateToSummary(products: List<Product>)
    }

    interface Presenter: BasePresenter {
        fun logout()
        fun loadProductAndCategory()
        fun makeOrder()
    }
}