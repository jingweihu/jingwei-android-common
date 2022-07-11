package link.jingweih.android.identity.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import link.jingweih.android.identity.R
import link.jingweih.android.identity.model.User
import link.jingweih.android.identity.usecase.FirebaseLoginUseCase
import link.jingweih.android.identity.usecase.LoginInput
import link.jingweih.jingwei.core.framework.domain.Result
import link.jingweih.jingwei.core.framework.utils.LoginUtil.isEmailValid
import link.jingweih.jingwei.core.framework.utils.LoginUtil.isPasswordValid
import javax.inject.Inject

@HiltViewModel
internal class FirebaseLoginViewModel @Inject constructor(
    private val firebaseLoginUseCase: FirebaseLoginUseCase
) : ViewModel() {
    private val _loginForm = MutableLiveData(LoginFormState(isDataValid = false))
    internal val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginUiState = MutableLiveData<LoginUiState>()
    internal val loginUiState: LiveData<LoginUiState> = _loginUiState

    internal fun login(email: String, password: String) {
        if (!validateCheck(email, password)) {
            return
        }
        // can be launched in a separate asynchronous job
        viewModelScope.launch {
            when (val result = firebaseLoginUseCase(LoginInput(email, password))) {
                is Result.Success -> _loginUiState.value = LoginUiState.Success(result.data)
                is Result.Error -> _loginUiState.value =
                    LoginUiState.Failure(result.exception.message)
            }
        }

    }

    private fun validateCheck(email: String, password: String): Boolean {
        return if (!isEmailValid(email)) {
            _loginForm.value = LoginFormState(emailError = R.string.invalid_email)
            false
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
            false
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
            true
        }
    }
}

internal data class LoginFormState(
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)

internal sealed class LoginUiState {
    data class Success(val user: User) : LoginUiState()
    data class Failure(val error: String?) : LoginUiState()
}