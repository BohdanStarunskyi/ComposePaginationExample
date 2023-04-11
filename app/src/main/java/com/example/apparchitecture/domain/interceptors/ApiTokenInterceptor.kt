package com.example.apparchitecture.domain.interceptors

import com.example.apparchitecture.data.repository.UserDataRepositoryImpl
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiTokenInterceptor @Inject constructor(
    private val userDataRepositoryImpl: UserDataRepositoryImpl,
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var accessToken: String = ""
        runBlocking {
//            accessToken = userDataRepositoryImpl.getAuthToken() ?: ""
        }
        val request: Request = newRequestWithAccessToken(chain.request(), accessToken)
        return if (accessToken.isEmpty()) chain.proceed(chain.request()) else chain.proceed(request)
    }

    private fun newRequestWithAccessToken(request: Request, accessToken: String): Request {
        return request.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()
    }
}