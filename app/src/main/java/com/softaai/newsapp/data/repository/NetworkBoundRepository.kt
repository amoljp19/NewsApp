package com.softaai.newsapp.data.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.softaai.newsapp.data.network.Resource
import kotlinx.coroutines.flow.*
import retrofit2.Response

abstract class NetworkBoundRepository<RESULT, REQUEST> {

    fun asFlow() = flow<Resource<RESULT>> {

        emit(Resource.Success(fetchFromLocal().first()))

        val apiResponse = fetchFromRemote()

        val remoteArticles = apiResponse.body()

        if (apiResponse.isSuccessful && remoteArticles != null) {
            saveRemoteData(remoteArticles)
        } else {
            emit(Resource.Failed(apiResponse.message()))
        }

        emitAll(
            fetchFromLocal().map {
                Resource.Success<RESULT>(it)
            }
        )
    }.catch { e ->
        e.printStackTrace()
        emit(Resource.Failed("Something went wrong! Can't get latest articles."))
    }


    @WorkerThread
    protected abstract suspend fun saveRemoteData(response: REQUEST?)


    @MainThread
    protected abstract fun fetchFromLocal(): Flow<RESULT>


    @MainThread
    protected abstract suspend fun  fetchFromRemote(): Response<REQUEST>
}
