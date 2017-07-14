package co.folto.kopigo.data.contract

import co.folto.kopigo.data.model.LoginResponse
import co.folto.kopigo.data.model.Product
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Daniel on 6/12/2017 for Kopigo project.
 */
interface UserContract {
    fun login(username: String, password: String): Single<LoginResponse>
    fun logout(): Completable
    fun checkLoginStatus(): Boolean
    fun clearData(): Completable

    fun getUserProducts(): Flowable<List<Product>>
}