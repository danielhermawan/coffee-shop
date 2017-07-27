package co.folto.kopigo.ui.stock.summaryStockDialog

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import co.folto.kopigo.R
import co.folto.kopigo.data.model.Product
import co.folto.kopigo.util.inflate
import kotlinx.android.synthetic.main.item_summary_dialog.view.*

/**
 * Created by rudys on 7/11/2017.
 */
class SummaryDialogAdapter(var product: MutableList<Product>): RecyclerView.Adapter<SummaryDialogAdapter.PlaceHolder>() {

    override fun getItemCount(): Int = product.size

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        holder.bind(product[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
       val view = parent.inflate(R.layout.item_summary_dialog)
        return SummaryDialogAdapter.PlaceHolder(view)
    }

    class PlaceHolder(val view: View): RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(product: Product) = with(itemView){
            textName.text = product.name.capitalize()
            textQty.text = product.orderQuantity.toString() + " Pcs"
        }
    }
}