package co.folto.kopigo.data.contract

import co.folto.kopigo.data.model.LoginResponse
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by Daniel on 6/12/2017 for Kopigo project.
 */
interface UserContract {
    fun login(username: String, password: String): Single<LoginResponse>
    fun logout(): Completable
    fun checkLoginStatus(): Boolean
}