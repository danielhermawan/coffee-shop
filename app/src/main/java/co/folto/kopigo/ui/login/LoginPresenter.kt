package co.folto.kopigo.ui.login

import co.folto.kopigo.data.UserRepository
import co.folto.kopigo.util.start
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Daniel on 6/14/2017 for Kopigo project.
 */

class LoginPresenter @Inject constructor(
        private val userRepository: UserRepository,
        private val view: LoginContract.View
) : LoginContract.Presenter {

    private var composite = CompositeDisposable()

    override fun subscribe() {

    }

    override fun unsubscribe() {
        composite.dispose()
    }

    override fun login(username: String, password: String) {
        composite.clear()
        view.showLoading(true)
        val request = userRepository.login(username, password)
            .start()
            .subscribeBy(
                onSuccess = {
                    view.showLoading(false)
                    view.loginSuccess()
                },
                onError = {
                    Timber.e(it)
                    view.showLoading(false)
                    if(it is HttpException)
                        view.loginError("Username or password is wrong")
                    else
                        view.showError("There is some kind of network trouble, please try again")
                }
            )
        composite.add(request)
    }
}