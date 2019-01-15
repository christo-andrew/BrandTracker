package com.iconasystems.christoandrew.brandtracker.repository.place;

import android.os.AsyncTask;

import com.iconasystems.christoandrew.brandtracker.db.AppDatabase;
import com.iconasystems.christoandrew.brandtracker.db.PlaceDao;
import com.iconasystems.christoandrew.brandtracker.models.Place;

import java.util.List;

public class populatePlacesAsync extends AsyncTask<Place, Void, Void> {
    private final PlaceDao placeDao;

    public populatePlacesAsync(AppDatabase appDatabase) {
        this.placeDao = appDatabase.placeDao();
    }

    @Override
    protected Void doInBackground(Place... places) {
        placeDao.deleteAll();
        placeDao.insert(new Place(1,"Panamera Bar and Lounge", "Naguru - Kampala"));
        return null;
    }
}
