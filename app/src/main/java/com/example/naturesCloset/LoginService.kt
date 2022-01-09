import com.example.naturesCloset.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService{

    @FormUrlEncoded
    @POST("/login/") //Post로 보내자.
    fun requestLogin(
        @Field("userid") userid:String,
        @Field("userpw") userpw:String
    ) : Call<LoginResponse> //어떤 값을 받아올 지 정하는 부분

}