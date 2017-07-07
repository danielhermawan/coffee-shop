package co.folto.kopigo.ui.order

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import co.folto.kopigo.KopigoApplication
import co.folto.kopigo.R
import co.folto.kopigo.data.model.Product
import co.folto.kopigo.data.model.ProductCategory
import co.folto.kopigo.ui.base.BaseActivity
import co.folto.kopigo.ui.login.LoginActivity
import co.folto.kopigo.ui.summaryOrder.SummaryActivity
import co.folto.kopigo.util.showSnack
import co.folto.kopigo.util.startNewActivitySession
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.content_order.*
import javax.inject.Inject

/**
 * Created by Daniel on 6/19/2017 for Kopigo project.
 */
class OrderActivity: BaseActivity(), OrderContract.View {

    //todo: save category and product to local database for cache
    //todo: Change nested to using other library like https://github.com/DevAhamed/MultiViewAdapter
    //todo: persist presenter
    //todo: add order and close status
    //todo: make all data in presenter become immutable

    @Inject
    lateinit var presenter: OrderPresenter

    companion object {
        @JvmStatic fun newIntent(context: Context) = Intent(context, OrderActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        DaggerOrderComponent.builder()
                .dataComponent(KopigoApplication.dataComponent)
                .orderModule(OrderModule(this))
                .build()
                .inject(this)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val linearLayoutManager = LinearLayoutManager(this)
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        //divider.setDrawable(resources.obtainDrawable(R.drawable.shape_brown_divider, this))
        with(rvProducts) {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            rvProducts.addItemDecoration(divider)
        }
        rvProducts.setNestedScrollingEnabled(false)
        presenter.subscribe();
    }

    /*override fun onStart() {
        super.onStart()
        presenter.subscribe();
    }*/

    override fun onDestroy() {
        super.onStop()
        presenter.unsubscribe()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.item_signout  -> {
                presenter.logout()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun showLoading(active: Boolean) {
        if(active)
            progress.visibility = View.VISIBLE
        else
            progress.visibility = View.GONE
    }

    override fun showMessage(message: String) {
        parentView.showSnack(message)
    }

    override fun navigateToLogin() {
        startNewActivitySession(LoginActivity.newIntent(this))
    }

    override fun showProduct(categories: MutableList<ProductCategory>, products: MutableList<Product>) {
        val orderAdapter = OrderAdapter (
            products,
            categories,
            { presenter.makeOrder() },
            { id, qty -> presenter.modifyProduct(id, qty) }
        )
        rvProducts.adapter = orderAdapter
    }

    override fun navigateToSummary(products: List<Product>) {
        startActivity(SummaryActivity.newIntent(this, products as ArrayList<Product>))
    }
}