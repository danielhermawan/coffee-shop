package co.folto.kopigo.ui.summaryOrder

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.TextView
import co.folto.kopigo.R
import co.folto.kopigo.data.model.Product
import co.folto.kopigo.ui.summaryOrder.adapter.SummaryDialogAdapter
import co.folto.kopigo.util.thoundsandSeperator

/**
 * Created by Daniel on 6/22/2017 for Kopigo project.
 */
class SummaryDialog: DialogFragment() {

    lateinit var listener: OrderDialogListener;

    //todo: add title border bottom
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

    interface OrderDialogListener {
        fun onPrintClick()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try{
            listener = activity as OrderDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("Activity host must be implement OrderDialogListener")
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = activity.layoutInflater.inflate(R.layout.dialog_summary, null)
       // val listener = targetFragment as OrderDialogListener
        val textTotalPrice = view.findViewById(R.id.textTotalPrice) as TextView
        val rv = view.findViewById(R.id.rvSummary) as RecyclerView
        val buttonCancel = view.findViewById(R.id.buttonCancel) as Button
        val buttonPrint = view.findViewById(R.id.buttonPrint) as Button
        val products = arguments.getParcelableArrayList<Product>(EXTRA_PRODUCTS)
        val linearLayoutManager = LinearLayoutManager(activity)
        with(rv) {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
        }
        rv.adapter = SummaryDialogAdapter(products)
        textTotalPrice.setText("IDR " + products.sumBy { it.orderQuantity * it.price }.thoundsandSeperator())
        buttonCancel.setOnClickListener { dismiss() }
        buttonPrint.setOnClickListener {
            dismiss()
            listener.onPrintClick()
        }
        builder.setView(view)
        return builder.create()
    }
}