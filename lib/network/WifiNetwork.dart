class WifiNetwork {

  String _wifiBSSID;
  String _wifiIP;
  String _wifiName;

  WifiNetwork(String wifiBSSID, String wifiIP, String wifiName) {
    this._wifiBSSID = wifiBSSID;
    this._wifiIP = wifiIP;
    this._wifiName = wifiName;
  }

  String get wifiName => _wifiName;

  String get wifiIP => _wifiIP;

  String get wifiBSSID => _wifiBSSID;
}