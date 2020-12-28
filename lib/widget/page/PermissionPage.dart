import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:permission_handler/permission_handler.dart';

import 'HomePage.dart';

class PermissionPage extends StatelessWidget {

  PermissionPage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            Container(
              child: Text(
                "In order for this app to function it will require certain permissions. Click the button below to add the required permissions and to get started!",
                textAlign: TextAlign.center,
              ),
              padding: EdgeInsets.all(20),
            ),
            RaisedButton(
              onPressed: () async {
                if (await Permission.location.request().isGranted) {
                  Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => HomePage(title: title)));
                }
              },
              child: const Text('Add Permissions'),
            ),
          ],
        ),
      ),
    );
  }
}