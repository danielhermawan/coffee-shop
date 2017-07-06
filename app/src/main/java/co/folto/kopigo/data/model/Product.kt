package co.folto.kopigo.data.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import co.folto.kopigo.ui.adapter.AdapterConstant
import co.folto.kopigo.ui.adapter.base.ViewType

@Entity
class Product(
    @PrimaryKey var id: Int = 0,
    var name: String = "",
    var price: Int = 0,
    var imageUrl: String = "",
    var quantity: Int = 0,
    @Ignore var orderQuantity: Int = 0,
    @Embedded(prefix = "category_") var category: ProductCategory = ProductCategory()
): ViewType, Parcelable {

    override fun getViewType(): Int = AdapterConstant.PRODUCT

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Product> = object : Parcelable.Creator<Product> {
            override fun createFromParcel(source: Parcel): Product = Product(source)
            override fun newArray(size: Int): Array<Product?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString(),
        source.readInt(),
        source.readString(),
        source.readInt(),
        source.readInt()
            //source.readProductCategory(),
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeInt(price)
        dest.writeString(imageUrl)
        dest.writeInt(quantity)
        //dest.writeTypedObject(category)
        dest.writeInt(orderQuantity)
    }
}