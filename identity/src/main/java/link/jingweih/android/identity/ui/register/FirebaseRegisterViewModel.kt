package link.jingweih.android.identity.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import link.jingweih.android.identity.R
import link.jingweih.android.identity.model.User
import link.jingweih.android.identity.usecase.FirebaseRegisterUseCase
import link.jingweih.android.identity.usecase.RegisterInput
import javax.inject.Inject
import link.jingweih.jingwei.core.framework.domain.Result
import link.jingweih.jingwei.core.framework.utils.LoginUtil.isEmailValid
import link.jingweih.jingwei.core.framework.utils.LoginUtil.isPasswordValid
import link.jingweih.jingwei.core.framework.utils.LoginUtil.isSamePassword

@HiltViewModel
internal class FirebaseRegisterViewModel @Inject constructor(
    private val firebaseRegisterUseCase: FirebaseRegisterUseCase
) : ViewModel() {

    private val _registerForm = MutableLiveData(RegisterFormState(isDataValid = false))
    internal val registerFormState: LiveData<RegisterFormState> = _registerForm

    private val _registerUiState = MutableLiveData<RegisterUiState>()
    internal val registerUiState: LiveData<RegisterUiState> = _registerUiState

    internal fun register(email: String, password: String, confirmPassword: String) {
        if (!validateCheck(email, password, confirmPassword)) {
            return
        }
        // can be launched in a separate asynchronous job
        viewModelScope.launch {
            when (val result = firebaseRegisterUseCase(RegisterInput(email, password))) {
                is Result.Success -> _registerUiState.value = RegisterUiState.Success(result.data)
                is Result.Error -> _registerUiState.value =
                    RegisterUiState.Failure(result.exception.message)
            }
        }

    }

    private fun validateCheck(email: String, password: String, confirmPassword: String): Boolean {
        return if (!isEmailValid(email)) {
            _registerForm.value = RegisterFormState(emailError = R.string.invalid_email)
            false
        } else if (!isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
            false
        } else if (!isSamePassword(password, confirmPassword)) {
            _registerForm.value =
                RegisterFormState(confirmPasswordError = R.string.password_not_same)
            false
        } else {
            _registerForm.value = RegisterFormState(isDataValid = true)
            true
        }
    }
}

internal data class RegisterFormState(
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val confirmPasswordError: Int? = null,
    val isDataValid: Boolean = false
)

internal sealed class RegisterUiState {
    data class Success(val user: User) : RegisterUiState()
    data class Failure(val error: String?) : RegisterUiState()
}