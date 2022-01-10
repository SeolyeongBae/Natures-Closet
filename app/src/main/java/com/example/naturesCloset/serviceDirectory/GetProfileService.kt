package com.example.naturesCloset.serviceDirectory

import com.example.naturesCloset.classDirectory.LoginResponse
import com.example.naturesCloset.classDirectory.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query

interface GetProfileService {

    @FormUrlEncoded
    @GET("posts")
    fun getProfile(@Query("userId") userId: Int): Call<LoginResponse>

}

/* 예제

    @GET("posts/1")
    fun getUser(): Call<User>

    @GET("posts/{page}")
    fun getUserPage(@Path("page") page: String): Call<User>


   @GET("posts/1")
  fun getStudent(@Query("school_id") schoolId: Int,
                 @Query("grade") grade: Int,
                 @Query("classroom") classroom: Int): Call<ExampleResponse>
 */