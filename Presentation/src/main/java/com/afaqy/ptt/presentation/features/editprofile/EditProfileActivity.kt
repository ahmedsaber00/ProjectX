package com.afaqy.ptt.presentation.features.editprofile

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.afaqy.ptt.presentation.R
import com.afaqy.ptt.presentation.base.PreferenceControl
import com.afaqy.ptt.presentation.base.createPartFromString
import com.afaqy.ptt.presentation.base.model.BaseMessageView
import com.afaqy.ptt.presentation.base.model.ProfileView
import com.afaqy.ptt.presentation.base.prepareImagePart
import com.afaqy.ptt.presentation.base.state.Resource
import com.afaqy.ptt.presentation.base.state.ResourceState
import com.afaqy.ptt.presentation.di.ViewModelProviderFactory
import com.afaqy.ptt.presentation.features.channels.AudioStreamingService
import com.afaqy.ptt.presentation.features.editpassword.EditPasswordActivity
import com.afaqy.ptt.presentation.features.login.LoginActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_edit_profile.*
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

class EditProfileActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private lateinit var editProfileViewModel: EditProfileViewModel
    private var picturePath = ""
    lateinit var dialog: Dialog
    lateinit var buYes: Button
    lateinit var buNo: Button

    private lateinit var profileView: ProfileView
    private var serverImage: MultipartBody.Part? = null
    private val SELECT_FILE = 2
    private var isChanged = false

    companion object {
        fun getStartIntent(context: Context, profileView: ProfileView?): Intent {
            return Intent(context, EditProfileActivity::class.java).putExtra("profile", profileView)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        supportActionBar?.hide()
        AndroidInjection.inject(this)
        editProfileViewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(EditProfileViewModel::class.java)

        editProfileViewModel.getEditProfile().observe(this,
            Observer<Resource<BaseMessageView>> { it?.let { handleDataState(it) } })

        profileView = intent.getParcelableExtra<ProfileView>("profile")
        profile_et_fullname.setText(profileView.name)
        profile_et_email.setText(profileView.email)
        profile_et_phone.setText(profileView.mobile)
        profile_et_address.setText(profileView.address)

        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.discard_dialog);
        dialog.setCancelable(true)
        buYes = dialog.findViewById(R.id.buYes)
        buNo = dialog.findViewById(R.id.buNo)

        var stringbuilder: StringBuilder? = profileView.countryCode?.let { StringBuilder(it) }

        var plusIndex: Int = stringbuilder?.indexOf('+') ?: -1
        if (plusIndex != -1)
            stringbuilder?.deleteCharAt(plusIndex)
        ccp.setCountryForPhoneCode(stringbuilder.toString().toInt())
        setRoundedImage(this, profileView.photo, ivProfile)
        profile_layout_password.setOnClickListener {
            startActivity(EditPasswordActivity.getStartIntent(this))
        }

        profile_et_phone.doAfterTextChanged {
            profile_layout_phone.isHintEnabled = it?.length!! > 0
            isChanged = true
            profile_layout_phone.error = null
        }

        profile_et_address.doAfterTextChanged {
            profile_layout_address.error = null
            isChanged = true
        }
        profile_et_email.doAfterTextChanged {
            profile_layout_email.error = null
            isChanged = true
        }
        profile_et_fullname.doAfterTextChanged {
            profile_layout_fullname.error = null
            isChanged = true
        }

        ivBack.setOnClickListener {
            onBackPressed()
        }
        tvChangePassword.setOnClickListener { profile_layout_password.performClick() }
        profile_et_password.setOnClickListener { profile_layout_password.performClick() }
        ivEdit.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                // We do not have this permission. Let's ask the user
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    10
                )

            } else {
                pickImage()
            }
        }
        buSubmit.setOnClickListener {
            if (profile_et_fullname.text.toString().trim().isNotEmpty()) {
                profileView.name = profile_et_fullname.text.toString()
            }else {
                profile_layout_fullname.error = getString(R.string.Please_enter_fullname)
                return@setOnClickListener
            }

            if (profile_et_email.text.toString().trim().isNotEmpty()) {
                profileView.email = profile_et_email.text.toString()
            } else {
                profile_layout_email.error = getString(R.string.Please_enter_valid_email)
                return@setOnClickListener
            }

            if (profile_et_phone.text.toString().trim().isNotEmpty()) {
                profileView.mobile = profile_et_phone.text.toString()

            }else {
                profile_layout_phone.isHintEnabled = true
                profile_layout_phone.error = getString(R.string.Please_enter_mobile)
                return@setOnClickListener
            }

            profileView.address = profile_et_address.text.toString()

            profileView.countryCode = ccp.selectedCountryCode

            if (picturePath.isNotEmpty()) {
                val file = File(picturePath)
                serverImage = prepareImagePart("photo", file)
                editProfileViewModel.fetchEditProfile(
                    PreferenceControl.loadToken(this),
                    createPartFromString("PUT"),
                    createPartFromString(profileView.name.toString()),
                    createPartFromString(profileView.email.toString()),
                    createPartFromString(profileView.countryCode.toString()),
                    createPartFromString(profileView.mobile.toString()),
                    createPartFromString(profileView.address.toString()),
                    serverImage
                )
            } else {
                editProfileViewModel.fetchEditProfile(
                    PreferenceControl.loadToken(this),
                    createPartFromString("PUT"),
                    createPartFromString(profileView.name.toString()),
                    createPartFromString(profileView.email.toString()),
                    createPartFromString(profileView.countryCode.toString()),
                    createPartFromString(profileView.mobile.toString()),
                    createPartFromString(profileView.address.toString())
                )
            }
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
                if (resource.message.equals("HTTP 401 Unauthorized")) {
                    logoutAndGoToLoginScreen(resource.message?.let { BaseMessageView(it) });
                } else if (resource.message.equals("413 Payload Too Large")) {
                    Toast.makeText(this, getString(R.string.please_choose_smaller_image), Toast.LENGTH_LONG).show()
                } else
                    Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupScreenForSuccess(baseMessageView: BaseMessageView?) {
        progressView.visibility = View.GONE
        Toast.makeText(this, baseMessageView?.message, Toast.LENGTH_LONG).show()
        val returnIntent = Intent()
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    private fun logoutAndGoToLoginScreen(messageView: BaseMessageView?) {
        progressView.visibility = View.GONE
        Toast.makeText(this, messageView?.message, Toast.LENGTH_LONG).show()
        stopService(Intent(applicationContext, AudioStreamingService::class.java))
        finishAffinity()
        PreferenceControl.saveToken(this, "")
        startActivity(LoginActivity.getStartIntent(this))
    }

    private fun pickImage() {
        val intent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                val selectedImage = data?.data
                val filePathColumn =
                    arrayOf(MediaStore.Images.Media.DATA)
                val cursor = contentResolver.query(
                    selectedImage!!,
                    filePathColumn, null, null, null
                )
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                picturePath = cursor.getString(columnIndex)
                cursor.close()
                setRoundedImage(this, picturePath, ivProfile)
                isChanged = true
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            pickImage()
        }
    }

    fun setRoundedImage(
        context: Context?,
        image: String?,
        imageView: ImageView?
    ) {
        Glide.with(context!!).load(
            image
        ).apply(RequestOptions.bitmapTransform(CircleCrop()))
            .apply(RequestOptions().placeholder(R.drawable.profile).error(R.drawable.profile))
            .into(imageView!!)
    }

    override fun onBackPressed() {
        if (isChanged) {
            buYes.setOnClickListener {
                isChanged = false
                onBackPressed()
            }
            buNo.setOnClickListener { dialog.dismiss() }
            dialog.show()
        } else
            super.onBackPressed()
    }
}
