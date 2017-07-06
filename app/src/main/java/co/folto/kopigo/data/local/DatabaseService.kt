package co.folto.kopigo.data.local

import co.folto.kopigo.data.model.Product
import co.folto.kopigo.data.model.ProductCategory
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Daniel on 7/5/2017 for Kopigo project.
 */
@Singleton
class DatabaseService @Inject constructor(private val db: AppDatabase){
    fun saveCategories(categories: List<ProductCategory>): Completable {
        db.categoryDao().insertAll(*categories.toTypedArray())
        return Completable.complete()
    }

    fun saveProducts(products: List<Product>): Completable {
        db.productDao().insertAll(*products.toTypedArray())
        return Completable.complete()
    }
}