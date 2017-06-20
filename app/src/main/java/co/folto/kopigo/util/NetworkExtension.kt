package co.folto.kopigo.util

import android.content.Context
import android.widget.ImageView
import co.folto.kopigo.R
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Daniel on 6/12/2017 for Kopigo project.
 */

fun <T> Flowable<T>.start()
        = this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.start()
        = this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())


fun Completable.start()
        = this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun ImageView.loadNetworkImage(context: Context,
                               url: String,
                               placeholder: Int = R.drawable.bitmap_image_loading,
                               errorImage: Int = R.drawable.bitmap_image_unavailable,
                               options: RequestOptions = RequestOptions())
        = GlideApp.with(context)
            .load(url)
            .placeholder(placeholder)
            .error(errorImage)
            .apply(options)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            /*.transition(withCrossFade())*/
            .into(this)