package co.folto.kopigo.util.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.Handler
import timber.log.Timber
import java.io.IOException
import java.util.UUID.fromString


/**
 * Created by Daniel on 8/10/2017 for Kopigo project.
 */
class BluetoothService(val context: Context, val handler: Handler) {

    companion object {
        val STATE_NONE = 0
        val STATE_LISTEN = 1
        val STATE_CONNECTING = 2
        val STATE_CONNECTED = 3

        val MESSAGE_STATE_CHANGE = 1
        val MESSAGE_READ = 2
        val MESSAGE_WRITE = 3
        val MESSAGE_DEVICE_NAME = 4
        val MESSAGE_TOAST = 5
        val MESSAGE_CONNECTION_LOST = 6
        val MESSAGE_UNABLE_CONNECT = 7

        val DEVICE_NAME = "device_name"
        val TOAST = "toast"
    }

    private val NAME = "ZJPrinter"
    private val MY_UUID = fromString("00001101-0000-1000-8000-00805F9B34FB")

    private var adapter = BluetoothAdapter.getDefaultAdapter()
    private var mState = STATE_NONE

    private var acceptThread: AcceptThread? = null

    @Synchronized fun setState(state: Int) {
        this.mState = state
        handler.obtainMessage(MESSAGE_STATE_CHANGE, state, -1).sendToTarget()
    }

    @Synchronized fun getState(): Int = mState

    @Synchronized fun start() {

    }

    private inner class AcceptThread: Thread() {
        private var mmServerSocket: BluetoothServerSocket? = null

        init {
            try {
                mmServerSocket = adapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID)
            }
            catch (e: IOException) {
                Timber.e(e)
            }
        }

        override fun run() {
            name = "AcceptThread"
            var socket: BluetoothSocket? = null
            while(mState != STATE_CONNECTED) {
                try {
                    socket = mmServerSocket?.accept()
                }
                catch (e: IOException) {
                    Timber.e(e)
                    break
                }
                if(socket != null) {
                    synchronized(this@BluetoothService) {
                        when(mState) {

                        }
                    }
                }
            }
        }

        fun cancel() {
            try {
                mmServerSocket?.close()
            } catch (e: IOException) {
                Timber.e(e)
            }

        }
    }

    private inner class ConnectThread(val device: BluetoothDevice): Thread() {
        private var mmSocket: BluetoothSocket? = null
        private var mmDevice: BluetoothDevice? = null

        init {
            mmDevice = device
            try {

            }
            catch (e: IOException) {
                Timber.e(e)
            }
        }

    }
}