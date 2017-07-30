package co.folto.kopigo.data.remote

import co.folto.kopigo.data.model.LoginResponse
import co.folto.kopigo.data.model.OrderRequest
import co.folto.kopigo.data.model.Product
import co.folto.kopigo.data.model.ProductCategory
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.*

/**
 * Created by Daniel on 6/12/2017 for Kopigo project.
 */
interface KopigoService {
    @FormUrlEncoded
    @POST("login")
    fun login(@Field("username") username: String, @Field("password") password: String): Single<LoginResponse>

    @POST("logout")
    fun logout(): Completable

    @GET("category")
    fun getCategories(): Flowable<List<ProductCategory>>

    @GET("me/product")
    fun getUserProduct(): Flowable<List<Product>>

    @POST("order")
    fun createOrder(@Body products: OrderRequest): Completable

    @POST("request")
    fun createRequest(@Body products: OrderRequest): Completable
}