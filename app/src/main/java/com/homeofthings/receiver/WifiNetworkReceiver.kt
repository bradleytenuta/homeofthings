package com.homeofthings.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import com.homeofthings.controller.WifiNetworkController
import com.homeofthings.exception.NoWifiNetworkFoundException

class WifiNetworkReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        try {
            // TODO: Doesn't work.
            // TODO: If no wifi, should turn off all saying connected.
            val wifiManager: WifiManager = context.getSystemService(AppCompatActivity.WIFI_SERVICE) as WifiManager
            WifiNetworkController.updateWifi(wifiManager.connectionInfo)
        } catch(exception: NoWifiNetworkFoundException) {
            print("Unable to Update wifi. Wifi must not be in the controller.")
        }
    }
}