package io.android.projectx.presentation.features.profile

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import io.android.projectx.presentation.R
import io.android.projectx.presentation.base.PreferenceControl
import io.android.projectx.presentation.base.model.BaseMessageView
import io.android.projectx.presentation.base.state.Resource
import io.android.projectx.presentation.base.state.ResourceState
import io.android.projectx.presentation.di.ViewModelProviderFactory
import io.android.projectx.presentation.features.channels.AudioStreamingService
import io.android.projectx.presentation.features.editprofile.EditProfileActivity
import io.android.projectx.presentation.features.login.LoginActivity
import kotlinx.android.synthetic.main.activity_profile.*
import javax.inject.Inject

class ProfileActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private lateinit var profileViewModel: ProfileViewModel
    lateinit var telMgr: TelephonyManager
    var deviceId = ""
    var simSerial = ""

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, ProfileActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.hide()
        AndroidInjection.inject(this)
        telMgr = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        profileViewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(ProfileViewModel::class.java)

        ibEditProfile.setOnClickListener {
            startActivity(EditProfileActivity.getStartIntent(this))
        }
        tvLogout.setOnClickListener {

            profileViewModel.getLogout().observe(this,
                Observer<Resource<BaseMessageView>> { it?.let { handleDataState(it) } })

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED
            ) {
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
                simSerial = telMgr.simSerialNumber?:""
                profileViewModel.fetchLogout(
                    PreferenceControl.loadData(this),
                    deviceId,
                    simSerial
                )
            }
        }
        ivBack.setOnClickListener { onBackPressed() }
    }


    private fun handleDataState(resource: Resource<BaseMessageView>) {
        when (resource.status) {
            ResourceState.SUCCESS -> {
                setupScreenForSuccess(resource.data)
            }
            ResourceState.LOADING -> {
                progressView.visibility = View.VISIBLE
            }
            ResourceState.ERROR -> {
                progressView.visibility = View.GONE
                if (resource.message.equals("HTTP 401 Unauthorized")) {
                    PreferenceControl.saveData(this, "")
                    finish()
                    startActivity(LoginActivity.getStartIntent(this))
                }

            }
        }
    }

    private fun setupScreenForSuccess(messageView: BaseMessageView?) {
        progressView.visibility = View.GONE
        Toast.makeText(this, messageView?.message, Toast.LENGTH_LONG).show()
        stopService(Intent(applicationContext, AudioStreamingService::class.java))
        finishAffinity()
        PreferenceControl.saveData(this, "")
        startActivity(LoginActivity.getStartIntent(this))

    }


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
                    simSerial = telMgr.getSimSerialNumber()?:""
                    profileViewModel.fetchLogout(
                        PreferenceControl.loadData(this),
                        deviceId,
                        simSerial
                    )
                }
                return
            }
        }
    }
}
