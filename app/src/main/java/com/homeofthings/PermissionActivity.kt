package com.homeofthings

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class PermissionActivity : AppCompatActivity() {

    private val permissionAllCode = 1
    private val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_WIFI_STATE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (hasPermissions(this, *permissions)) {
            toHomePage()
        }

        setContentView(R.layout.permission)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var hasAllPermissions: Boolean = true;

        // Loops through each permission and check if its been granted.
        for (result in grantResults) {
            if (grantResults.isEmpty() || result != PackageManager.PERMISSION_GRANTED) {
                hasAllPermissions = false
            }
        }

        if (hasAllPermissions) {
            toHomePage()
        } else {
            print("Not granted")
        }
    }

    fun approved(view: View) {
        if (!hasPermissions(this, *permissions)) {
            ActivityCompat.requestPermissions(this, permissions, permissionAllCode)
        } else {
            toHomePage()
        }
    }

    private fun hasPermissions(context: Context, vararg permissions: String): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun toHomePage() {
        // Permission is granted, continue the flow.
        // Creates the intent for the home activity and
        // finish is called afterwards to prevent the user going back.
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}