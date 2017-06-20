package co.folto.kopigo.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import co.folto.kopigo.KopigoApplication
import co.folto.kopigo.R
import co.folto.kopigo.ui.main.MainActivity
import co.folto.kopigo.util.showSnack
import co.folto.kopigo.util.startNewActivitySession
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Created by Daniel on 6/14/2017 for Kopigo project.
 */
class LoginActivity: AppCompatActivity(), LoginContract.View {

    @Inject
    lateinit var presenter: LoginPresenter

    companion object {
        @JvmStatic fun newIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        DaggerLoginPresenterComponent.builder()
                .dataComponent(KopigoApplication.dataComponent)
                .loginPresenterModule(LoginPresenterModule(this))
                .build()
                .inject(this)
        buttonSignin.setOnClickListener { attempLogin() }
        editPassword.setOnEditorActionListener(object: TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == R.id.signup_ime || actionId == EditorInfo.IME_NULL) {
                    attempLogin()
                    return true
                }
                return false
            }
        })
    }

    override fun onStart() {
        super.onStart()
        presenter.subscribe();
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
    }

    override fun loginSuccess() {
        toast(getString(R.string.login_login_success))
        startNewActivitySession(MainActivity.newIntent(this))
    }

    override fun loginError(message: String) {
        editUsername.setError(message)
        editUsername.requestFocus()
    }

    override fun showError(message: String) {
        parentView.showSnack(message)
    }

    override fun showLoading(active: Boolean) {
        if(active) {
            progress.visibility = View.VISIBLE
            buttonSignin.isEnabled = false
        }
        else {
            progress.visibility = View.GONE
            buttonSignin.isEnabled = true
        }
    }

    @Suppress("CAST_NEVER_SUCCEEDS")
    fun attempLogin() {
        var valid = true
        editPassword.setError(null)
        editUsername.setError(null)
        val username = editUsername.text
        val password = editPassword.text
        var focusView: View? = null
        if(password.isEmpty()){
            editPassword.setError(getString(R.string.login_error_password))
            focusView = editPassword
            valid = false
        }
        if(username.isEmpty()) {
            editUsername.setError(getString(R.string.login_error_username))
            focusView = editUsername
            valid = false
        }

        if(valid)
            presenter.login(username.toString(), password.toString())
        else
            focusView?.requestFocus()
    }
}