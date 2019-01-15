package com.iconasystems.christoandrew.brandtracker.repository.place;

import android.os.AsyncTask;

import com.iconasystems.christoandrew.brandtracker.db.PlaceDao;
import com.iconasystems.christoandrew.brandtracker.models.Place;

import java.util.List;

public class insertPlaceAsync  extends AsyncTask<Place, Void, Void> {
    private PlaceDao asyncTaskDao;
    public insertPlaceAsync(PlaceDao placeDao) {
        this.asyncTaskDao = placeDao;
    }


    @Override
    protected Void doInBackground(Place... places) {
        if (places.length > 1)
            for(Place place : places)
                this.asyncTaskDao.insert(place);

        return null;

    }
}
