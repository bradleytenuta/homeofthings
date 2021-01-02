package com.homeofthings.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.homeofthings.R
import com.homeofthings.model.WifiNetwork

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
// Useful link in understanding RecyclerView: https://github.com/codepath/android_guides/wiki/Using-the-RecyclerView
class WifiNetworkAdapter(private val wifiNetworks: List<WifiNetwork>) : RecyclerView.Adapter<WifiNetworkAdapter.ViewHolder>() {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val ssidTextView: TextView = itemView.findViewById<TextView>(R.id.ssid)
        val bssidTextView: TextView = itemView.findViewById<TextView>(R.id.bssid)
        val ipAddressTextView: TextView = itemView.findViewById<TextView>(R.id.ipAddress)
        val macAddressTextView: TextView = itemView.findViewById<TextView>(R.id.macAddress)
    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WifiNetworkAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.item_wifi_network, parent, false)

        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: WifiNetworkAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val wifiNetwork: WifiNetwork = wifiNetworks[position]

        // Set item views based on your views and data model
        viewHolder.ssidTextView.text = wifiNetwork.ssid
        viewHolder.bssidTextView.text = wifiNetwork.bssid
        viewHolder.ipAddressTextView.text = wifiNetwork.ipAddress
        viewHolder.macAddressTextView.text = wifiNetwork.macAddress
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return wifiNetworks.size
    }
}