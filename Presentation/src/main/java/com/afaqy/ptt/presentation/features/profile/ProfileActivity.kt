package com.afaqy.ptt.presentation.features.profile

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
import com.afaqy.ptt.codec.PTTMessageEncoder
import com.afaqy.ptt.models.PTTMessageType
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import dagger.android.AndroidInjection
import com.afaqy.ptt.presentation.R
import com.afaqy.ptt.presentation.base.BaseActivity
import com.afaqy.ptt.presentation.base.PreferenceControl
import com.afaqy.ptt.presentation.base.model.BaseMessageView
import com.afaqy.ptt.presentation.base.model.ProfileView
import com.afaqy.ptt.presentation.base.state.Resource
import com.afaqy.ptt.presentation.base.state.ResourceState
import com.afaqy.ptt.presentation.di.ViewModelProviderFactory
import com.afaqy.ptt.presentation.features.channels.AudioStreamingService
import com.afaqy.ptt.presentation.features.channels.ChannelsActivity
import com.afaqy.ptt.presentation.features.channels.MicRecorder
import com.afaqy.ptt.presentation.features.channels.SocketHandler
import com.afaqy.ptt.presentation.features.editprofile.EditProfileActivity
import com.afaqy.ptt.presentation.features.login.LoginActivity
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.IOException
import java.lang.Exception
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SocketChannel
import javax.inject.Inject

class ProfileActivity : BaseActivity() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private lateinit var profileViewModel: ProfileViewModel
    var deviceId = ""
    var simSerial = ""
    var profileView: ProfileView? = null
    private val UPDATE_PROFILE_REQUEST_CODE: Int = 200
    private lateinit var telMgr: TelephonyManager
    private lateinit var userCode: String

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

        profileViewModel.getLogout().observe(this,
            Observer<Resource<BaseMessageView>> { it?.let { handleLogout(it) } })

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
        userCode = PreferenceControl.loadToken(this)
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
                if (resource.message.equals("401 Unauthorized")) {
                    PreferenceControl.saveToken(this, "")
                    finish()
                    startActivity(LoginActivity.getStartIntent(this))
                }else{
                    Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    private fun logoutAndGoToLoginScreen(messageView: BaseMessageView?) {
        var client = ClientClass(deviceId, userCode)
        client.start()
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
                if (resource.message.equals("401 Unauthorized")) {
                    logoutAndGoToLoginScreen(resource.message?.let { BaseMessageView(it) });
                }else{
                    Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    private fun setupScreenForSuccess(profileView: ProfileView?) {
        progressView.visibility = View.GONE
        this.profileView = profileView
        profileView?.let {
            tvUserName.setText(profileView.username)
            tvFullName.setText(profileView.name)
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


    class ClientClass internal constructor(imei: String, userCode: String) : Thread() {
        var clientImei = imei
        var clientuserCode = userCode
        override fun run() {
            try {
                var socketChannel = SocketChannel.open()
                socketChannel.connect(
                    InetSocketAddress(
                        "212.70.49.194",
                        12050//port
                    )
                )
                SocketHandler.setSocket(socketChannel)

                var pttMessageEncoder =
                    PTTMessageEncoder(MicRecorder.SAMPLE_RATE, 1, MicRecorder.FRAME_SIZE)
                val encodedVoiceBytes = pttMessageEncoder.encodePTTMessage(
                    clientImei,
                    clientuserCode,
                    MicRecorder.channelsId,
                    null,
                    PTTMessageType.DISCONNECT
                )
                //    final OutputStream outputStream = SocketHandler.getSocket().getOutputStream();
                val audioStreamBuffer =
                    ByteBuffer.allocateDirect(1024)
                audioStreamBuffer.put(encodedVoiceBytes)
                audioStreamBuffer.flip()
                socketChannel.write(audioStreamBuffer)
                audioStreamBuffer.clear()
                // startActivity(Intent(getApplicationContext(), ChatWindow::class.java))
            } catch (e: UnsatisfiedLinkError) {
                e.printStackTrace()
            }catch (e: NoClassDefFoundError) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
