package co.folto.kopigo.ui.login

import co.folto.kopigo.ui.base.BasePresenter
import co.folto.kopigo.ui.base.BaseView

/**
 * Created by Daniel on 6/14/2017 for Kopigo project.
 */
interface LoginContract {
    interface Presenter: BasePresenter {
        fun login(username: String, password: String)
    }

    interface View: BaseView {
        fun loginSuccess()
        fun loginError(message: String)
        fun showError(message: String)
        fun showLoading(active: Boolean)
    }
}