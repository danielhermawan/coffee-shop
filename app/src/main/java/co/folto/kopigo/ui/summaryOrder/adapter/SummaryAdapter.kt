package co.folto.kopigo.ui.summaryOrder.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import co.folto.kopigo.R
import co.folto.kopigo.data.model.Product
import co.folto.kopigo.util.inflate
import co.folto.kopigo.util.thoundsandSeperator
import kotlinx.android.synthetic.main.item_summary.view.*

/**
 * Created by Daniel on 6/21/2017 for Kopigo project.
 */
class SummaryAdapter(var products: MutableList<Product>, val onModified: (Int, Int) -> Unit )
    : RecyclerView.Adapter<SummaryAdapter.SummaryPlaceHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryPlaceHolder {
        val view = parent.inflate(R.layout.item_summary)
        return SummaryPlaceHolder(view, parent.context, onModified)
    }

    override fun onBindViewHolder(holder: SummaryPlaceHolder?, position: Int) {
        holder?.bind(products[position])
    }

    override fun getItemCount(): Int = products.count()

    fun refreshData(products: MutableList<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    class SummaryPlaceHolder(val view: View, val context: Context, val onModified: (Int, Int) -> Unit)
        : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(product: Product)
            = with(itemView) {
                textProduct.text = product.name.capitalize()
                textPrice.text = "IDR ${product.price.thoundsandSeperator()}"
                editQuantity.text = "" + product.orderQuantity
                buttonMinus.setOnClickListener {
                    onModified(product.id, -1)
                    editQuantity.setText("" + product.orderQuantity)
                }
                buttonPlus.setOnClickListener {
                    onModified(product.id, 1)
                    editQuantity.setText("" + product.orderQuantity)
                }
            }

    }
}