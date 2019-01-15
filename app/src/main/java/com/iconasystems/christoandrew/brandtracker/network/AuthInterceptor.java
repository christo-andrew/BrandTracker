package com.iconasystems.christoandrew.brandtracker.network;

import android.support.annotation.NonNull;

import com.iconasystems.christoandrew.brandtracker.models.Token;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private String token;

    public AuthInterceptor(String token) {
        this.token = token;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder();
        requestBuilder.addHeader("Authorization", "Token ".concat(this.token));
        Request request = requestBuilder.build();

        return chain.proceed(request);
    }
}
