package com.iconasystems.christoandrew.brandtracker;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.iconasystems.christoandrew.brandtracker.api.ApiService;
import com.iconasystems.christoandrew.brandtracker.network.AuthInterceptor;
import com.iconasystems.christoandrew.brandtracker.async.FetchPlaces;
import com.iconasystems.christoandrew.brandtracker.models.Token;
import com.iconasystems.christoandrew.brandtracker.viewmodel.PlaceViewModel;
import com.iconasystems.christoandrew.brandtracker.viewmodel.TokenViewModel;

import java.util.Objects;

public class Splash extends AppCompatActivity {
    private ApiService apiClient;
    private static final String TAG = Splash.class.getSimpleName();
    private TokenViewModel tokenViewModel;
    private PlaceViewModel placeViewModel;
    private Token token;
    private AuthInterceptor authInterceptor = null;
    private FetchPlaces fetchPlaces;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_splash);
        placeViewModel = ViewModelProviders.of(this).get(PlaceViewModel.class);
        tokenViewModel = ViewModelProviders.of(this).get(TokenViewModel.class);
        //token = tokenViewModel.getToken();
        //authInterceptor = new AuthInterceptor(token.getToken());
        //this.apiClient = ApiClient.getClient(authInterceptor).create(ApiService.class);

        scheduleSplashScreen();
    }

    private void scheduleSplashScreen() {
        fetchPlaces = new FetchPlaces(apiClient);
        fetchPlaces.execute();

        startActivity(new Intent(getApplicationContext(), SelectLoginType.class));
    }
}
