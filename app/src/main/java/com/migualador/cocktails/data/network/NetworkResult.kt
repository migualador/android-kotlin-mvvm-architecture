package com.migualador.cocktails.data.network

sealed class NetworkResult<D> {
    class Success<D>(val data: D): NetworkResult<D>()
    class HttpError<D>(val httpStatus: Int): NetworkResult<D>()
    class ConnectionError<D>: NetworkResult<D>()
}