package co.folto.kopigo.ui.stock.summaryStockDialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import co.folto.kopigo.R
import co.folto.kopigo.data.model.Product

/**
 * Created by rudys on 7/11/2017.
 */

class SummaryDialog: DialogFragment() {

    lateinit var listener: StockDialogListener;

    companion object {
        val EXTRA_PRODUCTS = "EXTRA PRODUCTS"

        @JvmStatic fun newInstance(products: ArrayList<Product>): SummaryDialog {
            val args = Bundle()
            args.putParcelableArrayList(EXTRA_PRODUCTS, products)
            val dialog = SummaryDialog()
            dialog.arguments = args
            return dialog
        }
    }

    interface StockDialogListener {
        fun onOkClick(products: ArrayList<Product>)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try{
            listener = activity as StockDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("Activity host must be implement StockDialogListener")
        }
    }

    @SuppressLint("setTextI18n")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = activity.layoutInflater.inflate(R.layout.dialog_summary_stock, null)
        val rv = view.findViewById(R.id.rvSummary) as RecyclerView
        val buttonCancel = view.findViewById(R.id.buttonCancel) as Button
        val buttonOrder = view.findViewById(R.id.buttonOrder) as Button
        val products = arguments.getParcelableArrayList<Product>(EXTRA_PRODUCTS)
        val linearLayoutManager = LinearLayoutManager(activity)
        with(rv) {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
        }
        rv.adapter = SummaryDialogAdapter(products)
        buttonCancel.setOnClickListener { dismiss() }
        buttonOrder.setOnClickListener {
            dismiss()
            listener.onOkClick(products)
        }
        builder.setView(view)
        return builder.create()
    }



}