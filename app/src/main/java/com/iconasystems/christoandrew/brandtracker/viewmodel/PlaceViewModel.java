package com.iconasystems.christoandrew.brandtracker.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.iconasystems.christoandrew.brandtracker.models.Place;
import com.iconasystems.christoandrew.brandtracker.repository.DataRepository;

import java.util.List;

public class PlaceViewModel extends AndroidViewModel {
    private DataRepository dataRepository;
    private List<Place> places;
    public PlaceViewModel(@NonNull Application application) {
        super(application);
        this.dataRepository = new DataRepository(application);
        this.places = dataRepository.getPlaces();
    }

    public List<Place> getPlaces() {
        return this.places;
    }

    public void insertPlace(Place place){
        this.dataRepository.insertPlace(place);
    }

    public void insertPlaces(Place... places){
        this.dataRepository.insertPlace(places);
    }
}
