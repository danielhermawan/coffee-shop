package co.folto.kopigo.ui.summaryOrder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import co.folto.kopigo.KopigoApplication
import co.folto.kopigo.R
import co.folto.kopigo.data.model.Product
import co.folto.kopigo.ui.summaryOrder.adapter.PaymentAdapter
import co.folto.kopigo.ui.summaryOrder.adapter.SummaryAdapter
import co.folto.kopigo.util.obtainDrawable
import co.folto.kopigo.util.showSnack
import kotlinx.android.synthetic.main.activity_summary.*
import kotlinx.android.synthetic.main.content_summary.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Daniel on 6/20/2017 for Kopigo project.
 */
class SummaryActivity: AppCompatActivity(), SummaryContract.View {

    @Inject
    lateinit var presenter: SummaryPresenter

    companion object {
        val EXTRA_PRODUCTS = "EXTRA PRODUCTS"

        @JvmStatic fun newIntent(context: Context, products: ArrayList<Product>): Intent {
            val i = Intent(context, SummaryActivity::class.java)
            i.putExtra(EXTRA_PRODUCTS, products)
            return i
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)
        DaggerSummaryComponent.builder()
            .dataComponent(KopigoApplication.dataComponent)
            .summaryModule(SummaryModule(this, intent.getParcelableArrayListExtra(EXTRA_PRODUCTS)))
            .build()
            .inject(this)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val linearLayoutManager = LinearLayoutManager(this)
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        divider.setDrawable(resources.obtainDrawable(R.drawable.shape_brown_divider, this))
        with(rvSummary) {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            addItemDecoration(divider)
        }
        val linearPayment = LinearLayoutManager(this)
        val dividerPayment = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        dividerPayment.setDrawable(resources.obtainDrawable(R.drawable.shape_brown_divider, this))
        with(rvPayment) {
            setHasFixedSize(true)
            layoutManager = linearPayment
            addItemDecoration(dividerPayment)
        }
        presenter.subscribe()
        buttonCheckout.setOnClickListener { presenter.checkout() }
    }

    override fun onDestroy() {
        super.onStop()
        presenter.unsubscribe()
    }

    override fun showLoading(active: Boolean) {
        if(active)
            progress.visibility = View.VISIBLE
        else
            progress.visibility = View.GONE
    }

    override fun showSnack(message: String) {
        parentView.showSnack(message)
    }

    override fun showSummary(products: MutableList<Product>) {
        rvSummary.adapter = SummaryAdapter(products) { id, qty -> presenter.modifyProduct(id, qty)}
        rvPayment.adapter = PaymentAdapter(products)
    }

    override fun showModifiedProduct(products: MutableList<Product>) {
        Timber.d(products.toString())
        (rvPayment.adapter as PaymentAdapter).refreshData(products)
    }

    override fun openSummaryDialog(products: MutableList<Product>) {
        SummaryDialog.newInstance(products as ArrayList<Product>).show(fragmentManager, "Summary Dialog")
}

    override fun navigateToHome() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun navigateToAddItem() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}