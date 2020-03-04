package io.android.projectx.presentation.features.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import io.android.projectx.presentation.R
import io.android.projectx.presentation.features.MainActivity
import io.android.projectx.presentation.features.browse.BrowseActivity
import io.android.projectx.presentation.features.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(
            {
                finish()
                startActivity(LoginActivity.getStartIntent(this))
            }, 3000
        )

    }

}
