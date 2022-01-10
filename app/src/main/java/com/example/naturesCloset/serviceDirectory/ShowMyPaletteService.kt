package com.example.naturesCloset.serviceDirectory

import com.example.naturesCloset.classDirectory.LoginResponse
import com.example.naturesCloset.classDirectory.PaletteResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ShowMyPaletteService{

    @FormUrlEncoded
    @POST("/show/") //Post로 보내자.
    fun requestPalette(
        @Field("username") username:String
    ) : Call<PaletteResponse> //어떤 값을 받아올 지 정하는 부분

}