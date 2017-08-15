package co.folto.kopigo.ui.summaryOrder

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import co.folto.kopigo.KopigoApplication
import co.folto.kopigo.R
import co.folto.kopigo.data.model.Product
import co.folto.kopigo.ui.main.MainActivity
import co.folto.kopigo.ui.shared.ListDeviceDialog
import co.folto.kopigo.ui.summaryOrder.adapter.PaymentAdapter
import co.folto.kopigo.ui.summaryOrder.adapter.SummaryAdapter
import co.folto.kopigo.util.*
import co.folto.kopigo.util.bluetooth.Command
import co.folto.kopigo.util.bluetooth.PrinterCommand
import kotlinx.android.synthetic.main.activity_summary.*
import kotlinx.android.synthetic.main.content_summary.*
import org.jetbrains.anko.toast
import timber.log.Timber
import java.io.UnsupportedEncodingException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by Daniel on 6/20/2017 for Kopigo project.
 */
class SummaryActivity: AppCompatActivity(),
        SummaryContract.View, SummaryDialog.OrderDialogListener, ListDeviceDialog.DeviceChoosedListener {

    private val REQ_ENAB_BT = 1

    @Inject
    lateinit var presenter: SummaryPresenter
    private var bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            handlerPrinter(msg)
        }
    }
    private val bService = BluetoothService(this, handler)

    companion object {
        val EXTRA_PRODUCTS = "EXTRA PRODUCTS"

        @JvmStatic fun newIntent(context: Context, products: ArrayList<Product>): Intent {
            val i = Intent(context, SummaryActivity::class.java)
            i.putExtra(EXTRA_PRODUCTS, products)
            return i
        }
    }
    //todo: add button
    //todo: print error handling
    //todo: Bikin order dari api dulu baru print
    @Suppress("CAST_NEVER_SUCCEEDS")
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
        rvSummary.setNestedScrollingEnabled(false)
        val linearPayment = LinearLayoutManager(this)
        val dividerPayment = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        dividerPayment.setDrawable(resources.obtainDrawable(R.drawable.shape_brown_divider, this))
        with(rvPayment) {
            setHasFixedSize(true)
            layoutManager = linearPayment
            addItemDecoration(dividerPayment)
        }
        rvPayment.setNestedScrollingEnabled(false)
        buttonCheckout.setOnClickListener {
            if(editPayment.text.toString().isBlank())
                showSnack("Jumlah yang dibayar harus di isi")
            else
                presenter.checkout(editPayment.text.toString().toInt())
        }

        if (bluetoothAdapter == null) {
            toast("Bluetooth is not available")
            finish()
        }
        presenter.subscribe()
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onDestroy() {
        super.onDestroy()
        bService.stop()
        presenter.unsubscribe()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_ENAB_BT -> {
                if (resultCode == Activity.RESULT_OK) {
                    ListDeviceDialog().show(fragmentManager, "Device Dialog")
                }
                else {
                    toast("Bluetooth must be enable if you want to create order")
                }
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
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

    override fun disableCheckout(active: Boolean) {
        buttonCheckout.setEnabled(active)
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
        startNewActivitySession(MainActivity.newIntent(this))
    }

    override fun navigateToAddItem() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPrintClick() {
        if(!(bluetoothAdapter?.isEnabled ?: true)) {
            val enableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableIntent, REQ_ENAB_BT)
        }
        else {
            ListDeviceDialog().show(fragmentManager, "Device Dialog")
        }
    }

    override fun onDeviceChoose(address: String) {
        bService.start()
        if (BluetoothAdapter.checkBluetoothAddress(address)) {
            val device = bluetoothAdapter!!.getRemoteDevice(address) // Don't do this actually
            bService.connect(device)
        }
    }

    @Suppress("CAST_NEVER_SUCCEEDS")
    @SuppressLint("SimpleDateFormat")
    override fun printReceipt(products: MutableList<Product>, id: Int) {
        toast("Order has been created. Print started...")
        val formatter = SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss ")
        val curDate = Date(System.currentTimeMillis())//获取当前时间
        val str = formatter.format(curDate)
        val date = str + "\n\n\n\n\n"
        val total = products.sumBy { it.orderQuantity * it.price }
        val totalString = "\nTotal:                ${total.thoundsandSeperator()}\n"
        val payment = "Tunai:                ${editPayment.text.toString().toInt().thoundsandSeperator()}\n"
        val kembali = "Kembali:              ${Math.abs(total - (editPayment.text.toString().toInt())).thoundsandSeperator()}\n"
        val header = "Nama       Qty  Harga   Total"
        var productString = "Item:\n"
        products.forEach {
            productString += "${it.name} ${it.name.space(10)} " +
                    "${it.orderQuantity} ${it.orderQuantity.toString().space(1)} " +
                    "${it.price.thoundsandSeperator()} ${it.price.toString().space(2)} " +
                    "${(it.price * it.orderQuantity).thoundsandSeperator()}\n"
        }
        SendDataByte(Command.ESC_Align)
        Command.GS_ExclamationMark[2] = 0x11
        SendDataByte(Command.GS_ExclamationMark)// Make next font big
        Command.ESC_Align[2] = 0x01 //Make next data center
        SendDataByte(Command.ESC_Align)
        SendDataByte("Kopigo\n".toByteArray(charset("GBK")))
        Command.ESC_Align[2] = 0x00
        SendDataByte(Command.ESC_Align)
        Command.GS_ExclamationMark[2] = 0x00
        SendDataByte(Command.GS_ExclamationMark) // Make next fotn normal
        SendDataByte("Number:  ${id}\n".toByteArray(charset("GBK")))
        SendDataByte(productString.toByteArray(charset("GBK")))
        //SendDataByte("Name    Quantity    Trice  Total\nShoes   10.00       899     8990\nBall    10.00       1599    15990\n".toByteArray(charset("GBK")))
        //SendDataByte("total：                16889.00\npayment：              17000.00\nKeep the change：      111.00\n".toByteArray(charset("GBK")))
        SendDataByte(totalString.toByteArray(charset("GBK")))
        SendDataByte(payment.toByteArray(charset("GBK")))
        SendDataByte(kembali.toByteArray(charset("GBK")))
        SendDataByte("================================\n".toByteArray(charset("GBK")))
        Command.ESC_Align[2] = 0x01 //Make next data center
        SendDataByte(Command.ESC_Align)
        Command.GS_ExclamationMark[2] = 0x11
        SendDataByte(Command.GS_ExclamationMark)
        SendDataByte("Welcome again!\n".toByteArray(charset("GBK")))
        Command.ESC_Align[2] = 0x00
        SendDataByte(Command.ESC_Align)
        Command.GS_ExclamationMark[2] = 0x00
        SendDataByte(Command.GS_ExclamationMark)

        Command.ESC_Align[2] = 0x02
        SendDataByte(Command.ESC_Align)
        SendDataString(date)
        SendDataByte(PrinterCommand.POS_Set_PrtAndFeedPaper(48))
        SendDataByte(Command.GS_V_m_n)

        showLoading(false)
        navigateToHome()
    }

    private fun handlerPrinter(msg: Message) {
        showLoading(true)
        disableCheckout(false)
        when (msg.what) {
            BluetoothService.MESSAGE_STATE_CHANGE -> {
                when (msg.arg1) {
                    BluetoothService.STATE_CONNECTED -> {
                        presenter.createOrder()
                    }
                    BluetoothService.STATE_LISTEN -> {
                        toast("connecting to bluetooth device…")
                    }
                }
            }
            BluetoothService.MESSAGE_TOAST -> {
                toast(msg.data.getString(BluetoothService.TOAST))
            }
            BluetoothService.MESSAGE_UNABLE_CONNECT -> {
                toast("Unable to connect device, restart the bluetooth device and try again!")
                showLoading(false)
                disableCheckout(false)
            }
            BluetoothService.MESSAGE_CONNECTION_LOST -> {
                showLoading(false)
                disableCheckout(false)
            }
        }
    }

    private fun SendDataByte(data: ByteArray) {
        if (bService.getState() != BluetoothService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show()
            return
        }
        bService.write(data)
    }

    private fun SendDataString(data: String) {
        if (bService.getState() != BluetoothService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT)
                    .show()
            return
        }
        if (data.isNotEmpty()) {
            try {
                bService.write(data.toByteArray(charset("GBK")))
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

        }
    }
}