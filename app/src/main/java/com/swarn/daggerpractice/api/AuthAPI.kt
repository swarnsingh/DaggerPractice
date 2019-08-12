package com.swarn.daggerpractice.api

import com.swarn.daggerpractice.model.User
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Swarn Singh.
 */
interface AuthAPI {

    @GET("/users/{id}")
    fun getUser(@Path("id") id: Int): Flowable<User>
}