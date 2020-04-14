package com.afaqy.ptt.presentation.features.login

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.android.support.DaggerFragment
import com.afaqy.ptt.presentation.R
import com.afaqy.ptt.presentation.base.PreferenceControl
import com.afaqy.ptt.presentation.base.model.LoginView
import com.afaqy.ptt.presentation.base.state.Resource
import com.afaqy.ptt.presentation.base.state.ResourceState
import com.afaqy.ptt.presentation.di.ViewModelProviderFactory
import com.afaqy.ptt.presentation.features.channels.ChannelsActivity
import kotlinx.android.synthetic.main.fragment_login.*
import java.io.OutputStream
import java.net.Socket
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [LoginFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : DaggerFragment() {

    lateinit var telMgr: TelephonyManager
    var deviceId = ""
    var simSerial = ""

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(LoginViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.login().observe(this,
            Observer<Resource<LoginView>> { it?.let { handleDataState(it) } })

        login_et_username.doOnTextChanged { text, start, count, after ->
            login_layout_username.error = null
        }

        login_et_password.doOnTextChanged { text, start, count, after ->
            login_layout_password.error = null

        }
        buLogin.setOnClickListener {
            if (login_et_username.text.toString().trim().isEmpty() || login_et_password.text.toString().trim().isEmpty()) {
                if (login_et_username.text.toString().trim().isEmpty())
                    login_layout_username.error = getString(R.string.Please_enter_username)
                else
                    login_layout_password.error = getString(R.string.Please_enter_password)
            } else {
                login_layout_username.error = null
                login_layout_password.error = null
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_PHONE_STATE
                    )
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    // We do not have this permission. Let's ask the user
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.READ_PHONE_STATE),
                        100
                    )

                } else {
                    telMgr =
                        requireActivity().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

                    if (Build.VERSION.SDK_INT >= 26) {
                        if (telMgr.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA) {
                            deviceId = telMgr.getMeid();
                        } else if (telMgr.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
                            deviceId = telMgr.getImei(0);
                        } else {
                            deviceId = "" // default!!!
                        }
                    }
                    // simSerial = telMgr.simSerialNumber
                    simSerial = "123456789"
                    loginViewModel.fetchLogin(
                        login_et_username.text.toString(),
                        login_et_password.text.toString(), deviceId, simSerial
                    )
                }
            }
        }

        tvForgetPassword.setOnClickListener {
            val action =
                LoginFragmentDirections.actionLoginToForgetPassword(login_et_username.text.toString())
            findNavController().navigate(action)
        }
    }

    private fun handleDataState(resource: Resource<LoginView>) {
        when (resource.status) {
            ResourceState.SUCCESS -> {
                setupScreenForSuccess(resource.data)
            }
            ResourceState.LOADING -> {
                progressView.visibility = View.VISIBLE
            }
            ResourceState.ERROR -> {
                progressView.visibility = View.GONE
                if (resource.message.equals("retrofit2.adapter.rxjava2.HttpException: HTTP 422 Unprocessable Entity"))
                    Toast.makeText(requireContext(), getString(R.string.invalid_username_or_password), Toast.LENGTH_LONG).show()
                else
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupScreenForSuccess(loginView: LoginView?) {
        activity?.finish()
        progressView.visibility = View.GONE
        loginView?.let {
            PreferenceControl.saveToken(requireContext(), "Bearer " + loginView.accessToken)
            startActivity(ChannelsActivity.getStartIntent(requireContext()))
        } ?: run {

        }
    }

    class SomeTask() : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg params: Void?): String? {
            val socket = Socket("192.168.1.191", 1232)
            val bytes = ByteArray(10)
            bytes[1] = 5
            val outputStream: OutputStream = socket.getOutputStream()
            outputStream.write(bytes)
            outputStream.flush()
            outputStream.close()
            socket.close()
            return "done"
        }

        override fun onPreExecute() {
            super.onPreExecute()
            // ...
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            // ...
        }
    }

}
