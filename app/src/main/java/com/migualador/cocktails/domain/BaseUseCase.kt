package com.migualador.cocktails.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * BaseUseCase
 *
 * Provides a common way of executing use cases, using generic types for both the input data
 * parameters and the output UseCaseResult data
 */
abstract class BaseUseCase<P, R> {
     suspend operator fun invoke(params: P): R =
        withContext(Dispatchers.IO) {
            useCaseContent(params)
        }

    abstract suspend fun useCaseContent(params: P): R

}