package com.iconasystems.christoandrew.brandtracker.api;

import com.iconasystems.christoandrew.brandtracker.api.response.AuthResponse;
import com.iconasystems.christoandrew.brandtracker.api.response.PlacesResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @FormUrlEncoded
    @POST("auth/")
    Call<AuthResponse> login(@Field("username") String username,
                             @Field("password") String password);

    @GET("places/")
    Call<PlacesResponse> getPlaces();

}
