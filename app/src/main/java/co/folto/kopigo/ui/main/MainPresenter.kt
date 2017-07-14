package co.folto.kopigo.ui.main

import co.folto.kopigo.data.UserRepository
import co.folto.kopigo.util.start
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

/**
 * Created by Daniel on 6/14/2017 for Kopigo project.
 */
class MainPresenter (
        private val userRepository: UserRepository,
        private val view: MainContract.View
): MainContract.Presenter {

    private var composite = CompositeDisposable()

    override fun subscribe() {
        checkLoginStatus()
    }

    override fun unsubscribe() {
        composite.dispose()
    }

    override fun checkLoginStatus() {
        //if user is not login yet
        if(!userRepository.checkLoginStatus())
            view.navigateToLogin()
    }

    override fun logout() {
        composite.clear()
        view.showLoading(true)
        val request = userRepository.logout()
            .start()
            .subscribe(
                {
                    view.showMessage("Successfully logout from kopigo")
                    view.navigateToLogin()
                },
                {
                    Timber.e(it)
                    view.showMessage("There is some kind of network trouble")
                    view.showLoading(false)
                }
            )
        composite.add(request)
    }
}