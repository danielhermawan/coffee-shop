package co.folto.kopigo.data.local

import co.folto.kopigo.data.model.Product
import co.folto.kopigo.data.model.ProductCategory
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Daniel on 7/5/2017 for Kopigo project.
 */
@Singleton
class DatabaseService @Inject constructor(private val db: AppDatabase){
    fun saveCategories(categories: List<ProductCategory>): Completable
        = Completable.create {
            db.categoryDao().insertAll(*categories.toTypedArray())
            it.onComplete()
        }


    fun saveProducts(products: List<Product>): Completable {
        db.productDao().clear()
        db.productDao().insertAll(*products.toTypedArray())
        return Completable.complete()
    }

    fun clearProducts(): Completable
        = Completable.create {
            db.productDao().clear()
            it.onComplete()
        }

    fun getProducts(): Flowable<List<Product>> = db.productDao().getAll()
}