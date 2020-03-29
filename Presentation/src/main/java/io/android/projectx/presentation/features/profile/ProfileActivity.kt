package io.android.projectx.presentation.features.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.android.projectx.presentation.R
import io.android.projectx.presentation.features.editprofile.EditProfileActivity
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, ProfileActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.hide()
        ibEditProfile.setOnClickListener {
            startActivity(EditProfileActivity.getStartIntent(this))
        }
    }
}
