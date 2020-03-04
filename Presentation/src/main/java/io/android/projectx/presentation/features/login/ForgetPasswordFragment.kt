package io.android.projectx.presentation.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        loginViewModel.login().observe(this,
            Observer<Resource<LoginView>> { it?.let { handleDataState(it) } })


        buSend.setOnClickListener {
            val action =
                ForgetPasswordFragmentDirections.actionForgetPasswordToVerify()
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
