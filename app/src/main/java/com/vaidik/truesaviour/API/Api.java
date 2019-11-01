package com.vaidik.truesaviour.API;


import com.vaidik.truesaviour.models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {


    @POST("api/login/")
    Call<LoginResponse> userLogin(
            @Body LoginResponse login
          /*@B("username") String username,
            @Field("password") String password
    */);

}
