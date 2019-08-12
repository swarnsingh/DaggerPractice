package com.swarn.daggerpractice.api

import com.swarn.daggerpractice.model.Post
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Swarn Singh.
 */
interface MainAPI {
    @GET("posts")
    fun getPost(@Query("userId") id: Int): Flowable<List<Post>>
}