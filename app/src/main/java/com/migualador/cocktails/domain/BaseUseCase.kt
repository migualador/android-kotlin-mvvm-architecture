package com.migualador.cocktails.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * BaseUseCase
 *
 */
abstract class BaseUseCase<P, R> : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun execute(params: P, onResult: (UseCaseResult<R>) -> Unit = {})  {

        launch {
            val result = withContext(Dispatchers.IO) {
                useCaseContent(params)
            }
            onResult(result)

        }
    }

    abstract suspend fun useCaseContent(params: P): UseCaseResult<R>
    
}