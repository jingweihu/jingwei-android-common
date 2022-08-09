package link.jingweih.jingwei.core.framework.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flowOn

abstract class BaseFlowUseCase<in Input, out R : Any>(private val defaultDispatcher: CoroutineDispatcher) {

    @Suppress("TooGenericExceptionCaught")
    operator fun invoke(input: Input): Flow<R> {
        return execute(input).cancellable().flowOn(defaultDispatcher)
    }

    @Throws(RuntimeException::class)
    protected abstract fun execute(input: Input): Flow<R>
}