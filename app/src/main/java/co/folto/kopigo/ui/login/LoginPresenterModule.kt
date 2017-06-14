package co.folto.kopigo.ui.login

import co.folto.kopigo.data.UserRepository
import dagger.Module
import dagger.Provides

/**
 * Created by Daniel on 6/14/2017 for Kopigo project.
 */
@Module
class LoginPresenterModule(val view: LoginContract.View) {

    @Provides
    fun providePresenter(userRepository: UserRepository): LoginContract.Presenter
            = LoginPresenter(userRepository, view)
}