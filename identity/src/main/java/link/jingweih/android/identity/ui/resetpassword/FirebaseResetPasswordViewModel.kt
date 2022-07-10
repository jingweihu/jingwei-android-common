package link.jingweih.android.identity.ui.resetpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import link.jingweih.android.identity.R
import link.jingweih.android.identity.usecase.FirebasePasswordResetUseCase
import link.jingweih.jingwei.core.framework.domain.Result
import link.jingweih.jingwei.core.framework.utils.LoginUtil.isEmailValid
import java.util.*
import java.util.concurrent.TimeUnit.*
import javax.inject.Inject

@HiltViewModel
class FirebaseResetPasswordViewModel @Inject constructor(
    private val resetUseCase: FirebasePasswordResetUseCase
) : ViewModel() {

    private val _resetPasswordFormState = MutableLiveData(
        ResetPasswordFormState(email = "", startCountDownTime = null))
    val resetPasswordFormState: LiveData<ResetPasswordFormState> = _resetPasswordFormState

    private val _resetPasswordUiState = MutableLiveData<ResetPasswordUiState>()
    val resetPasswordUiState: LiveData<ResetPasswordUiState> = _resetPasswordUiState

    fun sendCode(email: String) {
        if (!isEmailValid(email)) {
            _resetPasswordFormState.value =
                ResetPasswordFormState(
                    email = "",
                    emailError = R.string.invalid_email,
                    startCountDownTime = null
                )
            return
        }
        viewModelScope.launch {
            when (val result = resetUseCase(email)) {
                is Result.Success -> {
                    _resetPasswordFormState.value = ResetPasswordFormState(
                        email = email,
                        startCountDownTime = Date().time,
                    )
                    _resetPasswordUiState.value = ResetPasswordUiState.Success
                }
                is Result.Error -> _resetPasswordUiState.value =
                    ResetPasswordUiState.Failure(result.exception.message)
            }
        }
    }

    fun reset() {
        _resetPasswordUiState.value = null
    }
}

data class ResetPasswordFormState(
    val email: String,
    val emailError: Int? = null,
    val startCountDownTime: Long? = null
)

sealed class ResetPasswordUiState {
    object Success : ResetPasswordUiState()
    data class Failure(val error: String?) : ResetPasswordUiState()
}