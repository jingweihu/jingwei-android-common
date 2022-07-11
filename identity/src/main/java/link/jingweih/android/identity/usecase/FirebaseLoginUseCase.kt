package link.jingweih.android.identity.usecase

import kotlinx.coroutines.CoroutineDispatcher
import link.jingweih.android.identity.exceptions.UnknownErrorException
import link.jingweih.android.identity.model.User
import link.jingweih.android.identity.repository.FirebaseLoginRepository
import link.jingweih.jingwei.core.framework.concurrent.IODispatcher
import link.jingweih.jingwei.core.framework.domain.BaseUseCase
import javax.inject.Inject

internal class FirebaseLoginUseCase @Inject constructor(
    private val firebaseLoginRepository: FirebaseLoginRepository,
    @IODispatcher ioDispatcher: CoroutineDispatcher
) : BaseUseCase<LoginInput, User>(ioDispatcher) {

    override suspend fun execute(input: LoginInput): User {
        val authResult = firebaseLoginRepository.login(input.email, input.password)
        return authResult.user?.let { User.fromFirebaseUser(it) } ?: throw UnknownErrorException()
    }
}

internal data class LoginInput(
    val email: String,
    val password: String
)