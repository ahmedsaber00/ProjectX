package io.android.projectx.presentation.features.editpassword

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.android.projectx.presentation.R
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditPasswordActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, EditPasswordActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        supportActionBar?.hide()
        profile_layout_password.setOnClickListener {

        }
    }
}
