package co.folto.kopigo.data.remote

import co.folto.kopigo.data.model.LoginResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Daniel on 6/12/2017 for Kopigo project.
 */
interface KopigoService {
    @FormUrlEncoded
    @POST("login")
    fun login(@Field("username") username: String, @Field("password") password: String): Single<LoginResponse>

    @POST("logout")
    fun logout(): Completable
}