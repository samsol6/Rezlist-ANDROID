package com.example.e_tecklaptop.testproject.Api;


import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by E-Teck Laptop on 6/7/2017.
 */

public interface ResInterface {


    @FormUrlEncoded
    @POST("signin")
    Call<SiginApi> signInUser(@Field("password") String field2 , @Field("email") String field1);


    @FormUrlEncoded
    @POST("signup")
    Call<SignUpApi> signupUser( @Field("email") String field1 ,@Field("password") String field2 , @Field("password_confirmation") String field3);


    @DELETE("signout")
    Call<SignoutApi> signoutUser();

    @FormUrlEncoded
    @POST("export")
    Call<SendEmailApi> sendEmail(@Field("email") String field1);


}
