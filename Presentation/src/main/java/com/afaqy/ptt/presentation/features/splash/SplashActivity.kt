package com.afaqy.ptt.presentation.features.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.afaqy.ptt.presentation.R
import com.afaqy.ptt.presentation.base.PreferenceControl
import com.afaqy.ptt.presentation.features.channels.ChannelsActivity
import com.afaqy.ptt.presentation.features.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(
            {
                finish()
                if (PreferenceControl.loadToken(this).equals(""))
                    startActivity(LoginActivity.getStartIntent(this))
                else
                    startActivity(ChannelsActivity.getStartIntent(this))

            }, 1000
        )

    }

}
