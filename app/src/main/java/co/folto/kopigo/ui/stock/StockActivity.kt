package co.folto.kopigo.ui.stock

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
import co.folto.kopigo.ui.stock.summaryStockDialog.SummaryDialog
import co.folto.kopigo.util.showSnack
import co.folto.kopigo.util.startNewActivitySession
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.content_order.*
import javax.inject.Inject

/**
 * Created by rudys on 7/5/2017.
 */

class StockActivity: BaseActivity(), StockContract.View {

   @Inject
    lateinit var presenter: StockPresenter

    companion object {

        @JvmStatic fun newIntent(context: Context) = Intent(context, StockActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        DaggerStockComponent.builder()
                .dataComponent(KopigoApplication.dataComponent)
                .stockModule(StockModule(this))
                .build()
                .inject(this)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val linearLayoutManager = LinearLayoutManager(this)
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        with(rvProducts){
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            rvProducts.addItemDecoration(divider)
        }
        presenter.subscribe()
    }

    override fun onDestroy() {
        super.onStop()
        presenter.unsubscribe()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.item_signout -> {
                presenter.logout()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showLoading(active: Boolean) {
        if (active) {
            progress.visibility = View.VISIBLE
        } else{
            progress.visibility = View.INVISIBLE
        }
    }

    override fun showMessage(message: String) {
        parentView.showSnack(message)
    }

    override fun navigateToLogin() {
        startNewActivitySession(LoginActivity.newIntent(this))
    }

    override fun showProduct(categories: MutableList<ProductCategory>, products: MutableList<Product>) {
        val orderStockAdapter = OrderStockAdapter(
                products,
                categories,
                { presenter.makeOrder() },
                { id, qty -> presenter.modifyProduct(id, qty) }
        )
        rvProducts.adapter = orderStockAdapter
    }


    override fun openSummaryDialog(products: MutableList<Product>) {
        SummaryDialog.newInstance(products as ArrayList<Product>).show(fragmentManager, "Summary Dialog")
    }
}