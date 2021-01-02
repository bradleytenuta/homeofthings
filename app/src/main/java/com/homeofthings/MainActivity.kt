package com.homeofthings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.homeofthings.controller.WifiNetworkController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        // Creates a WifiNetwork Adapter.
        // This adapter acts as the middle man between the data and the recycler view.
        WifiNetworkController.build(this)
    }

    override fun finish() {
        WifiNetworkController.destroy()
        super.finish()
    }

    fun addNetwork(view: View) {
        WifiNetworkController.addCurrentWifi()
    }
}