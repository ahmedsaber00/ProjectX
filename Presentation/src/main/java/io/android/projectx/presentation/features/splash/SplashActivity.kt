package io.android.projectx.presentation.features.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import io.android.projectx.presentation.R
import io.android.projectx.presentation.base.PreferenceControl
import io.android.projectx.presentation.features.channels.ChannelsActivity
import io.android.projectx.presentation.features.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(
            {
                finish()
                if (PreferenceControl.loadData(this).equals(""))
                    startActivity(LoginActivity.getStartIntent(this))
                else
                    startActivity(ChannelsActivity.getStartIntent(this))

            }, 1000
        )

    }

}
