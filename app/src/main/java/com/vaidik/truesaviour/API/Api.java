package com.vaidik.truesaviour.API;


import com.vaidik.truesaviour.models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {


    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> userLogin(
            @Field("username") String username,
            @Field("password") String password
    );

}
