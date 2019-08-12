package com.swarn.daggerpractice.vo


import com.swarn.daggerpractice.vo.Resource.Status.*

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */

/**
 * @author Swarn Singh.
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }

        fun <T> authenticated(data: T?): Resource<T> {
            return Resource(AUTHENTICATED, data, null)
        }

        fun <T> logout(): Resource<T> {
            return Resource(NOT_AUTHENTICATED, null, null)
        }
    }

    enum class Status {
        AUTHENTICATED,
        NOT_AUTHENTICATED,
        SUCCESS,
        ERROR,
        LOADING
    }
}
