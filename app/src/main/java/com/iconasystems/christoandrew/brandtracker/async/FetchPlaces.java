package com.iconasystems.christoandrew.brandtracker.async;

import android.os.AsyncTask;
import android.util.Log;

import com.iconasystems.christoandrew.brandtracker.api.ApiService;
import com.iconasystems.christoandrew.brandtracker.api.response.PlacesResponse;
import com.iconasystems.christoandrew.brandtracker.models.Place;

import java.util.List;

import retrofit2.Response;

public class FetchPlaces extends AsyncTask<Void, Void, List<Place>> {
    private ApiService apiService;

    public FetchPlaces(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    protected List<Place> doInBackground(Void... voids) {
        try {
            Response<PlacesResponse> response = apiService.getPlaces().execute();
            assert response.body() != null;
            return response.body().getPlaces();

        }catch (Exception ex){
            System.out.print("Error = "+ex.getMessage());
        }

        return null;
    }

}