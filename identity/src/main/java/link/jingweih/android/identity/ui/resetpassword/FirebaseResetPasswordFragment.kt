package link.jingweih.android.identity.ui.resetpassword

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import link.jingweih.android.identity.R
import link.jingweih.android.identity.databinding.FragmentResetPasswordBinding
import link.jingweih.core.ui.toast.toast
import link.jingweih.jingwei.core.framework.ui.BaseFragment
import java.util.*
import java.util.concurrent.TimeUnit

class FirebaseResetPasswordFragment : BaseFragment<FragmentResetPasswordBinding>() {

    companion object {
        private val COUNTER_TIME = TimeUnit.SECONDS.toMillis(30)
        private val INTERVAL = TimeUnit.SECONDS.toMillis(1)
    }

    private val viewModel: FirebaseResetPasswordViewModel by activityViewModels()
    private var countDownTimer: CountDownTimer? = null
    override fun initView() {
        binding.sendCodeButton.setOnClickListener {
            viewModel.sendCode(binding.resetEmail.text.toString())
        }
    }

    override fun initObserver() {
        viewModel.resetPasswordFormState.observe(this) Observer@{
            val resetFormState = it ?: return@Observer

            if (resetFormState.emailError != null) {
                binding.resetEmail.error = getString(resetFormState.emailError)
            }
            setupTimer(resetFormState)
        }

        viewModel.resetPasswordUiState.observe(this) { uiState ->
            when (uiState) {
                is ResetPasswordUiState.Success -> {
                    toast(
                        message = getString(R.string.reset_email_sent),
                        duration = Toast.LENGTH_LONG
                    )
                    viewModel.reset()
                    findNavController().popBackStack()
                }
                is ResetPasswordUiState.Failure -> {
                    toast(message = uiState.error ?: "Unknown error")
                }
            }
        }
    }

    private fun setupTimer(resetFormState: ResetPasswordFormState) {
        if (resetFormState.startCountDownTime != null) {
            val millisInFuture = resetFormState.startCountDownTime + COUNTER_TIME - Date().time
            if (millisInFuture > 0) {
                countDownTimer?.cancel()
                countDownTimer = object : CountDownTimer(millisInFuture, INTERVAL) {
                    override fun onTick(millisUntilFinished: Long) {
                        binding.sendCodeButton.text = getString(
                            R.string.count_down_time,
                            (millisUntilFinished / INTERVAL).toInt().toString()
                        )
                        binding.headerTitle.text = getString(
                            R.string.reset_password_title_in_progress,
                            resetFormState.email
                        )
                    }

                    override fun onFinish() {
                        binding.headerTitle.text = getString(R.string.reset_password_title)
                        binding.sendCodeButton.text = getString(R.string.send_code)
                        binding.sendCodeButton.isEnabled = true
                    }
                }.start()
                binding.sendCodeButton.isEnabled = false
            } else {
                countDownTimer = null
            }
        } else {
            binding.sendCodeButton.isEnabled = true
            binding.headerTitle.text = getString(R.string.reset_password_title)
        }
    }

    override fun createFragmentViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentResetPasswordBinding {
        return FragmentResetPasswordBinding.inflate(inflater, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
        countDownTimer = null
    }
}