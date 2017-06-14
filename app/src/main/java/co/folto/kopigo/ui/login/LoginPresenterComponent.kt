package co.folto.kopigo.ui.login

import co.folto.kopigo.dagger.Component.DataComponent
import co.folto.kopigo.dagger.annotation.ActivityScoped
import dagger.Component

/**
 * Created by Daniel on 6/14/2017 for Kopigo project.
 */
@ActivityScoped
@Component(dependencies = arrayOf(DataComponent::class), modules = arrayOf(LoginPresenterModule::class))
interface LoginPresenterComponent {
    fun inject(loginActivity: LoginActivity)
}