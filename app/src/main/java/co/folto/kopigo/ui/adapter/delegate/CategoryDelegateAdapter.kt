package co.folto.kopigo.ui.adapter.delegate

import android.content.Context
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import co.folto.kopigo.R
import co.folto.kopigo.data.model.Product
import co.folto.kopigo.data.model.ProductCategory
import co.folto.kopigo.ui.adapter.base.ViewType
import co.folto.kopigo.ui.adapter.base.ViewTypeDelegateAdapter
import co.folto.kopigo.ui.adapter.util.CustomLinearLayoutManager
import co.folto.kopigo.ui.order.ProductAdapter
import co.folto.kopigo.util.inflate
import co.folto.kopigo.util.obtainDrawable
import kotlinx.android.synthetic.main.item_category.view.*


/**
 * Created by Daniel on 6/20/2017 for Kopigo project.
 */
class CategoryDelegateAdapter(val products: MutableList<Product>): ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
        = CategoryViewHolder(parent.inflate(R.layout.item_category), parent.context)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as CategoryViewHolder
        holder.bind(item as ProductCategory, products)
    }

    class CategoryViewHolder(val view: View, val context: Context): RecyclerView.ViewHolder(view) {

        private val productAdapter = ProductAdapter()

        init {
            val linearLayoutManager = CustomLinearLayoutManager(context)
            val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            divider.setDrawable(itemView.resources.obtainDrawable(R.drawable.shape_brown_divider, context))
            with(itemView.rvProducts) {
                adapter = productAdapter
                setHasFixedSize(true)
                layoutManager = linearLayoutManager
                rvProducts.addItemDecoration(divider)
            }
            with(itemView) {
                viewHeader.setOnClickListener {
                    expandPanel.toggle()
                    if(!expandPanel.isExpanded)
                        buttonDropdown.setImageDrawable(resources.obtainDrawable(R.drawable.ic_arrow_up, context))
                    else
                        buttonDropdown.setImageDrawable(resources.obtainDrawable(R.drawable.ic_arrow_down, context))
                }
            }
        }

        fun bind(category: ProductCategory, products: MutableList<Product>) {
            val newProduct = products.filter { it.category.id == category.id }
                    .sortedBy { it.name }
                    .toMutableList()
            productAdapter.refreshData(newProduct)
            itemView.textCategory.text = category.name
        }
    }
}