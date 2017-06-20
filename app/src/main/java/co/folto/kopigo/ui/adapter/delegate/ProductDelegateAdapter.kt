package co.folto.kopigo.ui.adapter.delegate

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import co.folto.kopigo.R
import co.folto.kopigo.data.model.Product
import co.folto.kopigo.ui.adapter.base.ViewType
import co.folto.kopigo.ui.adapter.base.ViewTypeDelegateAdapter
import co.folto.kopigo.util.inflate
import co.folto.kopigo.util.loadNetworkImage
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_product.view.*

/**
 * Created by Daniel on 6/19/2017 for Kopigo project.
 */
class ProductDelegateAdapter(): ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
        = ProductViewHolder(parent.inflate(R.layout.item_product), parent.context)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as ProductViewHolder
        holder.bind(item as Product)
    }

    class ProductViewHolder(val view: View, val context: Context): RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(product: Product) {
            with(itemView) {
                imageProduct.loadNetworkImage(context, product.imageUrl, options = RequestOptions().circleCrop())
                textProduct.text = product.name.capitalize()
                textPrice.text = "IDR ${product.price}"
                editQuantity.text = "" + product.orderQuantity
                buttonMinus.setOnClickListener {
                    if(product.orderQuantity > 0 ) {
                        product.orderQuantity -= 1
                        editQuantity.setText("" + product.orderQuantity)
                    }
                }
                buttonPlus.setOnClickListener {
                    if(product.orderQuantity < product.quantity) {
                        product.orderQuantity += 1
                       editQuantity.setText("" + product.orderQuantity)
                    }
                }
            }
        }

    }

}