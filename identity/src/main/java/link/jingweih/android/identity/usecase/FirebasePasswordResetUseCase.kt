package link.jingweih.android.identity.usecase

import kotlinx.coroutines.CoroutineDispatcher
import link.jingweih.android.identity.repository.FirebaseLoginRepository
import link.jingweih.jingwei.core.framework.concurrent.IODispatcher
import link.jingweih.jingwei.core.framework.domain.BaseUseCase
import javax.inject.Inject

class FirebasePasswordResetUseCase @Inject constructor(
    @IODispatcher ioDispatcher: CoroutineDispatcher,
    private val loginRepository: FirebaseLoginRepository
) : BaseUseCase<String, Boolean>(ioDispatcher) {
    override suspend fun execute(input: String): Boolean {
        return loginRepository.sendPasswordResetEmail(email = input)
    }
}