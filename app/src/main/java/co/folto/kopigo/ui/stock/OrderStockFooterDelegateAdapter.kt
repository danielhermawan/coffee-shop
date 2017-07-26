package co.folto.kopigo.ui.stock

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
 * Created by rudys on 7/7/2017.
 */

class OrderStockDelegateAdapter(val orderStockClick: () -> Unit): ViewTypeDelegateAdapter{

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
            = OrderStockFooterHolder(parent.inflate(R.layout.item_order_button), orderStockClick)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {}

    class OrderStockFooterHolder(val view: View, val orderStockClick: () -> Unit): RecyclerView.ViewHolder(view) {
        init {
            itemView.buttonOrder.setOnClickListener { orderStockClick }
        }
    }
}

class OrderStockFooterItem: ViewType {
    override fun getViewType(): Int = AdapterConstant.ORDER_FOOTER
}