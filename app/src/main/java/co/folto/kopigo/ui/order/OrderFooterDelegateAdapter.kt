package co.folto.kopigo.ui.order

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import co.folto.kopigo.R
import co.folto.kopigo.ui.adapter.AdapterConstant
import co.folto.kopigo.ui.adapter.base.ViewType
import co.folto.kopigo.ui.adapter.base.ViewTypeDelegateAdapter
import co.folto.kopigo.util.inflate
import kotlinx.android.synthetic.main.item_order_button.view.*

/**
 * Created by Daniel on 6/19/2017 for Kopigo project.
 */
class OrderFooterDelegateAdapter(val orderClick: () -> Unit): ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
            = OrderFooterHolder(parent.inflate(R.layout.item_order_button), orderClick)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {}

    class OrderFooterHolder(val view: View, val orderClick: () -> Unit): RecyclerView.ViewHolder(view) {
        init {
            itemView.buttonOrder.setOnClickListener { orderClick() }
        }
    }
}

class OrderFooterItem: ViewType {
    override fun getViewType(): Int = AdapterConstant.ORDER_FOOTER
}