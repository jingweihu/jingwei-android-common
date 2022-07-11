package link.jingweih.jingwei.core.framework.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class BaseUseCase<in Input, out R : Any>(private val defaultDispatcher: CoroutineDispatcher) {

    @Suppress("TooGenericExceptionCaught")
    suspend operator fun invoke(input: Input): Result<R> {
        return withContext(defaultDispatcher) {
            try {
                execute(input).let {
                    Result.Success(it)
                }
            } catch (e: Exception) {
//            if (e is CancellationException) {
//                Result.Error()
//            }
//            // Allow the coroutine to be cancelled and not execute any further.
//            allowCancel(e)

                Result.Error(e)
            }

        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(input: Input): R
}