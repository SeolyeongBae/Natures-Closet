package com.example.naturesCloset.serviceDirectory

import com.example.naturesCloset.classDirectory.EditResponse
import com.example.naturesCloset.classDirectory.PaletteResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface EditProfileService {

    @FormUrlEncoded
    @POST("/profedit/") //Post로 보내자.
    fun requestEdit(
        @Field("username") username:String,
        @Field("profile") profile:String
    ) : Call<EditResponse> //어떤 값을 받아올 지 정하는 부분

}