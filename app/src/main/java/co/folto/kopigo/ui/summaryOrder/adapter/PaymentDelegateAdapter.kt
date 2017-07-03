package co.folto.kopigo.ui.summaryOrder.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import co.folto.kopigo.R
import co.folto.kopigo.data.model.Product
import co.folto.kopigo.ui.adapter.base.ViewType
import co.folto.kopigo.ui.adapter.base.ViewTypeDelegateAdapter
import co.folto.kopigo.util.inflate
import co.folto.kopigo.util.thoundsandSeperator
import kotlinx.android.synthetic.main.item_payment.view.*

/**
 * Created by Daniel on 6/21/2017 for Kopigo project.
 */
class PaymentDelegateAdapter(): ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
        = PaymentViewHolder(parent.inflate(R.layout.item_payment))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as PaymentViewHolder
        holder.bind(item as Product)
    }

    class PaymentViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        fun bind(product: Product)
            = with(itemView) {
                textProduct.text = product.name.capitalize()
                textPrice.text = "IDR ${(product.price * product.orderQuantity).thoundsandSeperator()}"
            }
    }
}