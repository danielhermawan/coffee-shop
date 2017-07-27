package co.folto.kopigo.ui.stock

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import co.folto.kopigo.R
import co.folto.kopigo.data.model.Product
import co.folto.kopigo.util.inflate
import co.folto.kopigo.util.loadNetworkImage
import co.folto.kopigo.util.thoundsandSeperator
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_product_stock.view.*

/**
 * Created by rudys on 7/7/2017.
 */

class ProductStockAdapter(val onModified: (Int, Int) -> Unit): RecyclerView.Adapter<ProductStockAdapter.ProductViewHolder>(){

    private var products: MutableList<Product> = ArrayList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = parent.inflate(R.layout.item_product_stock)
        return ProductViewHolder(view, parent.context, onModified)
    }

    override fun onBindViewHolder(holder: ProductViewHolder?, position: Int) {
        holder?.bind(products[position])
    }

    override fun getItemCount(): Int = products.count()


    fun refreshData(products: MutableList<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    class ProductViewHolder(val view: View, val context: Context, val onModified: (Int, Int) -> Unit):
            RecyclerView.ViewHolder(view) {

            @SuppressLint("SetTextI18n")
            fun bind(product: Product){
                with(itemView){
                    imageProductStock.loadNetworkImage(context, product.imageUrl, options = RequestOptions().circleCrop())
                    textProductStock.text = product.name.capitalize()
                    textPriceStock.text = "IDR ${product.price.thoundsandSeperator()}"
                    textStock.text = "STOCK: " + product.quantity + " items left"
                    editQuantityStock.text = "" + product.orderQuantity
                    buttonMinusStock.setOnClickListener {
                        onModified(product.id, -1)
                        editQuantityStock.setText("" + product.orderQuantity)
                    }
                    buttonPlusStock.setOnClickListener {
                        onModified(product.id, +1)
                        editQuantityStock.setText("" + product.orderQuantity)
                    }
                }
            }

    }

}