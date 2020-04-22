package com.afaqy.ptt.presentation.features.channels

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.afaqy.ptt.codec.PTTMessageEncoder
import com.afaqy.ptt.models.PTTMessageType
import com.afaqy.ptt.presentation.R
import com.afaqy.ptt.presentation.base.BaseActivity
import com.afaqy.ptt.presentation.base.PreferenceControl
import com.afaqy.ptt.presentation.base.model.ChannelsView
import com.afaqy.ptt.presentation.base.state.Resource
import com.afaqy.ptt.presentation.base.state.ResourceState
import com.afaqy.ptt.presentation.di.ViewModelProviderFactory
import com.afaqy.ptt.presentation.features.login.LoginActivity
import com.afaqy.ptt.presentation.features.profile.ProfileActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SocketChannel
import javax.inject.Inject

class ChannelsActivity : BaseActivity() {

    @Inject
    lateinit var channelsAdapter: ChannelsAdapter
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private lateinit var channelsViewModel: ChannelsViewModel

    private var stringBuilder: StringBuilder = StringBuilder("");

    private var channelsList: MutableList<ChannelsView> = mutableListOf()
    private var isTalking: Boolean = false
    private var micRecorder: MicRecorder? = null
    @Volatile
    private var selectedChannelsId: ArrayList<String> = ArrayList()
    var t: Thread? = null
    lateinit var telMgr: TelephonyManager
    private lateinit var imei: String
    private lateinit var userCode: String
    private val LOGOUT_REQUEST_CODE: Int = 100

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, ChannelsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        AndroidInjection.inject(this)
        channelsViewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(ChannelsViewModel::class.java)
        setupBrowseRecycler()

        channelsViewModel.getChannels().observe(this,
            Observer<Resource<List<ChannelsView>>> { it?.let { handleDataState(it) } })

        channelsViewModel.fetchChannels(PreferenceControl.loadToken(this), 0)

        var client = ClientClass(imei, userCode)
        client.start()
        stopService(Intent(applicationContext, AudioStreamingService::class.java))
        startForegroundService(Intent(applicationContext, AudioStreamingService::class.java))
        ivMic.setOnTouchListener { view, motionEvent ->
            if (checkForMicPermission()) {
                val action = motionEvent.actionMasked
                when (action) {
                    MotionEvent.ACTION_DOWN, MotionEvent.ACTION_UP -> {
                        pushToTalk()
                        return@setOnTouchListener true
                    }
                    else -> return@setOnTouchListener false
                }
            }
            return@setOnTouchListener false
        }

        ibProfile.setOnClickListener {
            startActivityForResult(ProfileActivity.getStartIntent(this), LOGOUT_REQUEST_CODE)
        }
        etSearch.doOnTextChanged { text, start, count, after ->
            getSearchedList(text.toString())
        }
        checkForMicPermission()
        telMgr = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
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
                    imei = telMgr.getMeid();
                } else if (telMgr.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
                    imei = telMgr.getImei(0);
                } else {
                    imei = "" // default!!!
                }
            }
        }
        userCode = PreferenceControl.loadToken(this)
    }

    private fun setupBrowseRecycler() {
        channelsAdapter.channelListener = channelstener
        recyclerChannels.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerChannels.adapter = channelsAdapter
    }

    private fun handleDataState(resource: Resource<List<ChannelsView>>) {
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
                    PreferenceControl.saveToken(this, "")
                    finish()
                    startActivity(LoginActivity.getStartIntent(this))
                } else {
                    Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    private fun setupScreenForSuccess(channels: List<ChannelsView>?) {
        progressView.visibility = View.GONE
        channels?.let {
            channelsList.addAll(channels)
            channelsAdapter.channels = it
            channelsAdapter.notifyDataSetChanged()
            recyclerChannels.visibility = View.VISIBLE
        } ?: run {

        }
    }

    private fun getSearchedList(searchedText: String) {
        channelsList?.let {
            var tempChannelsList: MutableList<ChannelsView> = mutableListOf()
            for (i in 0..channelsList.size - 1) {
                if (channelsList[i].name.contains(searchedText))
                    tempChannelsList.add(channelsList[i])
            }
            channelsAdapter.channels = tempChannelsList
            channelsAdapter.notifyDataSetChanged()
        }
    }

    private fun pushToTalk() {
        if (!isTalking) { // stream audio
            isTalking = true
            if (micRecorder == null)
                micRecorder =
                    MicRecorder(imei, userCode)
            t = Thread(micRecorder)
            if (micRecorder != null) {
                MicRecorder.keepRecording = true
            }
            t?.start()
            // start animation
            // start animation
            rippleBackground.startRippleAnimation()
        } else if (isTalking) {
            isTalking = false
            if (micRecorder != null) {
                MicRecorder.keepRecording = false
            }
            // stop animation
            rippleBackground.clearAnimation();
            rippleBackground.stopRippleAnimation();
        }
    }

    private val channelstener = object : ChannelListener {

        override fun onChannelClicked(channelPosition: Int) {
            if (channelsAdapter.channels[channelPosition].isSelected) {
                if (!stringBuilder.isBlank())
                    stringBuilder.append(" / ")
                stringBuilder.append(channelsAdapter.channels[channelPosition].name)
                selectedChannelsId.add(channelsAdapter.channels[channelPosition].code)
            } else {
                var channelName = channelsAdapter.channels[channelPosition].name
                var index = stringBuilder.indexOf(channelName)
                if (index != -1)
                    stringBuilder.delete(index, index + channelName.length)
                if (stringBuilder.contains(" /  / "))
                    stringBuilder.delete(
                        stringBuilder.indexOf(" /  / "),
                        stringBuilder.indexOf(" /  / ") + 3
                    )
                if (stringBuilder.endsWith(" / "))
                    stringBuilder.delete(stringBuilder.length - 3, stringBuilder.length)
                if (stringBuilder.startsWith(" / "))
                    stringBuilder.delete(0, 3)
                selectedChannelsId.remove(channelsAdapter.channels[channelPosition].code)
            }
            tvSelectedChannels.setText(stringBuilder)
            MicRecorder.channelsId = arrayOfNulls<String>(selectedChannelsId.size)
            selectedChannelsId.toArray(MicRecorder.channelsId)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (micRecorder != null) {
            MicRecorder.keepRecording = false
        }
    }


    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        if (event.keyCode == 1015 && isTalking) {//xcoverkey
            if (micRecorder != null) {
                MicRecorder.keepRecording = false
            }
            if (ivMic != null)
                ivMic.setBackgroundResource(R.drawable.bg_mic_rounded)
            // stop animation
            rippleBackground.clearAnimation();
            rippleBackground.stopRippleAnimation();
            isTalking = false
        } else if (event.keyCode == 4) {//back key
            onBackPressed()
        }
        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.keyCode == 1015 && !isTalking) { // stream audio
            if (micRecorder == null)
                micRecorder =
                    MicRecorder(imei, userCode)
            t = Thread(micRecorder)
            if (micRecorder != null) {

                MicRecorder.keepRecording = true
            }
            t!!.start()
            rippleBackground.startRippleAnimation()
            isTalking = true
        }
        return true
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
                    PTTMessageType.CONNECT
                )
                //    final OutputStream outputStream = SocketHandler.getSocket().getOutputStream();
                val audioStreamBuffer =
                    ByteBuffer.allocateDirect(1024)
                audioStreamBuffer.put(encodedVoiceBytes)
                audioStreamBuffer.flip()
                socketChannel.write(audioStreamBuffer)
                audioStreamBuffer.clear()
                // startActivity(Intent(getApplicationContext(), ChatWindow::class.java))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        for (i in 0..channelsList.size - 1) {
            channelsList[i].isSelected = false
        }
        channelsAdapter.channels = channelsList
        channelsAdapter.notifyDataSetChanged()
    }

    fun checkForMicPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // We do not have this permission. Let's ask the user
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                100
            )
            return false
        } else {
            return true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LOGOUT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            recreate()
        }
    }
}
