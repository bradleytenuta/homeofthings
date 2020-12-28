import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:home_of_things/network/WifiNetwork.dart';

class NetworkPage extends StatefulWidget {

  NetworkPage({Key key, this.network}) : super(key: key);

  final WifiNetwork network;

  @override
  _NetworkPageState createState() => _NetworkPageState();
}

class _NetworkPageState extends State<NetworkPage> {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.network.wifiName),
      ),
    );
  }
}