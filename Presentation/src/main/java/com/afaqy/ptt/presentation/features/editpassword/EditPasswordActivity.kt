package com.afaqy.ptt.presentation.features.editpassword

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.afaqy.ptt.presentation.R
import com.afaqy.ptt.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditPasswordActivity : BaseActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, EditPasswordActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_password)
        supportActionBar?.hide()
        profile_layout_password.setOnClickListener {

        }
    }
}
