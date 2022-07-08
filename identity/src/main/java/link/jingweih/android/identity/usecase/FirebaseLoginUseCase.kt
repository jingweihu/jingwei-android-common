package link.jingweih.android.identity.usecase

import kotlinx.coroutines.CoroutineDispatcher
import link.jingweih.android.identity.exceptions.EmptyUserResponse
import link.jingweih.android.identity.model.User
import link.jingweih.android.identity.repository.FirebaseLoginRepository
import link.jingweih.jingwei.core.framework.concurrent.IODispatcher
import link.jingweih.jingwei.core.framework.domain.BaseUseCase
import javax.inject.Inject

class FirebaseLoginUseCase @Inject constructor(
    private val firebaseLoginRepository: FirebaseLoginRepository,
    @IODispatcher ioDispatcher: CoroutineDispatcher
) :
    BaseUseCase<LoginInput, User>(ioDispatcher) {

    override suspend fun execute(input: LoginInput): User {
       val userResponse = firebaseLoginRepository.login(input.email, input.password)
        return userResponse ?: throw EmptyUserResponse()
    }
}

data class LoginInput(
    val email: String,
    val password: String
)