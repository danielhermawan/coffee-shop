package co.folto.kopigo.ui.base

import android.support.v4.app.Fragment

/**
 * Created by Daniel on 6/7/2017 for GitFInder project.
 */
open class BaseFragment<T>: Fragment(), BaseView<T> where T: BasePresenter {

    lateinit private var presenter: T

    override fun attachPresenter(presenter: T) {
        this.presenter = presenter
    }
}