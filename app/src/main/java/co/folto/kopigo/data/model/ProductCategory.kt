package co.folto.kopigo.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import co.folto.kopigo.ui.adapter.AdapterConstant
import co.folto.kopigo.ui.adapter.base.ViewType

/**
 * Created by Daniel on 6/16/2017 for Kopigo project.
 */
@Entity
class ProductCategory: ViewType {
    @PrimaryKey var id: Int = 0
    var name: String = ""

    override fun getViewType(): Int = AdapterConstant.CATEGORY
}