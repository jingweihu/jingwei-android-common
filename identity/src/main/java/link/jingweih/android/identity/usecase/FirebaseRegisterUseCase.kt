package link.jingweih.android.identity.usecase

import kotlinx.coroutines.CoroutineDispatcher
import link.jingweih.android.identity.exceptions.UnknownErrorException
import link.jingweih.android.identity.model.User
import link.jingweih.android.identity.repository.FirebaseLoginRepository
import link.jingweih.jingwei.core.framework.concurrent.IODispatcher
import link.jingweih.jingwei.core.framework.domain.BaseUseCase
import javax.inject.Inject

internal class FirebaseRegisterUseCase @Inject constructor(
    private val firebaseLoginRepository: FirebaseLoginRepository,
    @IODispatcher ioDispatcher: CoroutineDispatcher
) :
    BaseUseCase<RegisterInput, User>(ioDispatcher) {

    override suspend fun execute(input: RegisterInput): User {
        val authResult = firebaseLoginRepository.register(input.email, input.password)
        return authResult.user?.let { User.fromFirebaseUser(it) } ?: throw UnknownErrorException()
    }
}

internal data class RegisterInput(
    val email: String,
    val password: String
)