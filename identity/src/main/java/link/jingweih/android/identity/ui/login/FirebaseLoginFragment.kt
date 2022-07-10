package link.jingweih.android.identity.ui.login

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import link.jingweih.android.identity.R
import link.jingweih.android.identity.databinding.FragmentLoginBinding
import link.jingweih.android.identity.ui.EXTRA_APPLICATION_NAME
import link.jingweih.core.ui.toast.toast
import link.jingweih.jingwei.core.framework.exts.applyText
import link.jingweih.jingwei.core.framework.exts.getIntent
import link.jingweih.jingwei.core.framework.ui.BaseFragment

@AndroidEntryPoint
internal class FirebaseLoginFragment : BaseFragment<FragmentLoginBinding>() {
    private val viewModel: FirebaseLoginViewModel by viewModels()

    override fun initView() {

        binding.headerTitle.applyText(getIntent()?.getStringExtra(EXTRA_APPLICATION_NAME))
        binding.doNotHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.resetPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_resetPasswordFragment)
        }

        binding.loginEmail.doAfterTextChanged {
            binding.loginButton.isEnabled = true
        }

        binding.loginPassword.doAfterTextChanged {
            binding.loginButton.isEnabled = true
        }

        binding.loginButton.apply {
            setOnClickListener {
                viewModel.login(
                    binding.loginEmail.text.toString(),
                    binding.loginPassword.text.toString()
                )
            }
        }
    }

    override fun initObserver() {
        viewModel.loginFormState.observe(this) Observer@{
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            binding.loginButton.isEnabled = loginState.isDataValid

            if (loginState.emailError != null) {
                binding.loginEmail.error = getString(loginState.emailError)
            }
            if (loginState.passwordError != null) {
                binding.loginPassword.error = getString(loginState.passwordError)
            }
        }

        viewModel.loginUiState.observe(this) { uiState ->
            when (uiState) {
                is LoginUiState.Success -> {
                    activity?.apply {
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                }
                is LoginUiState.Failure -> {
                    toast(message = uiState.error ?: "Unknown error")
                }
            }
        }
    }

    override fun createFragmentViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }
}