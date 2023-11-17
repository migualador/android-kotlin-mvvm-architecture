package com.migualador.cocktails.domain

/**
 * UseCaseResult
 *
 */

sealed class UseCaseResult<D> {

    data class Success<D>(val data: D): UseCaseResult<D>()

    data class Error<D>(val error: Throwable): UseCaseResult<D>()
}