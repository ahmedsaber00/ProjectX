package io.android.projectx.presentation.features.login

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
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
import io.android.projectx.presentation.R
import io.android.projectx.presentation.base.model.LoginView
import io.android.projectx.presentation.base.state.Resource
import io.android.projectx.presentation.base.state.ResourceState
import io.android.projectx.presentation.di.ViewModelProviderFactory
import io.android.projectx.presentation.features.MainActivity
import kotlinx.android.synthetic.main.fragment_login.*
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

                    loginViewModel.fetchLogin(
                        login_et_username.text.toString(),
                        login_et_password.text.toString(), deviceId
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
                Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupScreenForSuccess(recipes: LoginView?) {
        activity?.finish()
        progressView.visibility = View.GONE
        recipes?.let {
            startActivity(MainActivity.getStartIntent(requireContext()))
        } ?: run {

        }
    }

}
