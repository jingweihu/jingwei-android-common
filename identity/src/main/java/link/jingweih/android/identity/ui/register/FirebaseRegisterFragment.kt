package link.jingweih.android.identity.ui.register

import android.app.Activity.RESULT_OK
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import link.jingweih.android.identity.databinding.FragmentRegisterBinding
import link.jingweih.android.identity.ui.EXTRA_APPLICATION_NAME
import link.jingweih.jingwei.core.framework.ui.BaseFragment
import link.jingweih.jingwei.core.framework.exts.applyText
import link.jingweih.jingwei.core.framework.exts.getIntent

@AndroidEntryPoint
class FirebaseRegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    private val viewModel: FirebaseRegisterViewModel by viewModels()

    override fun initView() {
        binding.headerTitle.applyText(getIntent()?.getStringExtra(EXTRA_APPLICATION_NAME))

        binding.alreadyHaveAccount.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.signupEmail.doAfterTextChanged {
            binding.signupButton.isEnabled = true
        }

        binding.signupPassword.doAfterTextChanged {
            binding.signupButton.isEnabled = true
        }

        binding.signupButton.setOnClickListener {
            viewModel.register(
                binding.signupEmail.text.toString(),
                binding.signupPassword.text.toString(),
                binding.signupConfirmPassword.text.toString()
            )
        }
    }

    override fun initObserver() {
        viewModel.registerFormState.observe(this) Observer@{
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            binding.signupButton.isEnabled = loginState.isDataValid

            if (loginState.emailError != null) {
                binding.signupEmail.error = getString(loginState.emailError)
            }
            if (loginState.passwordError != null) {
                binding.signupPassword.error = getString(loginState.passwordError)
            }
            if (loginState.confirmPasswordError != null) {
                binding.signupConfirmPassword.error = getString(loginState.confirmPasswordError)
            }
        }

        viewModel.registerUiState.observe(this) { uiState ->
            when (uiState) {
                is RegisterUiState.Success -> {
                    activity?.apply {
                        setResult(RESULT_OK)
                        finish()
                    }
                }
                is RegisterUiState.Failure -> {
                    activity?.apply {
                        finish()
                    }
                }
            }
        }
    }

    override fun createFragmentViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(inflater, container, false)
    }
}