import 'package:flutter/material.dart';
import 'package:home_of_things/network/WifiNetwork.dart';

import 'package:connectivity/connectivity.dart';
import 'package:wifi_info_flutter/wifi_info_flutter.dart';
import 'package:permission_handler/permission_handler.dart';

class WifiController {

  static WifiController instance;

  static getInstance() {
    if (instance == null) {
      return new WifiController();
    }
    return instance;
  }

  WifiNetwork _currentWifiNetwork;

  void init() {
    // Listens for a change in wifi network.
    // Whenever a change in network is detected we get the latest current
    // wifi network.
    Connectivity().onConnectivityChanged.listen((ConnectivityResult result) async {
      // Got a new connectivity status!
      if (result == ConnectivityResult.wifi) {
        _currentWifiNetwork = await _getCurrentWifi();
      } else {
        // If not connected to wifi set current wifi network to null.
        _currentWifiNetwork = null;
      }
    });
  }

  Future<WifiNetwork> getWifi(BuildContext context) async {
    WifiNetwork currentWifi = await _getCurrentWifi();
    if (await _showMyDialog(currentWifi, context)) {
      return currentWifi;
    }
    return null;
  }

  void deleteWifi() {

  }

  Future<WifiNetwork> _getCurrentWifi() async {
    if (await Permission.location.request().isGranted) {
      if (await (Connectivity().checkConnectivity()) == ConnectivityResult.wifi) {
        return new WifiNetwork(
            await WifiInfo().getWifiBSSID(),
            await WifiInfo().getWifiIP(),
            await WifiInfo().getWifiName()
        );
      }
    }
    return null;
  }

  Future<bool> _showMyDialog(WifiNetwork network, BuildContext context) async {
    return showDialog(
      context: context,
      barrierDismissible: false, // user must tap button!
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text("Network Found!"),
          content: RichText(
            text: TextSpan(
              style: TextStyle(color: Colors.black),
              children: <TextSpan>[
                TextSpan(text: "Add "),
                TextSpan(
                  text: network.wifiName,
                  style: TextStyle(color: Colors.red),
                ),
                TextSpan(text: " to list of Networks?"),
              ],
            ),
          ),
          actions: <Widget>[
            InkWell(
              child: FlatButton(
                onPressed: () {
                  print("'No' pressed");
                  Navigator.pop(context, false);
                },
                child: Text("No"),
              ),
            ),
            InkWell(
                child: FlatButton(
                  onPressed: () {
                    print("'Yes' pressed");
                    Navigator.pop(context, true);
                  },
                  child: Text("Yes"),
                )
            ),
          ],
        );
      },
    );
  }
}