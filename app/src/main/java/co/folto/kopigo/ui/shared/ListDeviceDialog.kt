package co.folto.kopigo.ui.shared

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.*
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import co.folto.kopigo.R


/**
 * Created by Daniel on 8/11/2017 for Kopigo project.
 */
class ListDeviceDialog: DialogFragment() {

    lateinit var listener: DeviceChoosedListener
    private var rvNewDevices: RecyclerView? = null
    private var progress: ProgressBar? = null
    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (BluetoothDevice.ACTION_FOUND == action) {
                val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                (rvNewDevices?.adapter as DevicesAdapter).addData(device)
            }
            else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED == action) {
                progress?.visibility = View.GONE
            }
        }
    }


    interface DeviceChoosedListener {
        fun onDeviceChoose(address: String)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try{
            listener = activity as DeviceChoosedListener
        } catch (e: ClassCastException) {
            throw ClassCastException("Activity host must be implement DeviceChoosedListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val pairedDevices = bluetoothAdapter.bondedDevices.toMutableList()
        var filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        activity.registerReceiver(receiver, filter)
        filter = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        activity.registerReceiver(receiver, filter)

        val view = activity.layoutInflater.inflate(R.layout.dialog_list_device, null)
        val rvPairedDevices = view.findViewById(R.id.rvPairedDevices) as RecyclerView
        val buttonScan = view.findViewById(R.id.buttonScan)
        val textNodevice = view.findViewById(R.id.textNoDevice)
        if (pairedDevices.isEmpty())
            textNodevice.visibility = View.VISIBLE
        progress = view.findViewById(R.id.progress) as ProgressBar
        rvNewDevices = view.findViewById(R.id.rvNewDevices) as RecyclerView
        with(rvPairedDevices) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = DevicesAdapter({ chooseDevice(it) }, pairedDevices)
        }
        rvNewDevices?.setHasFixedSize(true)
        rvNewDevices?.layoutManager = LinearLayoutManager(activity)
        rvNewDevices?.adapter = DevicesAdapter({ chooseDevice(it) })
        buttonScan.setOnClickListener {
            progress?.visibility = View.VISIBLE
            bluetoothAdapter.startDiscovery()
        }

        builder.setView(view)
        return builder.create()
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        activity.unregisterReceiver(receiver)
        bluetoothAdapter.cancelDiscovery()
    }

    fun chooseDevice(address: String){
        dismiss()
        listener.onDeviceChoose(address)
        bluetoothAdapter.cancelDiscovery()
    }

}