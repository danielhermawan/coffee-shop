package co.folto.kopigo.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import co.folto.kopigo.KopigoApplication
import co.folto.kopigo.R
import co.folto.kopigo.data.UserRepository
import co.folto.kopigo.ui.login.LoginActivity
import co.folto.kopigo.util.startNewActivitySession
import org.jetbrains.anko.toast
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject
    lateinit var userRepository: UserRepository
    lateinit var presenter: MainContract.Presenter

    companion object {
        @JvmStatic fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        KopigoApplication.dataComponent.inject(this)
        presenter = MainPresenter(userRepository, this)
    }

    override fun onStart() {
        super.onStart()
        presenter.subscribe();
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
    }

    override fun navigateToLogin() {
        startNewActivitySession(LoginActivity.newIntent(this))
    }

    override fun showMessage(message: String) {
        toast(message)
    }


}
