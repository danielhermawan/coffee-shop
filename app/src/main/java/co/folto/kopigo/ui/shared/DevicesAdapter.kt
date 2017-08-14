package co.folto.kopigo.ui.shared

import android.bluetooth.BluetoothDevice
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import co.folto.kopigo.R
import co.folto.kopigo.util.inflate
import kotlinx.android.synthetic.main.item_device.view.*

/**
 * Created by Daniel on 8/11/2017 for Kopigo project.
 */
class DevicesAdapter(val clickListener: (String) -> Unit, var devices: MutableList<BluetoothDevice> = ArrayList())
    : RecyclerView.Adapter<DevicesAdapter.PlaceHolder>() {

    override fun onBindViewHolder(holder: DevicesAdapter.PlaceHolder, position: Int) {
        holder.bind(devices[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevicesAdapter.PlaceHolder {
        val view = parent.inflate(R.layout.item_device)
        return DevicesAdapter.PlaceHolder(view, clickListener)
    }

    override fun getItemCount(): Int = devices.size

    fun refreshData(devices: MutableList<BluetoothDevice>) {
        this.devices = devices
        notifyDataSetChanged()
    }

    fun addData(device: BluetoothDevice) {
        if(this.devices.find { it.address == device.address } == null){
            devices.add(device)
            notifyDataSetChanged()
        }
    }

    class PlaceHolder(val view: View, val clickListener: (String) -> Unit): RecyclerView.ViewHolder(view) {
        fun bind(device: BluetoothDevice)
            = with(itemView) {
                textName.text = device.name
                textAddres.text = device.address
                viewParent.setOnClickListener {
                    clickListener(device.address)
                }
            }

    }
}