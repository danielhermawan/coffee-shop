package co.folto.kopigo.ui.summaryOrder.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import co.folto.kopigo.R
import co.folto.kopigo.ui.adapter.AdapterConstant
import co.folto.kopigo.ui.adapter.base.ViewType
import co.folto.kopigo.ui.adapter.base.ViewTypeDelegateAdapter
import co.folto.kopigo.util.inflate
import co.folto.kopigo.util.thoundsandSeperator
import kotlinx.android.synthetic.main.item_total.view.*

/**
 * Created by Daniel on 6/21/2017 for Kopigo project.
 */
class TotalDelegateAdapter(): ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
            = TotalViewHolder(parent.inflate(R.layout.item_total))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as TotalViewHolder
        //val total = products.sumBy { it.quantity * it.price }
        holder.bind((item as TotalItem).total)
    }

    class TotalViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun bind(total: Int)
            = with(itemView) {
                textTotal.text = "IDR ${total.thoundsandSeperator()}"
            }
    }
}

data class TotalItem(val total: Int): ViewType {
    override fun getViewType(): Int = AdapterConstant.TOTAL
}