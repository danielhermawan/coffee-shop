package co.folto.kopigo.data.model

import co.folto.kopigo.ui.adapter.AdapterConstant
import co.folto.kopigo.ui.adapter.base.ViewType

/**
 * Created by Daniel on 6/16/2017 for Kopigo project.
 */
class Product: ViewType {
    var id: Int = 0
    var name: String = ""
    var price: Int = 0
    var imageUrl: String = ""
    var quantity: Int = 0
    var category: ProductCategory = ProductCategory()
    var orderQuantity: Int = 0

    override fun getViewType(): Int = AdapterConstant.PRODUCT
}