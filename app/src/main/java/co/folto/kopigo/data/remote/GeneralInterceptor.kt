package co.folto.kopigo.data.remote

import co.folto.kopigo.data.BusEvent
import com.squareup.otto.Bus
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Daniel on 6/14/2017 for Kopigo project.
 */
@Singleton
class GeneralInterceptor @Inject constructor(
    private val bus: Bus
): Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
                .addHeader("Accept", "application/json")
                .build()
        val response = chain.proceed(newRequest)
        if(response.code() == 401)
            bus.post(BusEvent.AuthenticationError())
        return response
    }
}