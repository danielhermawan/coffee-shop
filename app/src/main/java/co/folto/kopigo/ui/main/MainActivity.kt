package co.folto.kopigo.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import co.folto.kopigo.KopigoApplication
import co.folto.kopigo.R
import co.folto.kopigo.ui.login.LoginActivity
import co.folto.kopigo.ui.order.OrderActivity
import co.folto.kopigo.util.startNewActivitySession
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.toast
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainContract.Presenter

    companion object {
        @JvmStatic fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        DaggerMainComponent.builder()
                .dataComponent(KopigoApplication.dataComponent)
                .mainModule(MainModule(this))
                .build()
                .inject(this)
        viewOrder.setOnClickListener {
            startActivity(OrderActivity.newIntent(this))
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.subscribe();
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_signout  -> {
                presenter.logout()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun navigateToLogin() {
        startNewActivitySession(LoginActivity.newIntent(this))
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun showLoading(active: Boolean) {
        if(active)
            progress.visibility = View.VISIBLE
        else
            progress.visibility = View.GONE
    }


}
