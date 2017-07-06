package co.folto.kopigo.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import co.folto.kopigo.data.model.Product
import co.folto.kopigo.data.model.ProductCategory

/**
 * Created by Daniel on 7/5/2017 for Kopigo project.
 */
@Database(entities = arrayOf(Product::class, ProductCategory::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
}