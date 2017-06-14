package co.folto.kopigo.ui.main

import co.folto.kopigo.ui.base.BasePresenter
import co.folto.kopigo.ui.base.BaseView

/**
 * Created by Daniel on 6/14/2017 for Kopigo project.
 */
interface MainContract {
    interface Presenter : BasePresenter {
        fun checkLoginStatus()
        fun logout()
    }

    interface View : BaseView {
        fun navigateToLogin()
        fun showMessage(message: String)
    }
}