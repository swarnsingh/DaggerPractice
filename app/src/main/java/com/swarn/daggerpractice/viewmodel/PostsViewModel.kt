package com.swarn.daggerpractice.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.swarn.daggerpractice.SessionManager
import com.swarn.daggerpractice.api.MainAPI
import com.swarn.daggerpractice.model.Post
import com.swarn.daggerpractice.vo.Resource
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Swarn Singh.
 */
class PostsViewModel @Inject constructor(mainAPI: MainAPI, sessionManager: SessionManager) : ViewModel() {

    private val TAG = PostsViewModel::class.java.canonicalName

    private var sessionManager: SessionManager
    private var mainAPI: MainAPI

    private lateinit var posts: MediatorLiveData<Resource<List<Post>>>

    init {
        Log.d(TAG, "Posts View Model added...")
        this.mainAPI = mainAPI
        this.sessionManager = sessionManager
    }

    fun observePosts(): LiveData<Resource<List<Post>>> {
        if (!::posts.isInitialized) {
            posts = MediatorLiveData()
            posts.value = Resource.loading(null)

            val source = LiveDataReactiveStreams.fromPublisher(
                mainAPI.getPost(sessionManager.getAuthUser().value!!.data!!.id)

                    .onErrorReturn {
                        Log.e(TAG, "onError: ", it)
                        val post = Post()
                        post.id = -1
                        val posts = ArrayList<Post>()
                        posts.add(post)
                        return@onErrorReturn posts
                    }
                    .map {
                        if (it.isNotEmpty()) {
                            if (it[0].id == -1) {
                                return@map Resource.error("Something went wrong...", null)
                            }
                        }
                        return@map Resource.success(it)
                    }
                    .subscribeOn(Schedulers.io())
            )

            posts.addSource(source) {
                posts.value = it
                posts.removeSource(source)
            }
        }

        return posts
    }
}