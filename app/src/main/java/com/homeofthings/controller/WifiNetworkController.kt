package com.homeofthings.controller

import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.homeofthings.R
import com.homeofthings.adapter.WifiNetworkAdapter
import com.homeofthings.exception.NoWifiNetworkFoundException
import com.homeofthings.model.WifiNetwork
import java.math.BigInteger
import java.net.InetAddress
import java.net.UnknownHostException
import java.nio.ByteOrder

object WifiNetworkController {

    private var wifiNetworks: ArrayList<WifiNetwork> = ArrayList()

    // These variables are initialised when the build method is called.
    // '?' allows variables to be set to null
    private var context: AppCompatActivity? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: WifiNetworkAdapter? = null

    fun build(context: AppCompatActivity) {
        this.context = context

        // Lookup the recyclerview in activity layout
        recyclerView = context.findViewById<View>(R.id.networks_list) as RecyclerView

        // Create adapter passing in the sample user data
        adapter = WifiNetworkAdapter(wifiNetworks)

        // Attach the adapter to the recyclerview to populate items
        recyclerView!!.adapter = adapter

        // Set layout manager to position the items
        recyclerView!!.layoutManager = LinearLayoutManager(context)
    }

    fun destroy() {
        context = null
        recyclerView = null
        adapter = null
        wifiNetworks = ArrayList()
    }

    fun addCurrentWifi() {
        // Uses the WifiManager to obtain the currently connected wifi
        // as an Object of type WifiInfo. WifiManager is obtained by getting the system
        // service via this context.
        val wifiManager: WifiManager = context!!.getSystemService(AppCompatActivity.WIFI_SERVICE) as WifiManager
        val wifiInfo: WifiInfo = wifiManager.connectionInfo

        // Checks to see if the Wifi network has already been
        // added or not before continuing.
        // If it exists already then throw some toast and exit.
        if (ifExists(wifiInfo)) {
            Toast
                .makeText(context, getWifiSSID(wifiInfo) + " is already added!", Toast.LENGTH_SHORT)
                .show()
            return
        }

        // Gets the IP Address, its possible there could be an exception
        // So a default value is provided.
        var ipAddressAsString = try {
            getIpAddress(wifiInfo)
        } catch(exception: UnknownHostException) {
            "N/A"
        }

        // Uses the wifiInfo to create a wifi network object.
        val wifiNetwork = WifiNetwork(wifiInfo.bssid, ipAddressAsString, wifiInfo.macAddress, getWifiSSID(wifiInfo))
        wifiNetwork.current = true

        // Adds the object to the end of the list by using size to find the end of the list.
        wifiNetworks.add(wifiNetworks.size, wifiNetwork)
        // Notify the adapter that an item was inserted at the last position.
        adapter!!.notifyItemInserted(wifiNetworks.size)
    }

    @Throws(NoWifiNetworkFoundException::class)
    fun updateWifi(wifiInfo: WifiInfo) {
        // uses the find method to get index and object of given wifiInfo.
        var (index, wifiNetwork) = find(wifiInfo)

        // Replaces the old wifiNetwork with the new one.
        wifiNetworks[index] = wifiNetwork

        // Notifies the adapter of the change.
        adapter!!.notifyItemChanged(index)
    }

    @Throws(NoWifiNetworkFoundException::class)
    private fun find(wifiInfo: WifiInfo): Pair<Int, WifiNetwork> {
        for ((index, wifiNetwork) in wifiNetworks.withIndex()) {
            if (wifiNetwork.bssid == wifiInfo.bssid) {
                return Pair(index, wifiNetwork)
            }
        }
        throw NoWifiNetworkFoundException("Could not find wifiNetwork within controller.")
    }

    private fun ifExists(wifiInfo: WifiInfo): Boolean {
        for (wifiNetwork in wifiNetworks) {
            if (wifiNetwork.bssid == wifiInfo.bssid) {
                return true
            }
        }
        return false
    }

    private fun getWifiSSID(wifiInfo: WifiInfo): String {
        var ssid: String = wifiInfo.ssid
        return ssid.replace("\"", "")
    }

    @Throws(UnknownHostException::class)
    private fun getIpAddress(wifiInfo: WifiInfo): String {
        var ipAddress: Int = wifiInfo.ipAddress

        // Convert little-endian to big-endian if needed
        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            ipAddress = Integer.reverseBytes(ipAddress)
        }

        val ipByteArray: ByteArray = BigInteger.valueOf(ipAddress.toLong()).toByteArray()

        try {
            return InetAddress.getByAddress(ipByteArray).hostAddress
        } catch(exception: UnknownHostException) {
            throw exception
        }
    }
}