package com.example.naturesCloset.serviceDirectory

import com.example.naturesCloset.classDirectory.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SaveMyPaletteService{

    @FormUrlEncoded
    @POST("/save/") //Post로 보내자.
    fun requestLogin(
        @Field("username") username:String,
        @Field("palettename") palettename:String,
        @Field("color1") color1:String,
        @Field("color2") color2:String,
        @Field("color3") color3:String,
        @Field("color4") color4:String,
        @Field("color5") color5:String,
        @Field("color6") color6:String,
        @Field("upcolor") upcolor:String,
        @Field("downcolor")downcolor:String,
    ) : Call<LoginResponse> //어떤 값을 받아올 지 정하는 부분

}