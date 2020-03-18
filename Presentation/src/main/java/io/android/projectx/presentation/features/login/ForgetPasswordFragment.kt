package io.android.projectx.presentation.features.login

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.android.support.DaggerFragment
import io.android.projectx.presentation.R
import io.android.projectx.presentation.base.model.ForgetPasswordView
import io.android.projectx.presentation.base.state.Resource
import io.android.projectx.presentation.base.state.ResourceState
import io.android.projectx.presentation.di.ViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_forget_password.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ForgetPasswordFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ForgetPasswordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForgetPasswordFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private lateinit var loginViewModel: LoginViewModel
    var simSerial = ""
    lateinit var telMgr: TelephonyManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(LoginViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forget_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel.getVerificationCode().observe(this,
            Observer<Resource<ForgetPasswordView>> { it?.let { handleDataState(it) } })


        buGetCode.setOnClickListener {
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

                 simSerial = telMgr.simSerialNumber
                loginViewModel.fetchVerificationCode("a@a.a")
            }
        }
    }

    private fun handleDataState(resource: Resource<ForgetPasswordView>) {
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

    private fun setupScreenForSuccess(response: ForgetPasswordView?) {
        progressView.visibility = View.GONE
        Toast.makeText(requireContext(), response?.message, Toast.LENGTH_LONG).show()
        val action =
            ForgetPasswordFragmentDirections.actionForgetPasswordToVerify()
        findNavController().navigate(action)
    }

}
