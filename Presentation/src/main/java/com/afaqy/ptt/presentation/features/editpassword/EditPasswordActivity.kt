package com.afaqy.ptt.presentation.features.editpassword

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.afaqy.ptt.presentation.R
import com.afaqy.ptt.presentation.base.BaseActivity
import com.afaqy.ptt.presentation.base.PreferenceControl
import com.afaqy.ptt.presentation.base.createPartFromString
import com.afaqy.ptt.presentation.base.model.BaseMessageView
import com.afaqy.ptt.presentation.base.state.Resource
import com.afaqy.ptt.presentation.base.state.ResourceState
import com.afaqy.ptt.presentation.di.ViewModelProviderFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_edit_password.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject


class EditPasswordActivity : BaseActivity() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private lateinit var editPasswordViewModel: EditPasswordViewModel

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, EditPasswordActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_password)
        supportActionBar?.hide()
        AndroidInjection.inject(this)

        editPasswordViewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(EditPasswordViewModel::class.java)

        editPasswordViewModel.getEditProfile().observe(this,
            Observer<Resource<BaseMessageView>> { it?.let { handleDataState(it) } })
        ivBack.setOnClickListener { onBackPressed() }

        buSubmit.setOnClickListener {

            if (password_et_old_username.text.toString().trim().isEmpty()) {
                password_layout_old.error = getString(R.string.Please_enter_your_old_password)
                return@setOnClickListener
            } else
                password_layout_old.error = null


            if (password_et_new.text.toString().trim().isEmpty()) {
                password_layout_new.error = getString(R.string.Please_enter_your_the_new_password)
                return@setOnClickListener
            } else
                password_layout_new.error = null

            if (password_et_confirm_new.text.toString().trim().isEmpty()) {
                password_layout_confirm_new.error =
                    getString(R.string.Please_confirm_the_new_password)
                return@setOnClickListener
            } else
                password_et_confirm_new.error = null

            if (!password_et_confirm_new.text.toString()?.equals(password_et_new.text.toString())!!) {
                password_layout_confirm_new.error = getString(R.string.this_fields_must_be_the_same)
                return@setOnClickListener
            } else
                password_layout_confirm_new.error = null

            val password = password_et_confirm_new.text.toString()

            if (password.length>8||password.length<6) {
                password_layout_new.error = getString(R.string.the_password_must_be_between_6_and_8_characters)
                return@setOnClickListener
            } else
                password_layout_new.error = null

            val small: Pattern = Pattern.compile("[a-z]", Pattern.CASE_INSENSITIVE)
            val number: Pattern = Pattern.compile("[0-9]", Pattern.CASE_INSENSITIVE)
            val capital: Pattern = Pattern.compile("[A-Z]", Pattern.CASE_INSENSITIVE)
            val special: Pattern = Pattern.compile("[^A-Z0-9a-z]", Pattern.CASE_INSENSITIVE)
            val matcherSmall: Matcher = small.matcher(password)
            val matcherNumber: Matcher = number.matcher(password)
            val matcherCapital: Matcher = capital.matcher(password)
            val matcherSpecial: Matcher = special.matcher(password)

            val constainsSmall: Boolean = matcherSmall.find()
            val containsNumber: Boolean = matcherNumber.find()
            val containsCapital: Boolean = matcherCapital.find()
            val containsSpecial: Boolean = matcherSpecial.find()

            if (!constainsSmall||!containsNumber||!containsCapital||!containsSpecial) {
                password_layout_new.error = getString(R.string.the_password_must_be_between_6_and_8_characters)
                password_layout_confirm_new.error = getString(R.string.the_password_must_be_between_6_and_8_characters)
                return@setOnClickListener
            }

            editPasswordViewModel.fetchEditProfile(
                PreferenceControl.loadToken(this),
                createPartFromString("PUT"),
                createPartFromString(password_et_old_username.text.toString()),
                createPartFromString(password_et_new.text.toString()),
                createPartFromString(password_et_confirm_new.text.toString())
            )
        }
    }

    private fun handleDataState(resource: Resource<BaseMessageView>) {
        when (resource.status) {
            ResourceState.SUCCESS -> {
                setupScreenForSuccess(resource.data)
            }
            ResourceState.LOADING -> {
                progressView.visibility = View.VISIBLE
            }
            ResourceState.ERROR -> {
                progressView.visibility = View.GONE
                Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupScreenForSuccess(baseMessageView: BaseMessageView?) {
        progressView.visibility = View.GONE
        Toast.makeText(this, baseMessageView?.message, Toast.LENGTH_LONG).show()
        finish()
    }

}
