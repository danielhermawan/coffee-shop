package co.folto.kopigo.dagger.module

import android.app.Application
import co.folto.kopigo.BuildConfig
import co.folto.kopigo.data.local.PreferenceHelper
import co.folto.kopigo.data.remote.GeneralInterceptor
import co.folto.kopigo.data.remote.KopigoService
import co.folto.kopigo.data.remote.TokenInterceptor
import co.folto.kopigo.util.Constant
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

/**
 * Created by Daniel on 6/12/2017 for Kopigo project.
 */

@Module
class RemoteDataModule {

    @Provides
    @Singleton
    fun provideOkhttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideGson(): Gson
            = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()


    @Provides
    @Singleton
    fun provideOkhttpClient(cache: Cache, preferenceHelper: PreferenceHelper): OkHttpClient {
        val client = OkHttpClient().newBuilder().cache(cache)
        client.addInterceptor(GeneralInterceptor())
        if (BuildConfig.DEBUG){
            val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Timber.tag("OkHttp").d(message)
                }
            })
            client.addInterceptor(logging).addNetworkInterceptor(StethoInterceptor())
                    .addInterceptor(TokenInterceptor(preferenceHelper))
        }
        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl(Constant.API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideGitService(retrofit: Retrofit): KopigoService = retrofit.create(KopigoService::class.java)
}