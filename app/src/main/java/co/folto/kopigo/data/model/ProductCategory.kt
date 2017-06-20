package co.folto.kopigo.data.model

import co.folto.kopigo.ui.adapter.AdapterConstant
import co.folto.kopigo.ui.adapter.base.ViewType

/**
 * Created by Daniel on 6/16/2017 for Kopigo project.
 */
class ProductCategory: ViewType {
    var id: Int = 0
    var name: String = ""

    override fun getViewType(): Int = AdapterConstant.CATEGORY
}