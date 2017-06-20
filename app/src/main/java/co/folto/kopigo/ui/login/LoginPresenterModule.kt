package co.folto.kopigo.ui.login

import dagger.Module
import dagger.Provides

/**
 * Created by Daniel on 6/14/2017 for Kopigo project.
 */
@Module
class LoginPresenterModule(val view: LoginContract.View) {

    @Provides
    fun provideView(): LoginContract.View = view
}