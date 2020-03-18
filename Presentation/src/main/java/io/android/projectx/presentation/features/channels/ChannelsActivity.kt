package io.android.projectx.presentation.features.channels

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.AndroidInjection
import io.android.projectx.presentation.R
import io.android.projectx.presentation.base.PreferenceControl
import io.android.projectx.presentation.base.model.ChannelsView
import io.android.projectx.presentation.base.state.Resource
import io.android.projectx.presentation.base.state.ResourceState
import io.android.projectx.presentation.di.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket
import javax.inject.Inject

class ChannelsActivity : AppCompatActivity() {

    @Inject
    lateinit var channelsAdapter: ChannelsAdapter
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private lateinit var channelsViewModel: ChannelsViewModel


    private var channelsList: MutableList<ChannelsView> = mutableListOf()
    private var isTalking: Boolean = false
    private var micRecorder: MicRecorder? = null
    var t: Thread? = null

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

        channelsViewModel.fetchChannels(PreferenceControl.loadData(this),0)

        var client =  ClientClass()
        client.start()
        startService(Intent(applicationContext, AudioStreamingService::class.java))

        ivMic.setOnTouchListener { view, motionEvent ->
            val action = motionEvent.actionMasked
            when (action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_UP -> {
                    pushToTalk()
                    return@setOnTouchListener true
                }
                else -> return@setOnTouchListener false
            }
        }
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

    private fun pushToTalk() {
        if (!isTalking) { // stream audio
            isTalking = true
            if (micRecorder==null)
                micRecorder = MicRecorder()
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
            //  channelsViewModel.unBookmarkRecipe(channelCode)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (micRecorder != null) {
            MicRecorder.keepRecording = false
        }
    }


    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        if (event.keyCode == 1015&&isTalking) {//xcoverkey
            if (micRecorder != null) {
                MicRecorder.keepRecording = false
            }
            if (ivMic!=null)
                ivMic.setBackgroundResource(R.drawable.bg_mic_rounded)
            // stop animation
            rippleBackground.clearAnimation();
            rippleBackground.stopRippleAnimation();
            isTalking = false
        }else if (event.keyCode == 4) {//back key
        onBackPressed()
        }
        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.keyCode == 1015&&!isTalking) { // stream audio
            if (micRecorder==null)
                micRecorder = MicRecorder()
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

    class ServerClass : Thread() {
        var socket: Socket? = null
        var serverSocket: ServerSocket? = null
        override fun run() {
            try {
                serverSocket =
                    ServerSocket(1232)//port
                socket = serverSocket!!.accept()
                SocketHandler.setSocket(socket)
              //  startActivity(Intent(getApplicationContext(), ChatWindow::class.java))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    class ClientClass internal constructor() : Thread() {
        var socket: Socket
        var hostAddress: String
        override fun run() {
            try {
                socket.connect(
                    InetSocketAddress(
                        "192.168.1.9",
                1232//port
                    ), 500
                )
                SocketHandler.setSocket(socket)
               // startActivity(Intent(getApplicationContext(), ChatWindow::class.java))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        init {
            socket = Socket()
            hostAddress = "192.168.1.191"
        }
    }

    override fun onPause() {
        super.onPause()
        for (i in 0..channelsList.size-1) {
            channelsList[i].isSelected = false
        }
        channelsAdapter.channels = channelsList
        channelsAdapter.notifyDataSetChanged()
    }
}
