package co.folto.kopigo.data.remote

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Daniel on 6/14/2017 for Kopigo project.
 */
class GeneralInterceptor: Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
                .addHeader("Accept", "application/json")
                .build()
        //todo: make general network error such as token expire
        return chain.proceed(newRequest)
    }
}