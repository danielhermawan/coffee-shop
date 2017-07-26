package co.folto.kopigo.ui.stock

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import co.folto.kopigo.data.model.Product
import co.folto.kopigo.data.model.ProductCategory
import co.folto.kopigo.ui.adapter.AdapterConstant
import co.folto.kopigo.ui.adapter.base.ViewType
import co.folto.kopigo.ui.adapter.base.ViewTypeDelegateAdapter
import co.folto.kopigo.ui.adapter.delegate.CategoryStockDelegateAdapter

/**
 * Created by rudys on 7/7/2017.
 */

class OrderStockAdapter(var products: MutableList<Product>, var categories: MutableList<ProductCategory>,
        val orderStockClick: () -> Unit, val onModified: (Int, Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var items: MutableList<ViewType> = ArrayList()

    private val delegateAdapter = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private val orderStockFooter = OrderStockFooterItem()

    init {
        delegateAdapter.put(AdapterConstant.CATEGORY, CategoryStockDelegateAdapter(products, onModified))
        delegateAdapter.put(AdapterConstant.ORDER_FOOTER, OrderStockDelegateAdapter(orderStockClick))
        items = categories.toMutableList()
        items.add(orderStockFooter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = delegateAdapter.get(viewType).onCreateViewHolder(parent)


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
            = delegateAdapter.get(getItemViewType(position)).onBindViewHolder(holder, this.items[position])


    override fun getItemCount(): Int =items.size

    override fun getItemViewType(position: Int): Int
        = items.get(position).getViewType()


}