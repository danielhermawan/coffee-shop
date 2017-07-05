package co.folto.kopigo.ui.order

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import co.folto.kopigo.data.model.Product
import co.folto.kopigo.data.model.ProductCategory
import co.folto.kopigo.ui.adapter.AdapterConstant
import co.folto.kopigo.ui.adapter.base.ViewType
import co.folto.kopigo.ui.adapter.base.ViewTypeDelegateAdapter
import co.folto.kopigo.ui.adapter.delegate.CategoryDelegateAdapter
import java.util.*

/**
 * Created by Daniel on 6/19/2017 for Kopigo project.
 */
class OrderAdapter(var products: MutableList<Product>, var categories: MutableList<ProductCategory>,
                   val orderClick: () -> Unit, val onModified: (Int, Int) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var items: MutableList<ViewType> = ArrayList()

    private val delegateAdapter = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private val orderFooter = OrderFooterItem()

    init {
        //delegateAdapter.put(AdapterConstant.PRODUCT, ProductDelegateAdapter())
        delegateAdapter.put(AdapterConstant.CATEGORY, CategoryDelegateAdapter(products, onModified))
        delegateAdapter.put(AdapterConstant.ORDER_FOOTER, OrderFooterDelegateAdapter(orderClick))
        items = categories.toMutableList()
        items.add(orderFooter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = delegateAdapter.get(viewType).onCreateViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
            = delegateAdapter.get(getItemViewType(position)).onBindViewHolder(holder, this.items[position])

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int
            = items.get(position).getViewType()

    /*fun refreshData(datas: MutableList<ProductCategory>) {
        items = datas.toMutableList()
        items.add(orderFooter)
        notifyDataSetChanged()
    }*/

}