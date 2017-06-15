package co.folto.kopigo.data.remote

import co.folto.kopigo.data.local.PreferenceHelper
import co.folto.kopigo.util.Constant
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by Daniel on 8/24/2016 for Bemaid project.
 */
class TokenInterceptor(private val mPreference: PreferenceHelper) : Interceptor {

    private val exception = arrayOf("login")

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = mPreference.getAccessToken()
        val url = originalRequest.url().toString()
        val path = url.split(Constant.API_URL).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
        val newRequest = originalRequest.newBuilder()
        if (!exception.contains(path)) {
            newRequest.addHeader("Authorization", "Bearer " + token)
        }
        return chain.proceed(newRequest.build())
    }

}