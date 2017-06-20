package co.folto.kopigo.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import co.folto.kopigo.R
import co.folto.kopigo.data.model.Product
import co.folto.kopigo.util.inflate
import co.folto.kopigo.util.loadNetworkImage
import co.folto.kopigo.util.showToast
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_product.view.*


/**
 * Created by Daniel on 6/20/2017 for Kopigo project.
 */
class ProductAdapter: RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var products: MutableList<Product> = ArrayList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = parent.inflate(R.layout.item_product)
        return ProductViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ProductViewHolder?, position: Int) {
        holder?.bind(products[position])
    }

    override fun getItemCount(): Int = products.count()


    fun refreshData(products: MutableList<Product>) {
        this.products = products
        notifyDataSetChanged()
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
                    else
                        context.showToast("Stock barang ini sudah habis")
                }
            }
        }

    }
}