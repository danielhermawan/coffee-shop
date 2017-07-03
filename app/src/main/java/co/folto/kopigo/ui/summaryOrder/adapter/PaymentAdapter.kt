package co.folto.kopigo.ui.summaryOrder.adapter

import android.support.v4.util.SparseArrayCompat
import co.folto.kopigo.data.model.Product
import co.folto.kopigo.ui.adapter.AdapterConstant
import co.folto.kopigo.ui.adapter.base.BaseAdapter
import co.folto.kopigo.ui.adapter.base.ViewTypeDelegateAdapter

/**
 * Created by Daniel on 6/21/2017 for Kopigo project.
 */
class PaymentAdapter(val products: MutableList<Product>)
    : BaseAdapter() {

    private val delegateAdapter = SparseArrayCompat<ViewTypeDelegateAdapter>()

    init {
        delegateAdapter.put(AdapterConstant.PAYMENT, PaymentDelegateAdapter())
        delegateAdapter.put(AdapterConstant.TOTAL, TotalDelegateAdapter())
        items = products.toMutableList()
        items.add(TotalItem(products.sumBy { it.orderQuantity * it.price }))
    }

    override fun getAdapter(): SparseArrayCompat<ViewTypeDelegateAdapter> = delegateAdapter

    override fun getItemViewType(position: Int): Int {
        if(items[position] is Product)
            return AdapterConstant.PAYMENT
        else
            return super.getItemViewType(position)
    }

    fun refreshData(products: MutableList<Product>) {
        items = products.toMutableList()
        items.add(TotalItem(products.sumBy { it.orderQuantity * it.price }))
        notifyDataSetChanged()
    }
}

