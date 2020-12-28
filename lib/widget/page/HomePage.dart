import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:home_of_things/network/WifiController.dart';
import 'package:home_of_things/network/WifiNetwork.dart';
import 'package:home_of_things/widget/page/NetworkPage.dart';

final GlobalKey<ScaffoldState> scaffoldKey = GlobalKey<ScaffoldState>();

class HomePage extends StatefulWidget {

  HomePage({Key key, this.title}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {

  Set<WifiNetwork> _networks = Set<WifiNetwork>();

  @override
  void initState() {
    super.initState();
    WifiController.getInstance().init();
  }

  @override
  Widget build(BuildContext context) {
    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
      key: scaffoldKey,
      appBar: AppBar(
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.
        backgroundColor: Colors.transparent,
        elevation: 0,
        leading: Builder(
          builder: (BuildContext context) {
            return IconButton(
              icon: Image.asset('assets/home_of_things.android.logo.png', fit: BoxFit.cover),
            );
          },
        ),
        title: Text(widget.title),
        actions: <Widget>[
          IconButton(
            icon: const Icon(Icons.help_outline_rounded),
            tooltip: 'Show Help',
            onPressed: () {
              scaffoldKey.currentState.showSnackBar(SnackBar(content: Text('Help Has Arrived')));
            },
          ),
        ],
      ),
      body: ListView(
        scrollDirection: Axis.vertical,
        children: <Widget>[
          Container(
            child: Text(
              "Networks",
              overflow: TextOverflow.ellipsis,
              textScaleFactor: 2,
            ),
            padding: EdgeInsets.all(20),
          ),
          Column(
            mainAxisAlignment: MainAxisAlignment.start,
            children: _networks.map((WifiNetwork network) {
              return InkWell(
                  child: Text(network.wifiName),
                  onTap: () {
                    Navigator.push(
                        context,
                        MaterialPageRoute(builder: (context) => NetworkPage(network: network)));
                  },
              );
            }).toList(),
          ),
        ],
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () async {
          WifiNetwork network = await WifiController.getInstance().getWifi(context);
          if (network != null) {
            for (var i = 0; i < 30; i++) {
              setState(() {
                _networks.add(network);
              });
            }
          }
        },
        tooltip: 'Add Network',
        child: Icon(Icons.add),
      ),
    );
  }
}