package co.folto.kopigo.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import co.folto.kopigo.data.model.ProductCategory
import io.reactivex.Flowable

/**
 * Created by Daniel on 7/5/2017 for Kopigo project.
 */
@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg category: ProductCategory)

    @Query("SELECT * FROM ProductCategory")
    fun getAll(): Flowable<List<ProductCategory>>
}