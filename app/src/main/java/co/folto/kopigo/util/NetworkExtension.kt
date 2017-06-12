package co.folto.kopigo.util

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Daniel on 6/12/2017 for Kopigo project.
 */

fun <T> Flowable<T>.start()
        = this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun Completable.start()
        = this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
