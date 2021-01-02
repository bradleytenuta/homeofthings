package com.homeofthings

import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.homeofthings.adapter.WifiNetworkAdapter
import com.homeofthings.model.WifiNetwork
import java.math.BigInteger
import java.net.InetAddress
import java.nio.ByteOrder

class MainActivity : AppCompatActivity() {

    private var wifiNetworks: ArrayList<WifiNetwork> = ArrayList<WifiNetwork>()

    private lateinit var adapter: WifiNetworkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        // Lookup the recyclerview in activity layout
        val rvContacts = findViewById<View>(R.id.networks_list) as RecyclerView

        // Create adapter passing in the sample user data
        adapter = WifiNetworkAdapter(wifiNetworks)

        // Attach the adapter to the recyclerview to populate items
        rvContacts.adapter = adapter

        // Set layout manager to position the items
        rvContacts.layoutManager = LinearLayoutManager(this)
    }

    fun addNetwork(view: View) {
        // Uses the WifiManager to obtain the currently connected wifi
        // as an Object of type WifiInfo. WifiManager is obtained by getting the system
        // service via this context.
        val wifiManager: WifiManager = this.getSystemService(WIFI_SERVICE) as WifiManager
        val wifiInfo: WifiInfo = wifiManager.connectionInfo

        // Uses the wifiInfo to create a wifi network object.
        val wifiNetwork = WifiNetwork(wifiInfo.bssid, getIpAddress(wifiInfo), wifiInfo.macAddress, getWifiSSID(wifiInfo))

        // Adds the object to the end of the list by using size to find the end of the list.
        wifiNetworks.add(wifiNetworks.size, wifiNetwork)
        // Notify the adapter that an item was inserted at the last position.
        adapter.notifyItemInserted(wifiNetworks.size)
    }

    private fun getWifiSSID(wifiInfo: WifiInfo): String {
        var ssid: String = wifiInfo.ssid

        return ssid.replace("\"", "")
    }

    private fun getIpAddress(wifiInfo: WifiInfo): String {
        var ipAddress: Int = wifiInfo.ipAddress

        // Convert little-endian to big-endian if needed
        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            ipAddress = Integer.reverseBytes(ipAddress)
        }

        val ipByteArray: ByteArray = BigInteger.valueOf(ipAddress.toLong()).toByteArray()

        // TODO: This line of code throws an exception in java.
        return InetAddress.getByAddress(ipByteArray).hostAddress
    }
}