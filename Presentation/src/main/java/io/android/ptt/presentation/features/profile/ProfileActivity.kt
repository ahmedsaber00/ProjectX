package io.android.ptt.presentation.features.profile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import dagger.android.AndroidInjection
import io.android.ptt.presentation.R
import io.android.ptt.presentation.base.BaseActivity
import io.android.ptt.presentation.base.PreferenceControl
import io.android.ptt.presentation.base.model.BaseMessageView
import io.android.ptt.presentation.base.model.ProfileView
import io.android.ptt.presentation.base.state.Resource
import io.android.ptt.presentation.base.state.ResourceState
import io.android.ptt.presentation.di.ViewModelProviderFactory
import io.android.ptt.presentation.features.channels.AudioStreamingService
import io.android.ptt.presentation.features.editprofile.EditProfileActivity
import io.android.ptt.presentation.features.login.LoginActivity
import kotlinx.android.synthetic.main.activity_profile.*
import javax.inject.Inject

class ProfileActivity : BaseActivity() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private lateinit var profileViewModel: ProfileViewModel
    lateinit var telMgr: TelephonyManager
    var deviceId = ""
    var simSerial = ""
    var profileView: ProfileView? = null
    private val UPDATE_PROFILE_REQUEST_CODE: Int = 200

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

        profileViewModel.getProfile().observe(this,
            Observer<Resource<ProfileView>> { it?.let { handleDataState(it) } })

        profileViewModel.fetchProfile(
            PreferenceControl.loadToken(this)
        )

        ibEditProfile.setOnClickListener {
            if (profileView != null)
                startActivityForResult(
                    EditProfileActivity.getStartIntent(this, profileView),
                    UPDATE_PROFILE_REQUEST_CODE
                )
        }
        tvLanguage.setOnClickListener {
            val builder1: AlertDialog.Builder = AlertDialog.Builder(this)
            builder1.setMessage(R.string.change_language)
                .setPositiveButton(R.string.yes) { dialog, which -> changeLanguage() }
                .setNegativeButton(R.string.no) { dialog, which -> dialog.dismiss() }.show();
        }

        tvLogout.setOnClickListener {

            profileViewModel.getLogout().observe(this,
                Observer<Resource<BaseMessageView>> { it?.let { handleLogout(it) } })

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
                simSerial = telMgr.simSerialNumber ?: ""
                profileViewModel.fetchLogout(
                    PreferenceControl.loadToken(this),
                    deviceId,
                    simSerial
                )
            }
        }
        ivBack.setOnClickListener { onBackPressed() }
    }

    private fun changeLanguage() {
        if (PreferenceControl.loadLanguage(this).equals(PreferenceControl.ARABIC)) {
            PreferenceControl.saveLanguage(this, PreferenceControl.ENGLISH)
        } else {
            PreferenceControl.saveLanguage(this, PreferenceControl.ARABIC)
        }
        val returnIntent = Intent()
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    private fun handleLogout(resource: Resource<BaseMessageView>) {
        when (resource.status) {
            ResourceState.SUCCESS -> {
                logoutAndGoToLoginScreen(resource.data)
            }
            ResourceState.LOADING -> {
                progressView.visibility = View.VISIBLE
            }
            ResourceState.ERROR -> {
                progressView.visibility = View.GONE
                if (resource.message.equals("HTTP 401 Unauthorized")) {
                    PreferenceControl.saveToken(this, "")
                    finish()
                    startActivity(LoginActivity.getStartIntent(this))
                }

            }
        }
    }

    private fun logoutAndGoToLoginScreen(messageView: BaseMessageView?) {
        progressView.visibility = View.GONE
        Toast.makeText(this, messageView?.message, Toast.LENGTH_LONG).show()
        stopService(Intent(applicationContext, AudioStreamingService::class.java))
        finishAffinity()
        PreferenceControl.saveToken(this, "")
        startActivity(LoginActivity.getStartIntent(this))

    }


    private fun handleDataState(resource: Resource<ProfileView>) {
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
                    logoutAndGoToLoginScreen(resource.message?.let { BaseMessageView(it) });
                }

            }
        }
    }

    private fun setupScreenForSuccess(profileView: ProfileView?) {
        progressView.visibility = View.GONE
        this.profileView = profileView
        profileView?.let {
            tvUserName.setText(profileView.name)
            tvEmail.setText(profileView.email)
            tvPhone.setText(profileView.countryCode.plus(profileView.mobile))
            tvSsn.setText(profileView.ssn)
            tvAddress.setText(profileView.address)
            tvPassword.setText("password")
            tvLanguage.setText(PreferenceControl.loadLanguage(this))
            tvLanguage.setText(PreferenceControl.loadLanguage(this))
            Glide.with(this).load(
                profileView.photo
            ).apply(RequestOptions.bitmapTransform(CircleCrop()))
                .apply(RequestOptions().placeholder(R.drawable.profile).error(R.drawable.profile))
                .into(ivProfile!!)

        } ?: run {

        }
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
                    simSerial = telMgr.getSimSerialNumber() ?: ""
                    profileViewModel.fetchLogout(
                        PreferenceControl.loadToken(this),
                        deviceId,
                        simSerial
                    )
                }
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UPDATE_PROFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            profileViewModel.fetchProfile(
                PreferenceControl.loadToken(this)
            )
        }
    }
}
