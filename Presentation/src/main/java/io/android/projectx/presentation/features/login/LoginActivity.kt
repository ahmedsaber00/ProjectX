package io.android.projectx.presentation.features.login

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation.findNavController
import io.android.projectx.presentation.R


class LoginActivity : AppCompatActivity() {
    lateinit var dialog: Dialog
    lateinit var telMgr: TelephonyManager
    lateinit var imeiTxt:AppCompatTextView
    lateinit var serialTxt:AppCompatTextView
    var deviceId = ""
    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(io.android.projectx.presentation.R.layout.activity_login)
        supportActionBar?.hide()
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(true)
        imeiTxt= dialog.findViewById<AppCompatTextView>(R.id.imei)
        serialTxt = dialog.findViewById<AppCompatTextView>(R.id.simSerial)
        telMgr = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
            != PackageManager.PERMISSION_GRANTED) {
            // We do not have this permission. Let's ask the user
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                100
            )

        } else {
            if (Build.VERSION.SDK_INT >= 26) {
                if (telMgr.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA) {
                    deviceId = telMgr.getMeid();
                } else if (telMgr.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
                    deviceId = telMgr.getImei(0);
                } else {
                    deviceId = "" // default!!!
                }
            }
            imeiTxt.text = deviceId
            serialTxt.text = telMgr.simSerialNumber
            //dialog.show()
            Log.e("sssss: ", "deviceId : " + deviceId)
            Log.e("sssss: ", "line1Number : " + telMgr.simSerialNumber)
        }
    }

    override fun onSupportNavigateUp() =
        findNavController(this, R.id.navHostFragment).navigateUp()

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            100 -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= 26) {
                        if (telMgr.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA) {
                            deviceId = telMgr.getMeid();
                        } else if (telMgr.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
                            deviceId = telMgr.getImei(0);
                        } else {
                            deviceId = "" // default!!!
                        }
                    }
                    Log.e("sssss: ", "deviceId : " + deviceId)
                    Log.e("sssss: ", "line1Number : " + telMgr.line1Number)
                    imeiTxt.text = deviceId
                    serialTxt.text = telMgr.getSimSerialNumber()
                    dialog.show()
                } else {
                    // permission denied
                }
                return
            }
        }
    }
}
