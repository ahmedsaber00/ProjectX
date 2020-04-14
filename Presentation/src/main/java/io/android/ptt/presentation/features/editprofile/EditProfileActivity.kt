package io.android.ptt.presentation.features.editprofile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import dagger.android.AndroidInjection
import io.android.ptt.presentation.R
import io.android.ptt.presentation.base.PreferenceControl
import io.android.ptt.presentation.base.createPartFromString
import io.android.ptt.presentation.base.model.BaseMessageView
import io.android.ptt.presentation.base.model.ProfileView
import io.android.ptt.presentation.base.prepareImagePart
import io.android.ptt.presentation.base.state.Resource
import io.android.ptt.presentation.base.state.ResourceState
import io.android.ptt.presentation.di.ViewModelProviderFactory
import io.android.ptt.presentation.features.channels.AudioStreamingService
import io.android.ptt.presentation.features.editpassword.EditPasswordActivity
import io.android.ptt.presentation.features.login.LoginActivity
import kotlinx.android.synthetic.main.activity_edit_profile.*
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

class EditProfileActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private lateinit var editProfileViewModel: EditProfileViewModel
    private var picturePath = ""

    private lateinit var profileView: ProfileView
    private var serverImage: MultipartBody.Part? = null
    private val SELECT_FILE = 2

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
        profile_et_username.setText(profileView.name)
        profile_et_email.setText(profileView.email)
        profile_et_phone.setText(profileView.mobile)
        profile_et_ssn.setText(profileView.ssn)
        profile_et_address.setText(profileView.address)
        ccp.setCountryForNameCode(profileView.countryCode)
        setRoundedImage(this, profileView.photo, ivProfile)
        profile_layout_password.setOnClickListener {
            startActivity(EditPasswordActivity.getStartIntent(this))
        }
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
            if (profile_et_username.text.toString().trim().isNotEmpty())
                profileView.name = profile_et_username.text.toString()
            else {
                profile_layout_username.error = getString(R.string.Please_enter_fullname)
                return@setOnClickListener
            }

            if (profile_et_email.text.toString().trim().isNotEmpty())
                profileView.email = profile_et_email.text.toString()
            else {
                profile_layout_username.error = getString(R.string.Please_enter_valid_email)
                return@setOnClickListener
            }

            if (profile_et_phone.text.toString().trim().isNotEmpty())
                profileView.mobile = profile_et_phone.text.toString()
            else {
                profile_layout_phone.error = getString(R.string.Please_enter_mobile)
                return@setOnClickListener
            }

            if (profile_et_ssn.text.toString().trim().isNotEmpty())
                profileView.ssn = profile_et_ssn.text.toString()
            else {
                profile_layout_ssn.error = getString(R.string.Please_enter_ssn)
                return@setOnClickListener
            }

            if (profile_et_address.text.toString().trim().isNotEmpty())
                profileView.address = profile_et_address.text.toString()
            else {
                profile_layout_address.error = getString(R.string.Please_enter_address)
                return@setOnClickListener
            }

            if (picturePath.isNotEmpty()) {
                val file = File(picturePath)
                serverImage = prepareImagePart("photo", file)
                editProfileViewModel.fetchEditProfile(
                    PreferenceControl.loadToken(this),
                    createPartFromString("PUT"),
                    createPartFromString(profileView.name.toString()),
                    createPartFromString(profileView.email.toString()),
                    createPartFromString(profileView.ssn.toString()),
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
                    createPartFromString(profileView.ssn.toString()),
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

}
