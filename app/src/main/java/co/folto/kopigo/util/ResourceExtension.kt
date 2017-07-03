package co.folto.kopigo.util

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import co.folto.kopigo.data.model.Product
import org.joda.time.DateTime

/**
 * Created by Daniel on 6/12/2017 for Kopigo project.
 */

fun Resources.obtainDrawable(id: Int, context: Context): Drawable =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            this.getDrawable(id, context.getTheme())
        else
            this.getDrawable(id)

fun String.formatDate(format: String)
        = DateTime(this).toString(format)

fun Int.thoundsandSeperator(seperator: Char = '.') = String.format("%,d", this).replace(',', seperator)

fun MutableList<Product>.replace(product: Product) {
    val index = this.indexOfFirst { it.id == product.id }
    this.set(index, product)
}