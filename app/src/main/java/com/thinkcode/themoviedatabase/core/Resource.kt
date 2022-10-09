package com.thinkcode.themoviedatabase.core

sealed class Resource<T>(
    val data: T?=null,
    val message: String? =null
){
    class Success<T>(data: T) : Resource<T>(data)

    // We'll pass error message wrapped in this 'Error'
    // class to the UI in case of failure response
    class Error<T>(message: String, data: T?=null) : Resource<T>(data,message)

    // We'll just pass object of this Loading
    // class, just before making an api call
    class Loading<T> : Resource<T>()

}
