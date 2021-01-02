package com.homeofthings.model

class WifiNetwork(
    val bssid: String,
    val ipAddress: String,
    val macAddress: String,
    val ssid: String
) {
    var current: Boolean = false

}