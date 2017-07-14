package co.folto.kopigo.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import co.folto.kopigo.data.model.Product
import io.reactivex.Flowable

/**
 * Created by Daniel on 7/5/2017 for Kopigo project.
 */
@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg product: Product)

    @Query("SELECT * FROM product")
    fun getAll(): Flowable<List<Product>>

    @Query("DELETE FROM product")
    fun clear()
}