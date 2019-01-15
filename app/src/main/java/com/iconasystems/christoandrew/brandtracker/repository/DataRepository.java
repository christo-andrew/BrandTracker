package com.iconasystems.christoandrew.brandtracker.repository;

import android.app.Application;

import com.iconasystems.christoandrew.brandtracker.db.AppDatabase;
import com.iconasystems.christoandrew.brandtracker.db.PlaceDao;
import com.iconasystems.christoandrew.brandtracker.db.TokenDao;
import com.iconasystems.christoandrew.brandtracker.models.Place;
import com.iconasystems.christoandrew.brandtracker.models.Token;
import com.iconasystems.christoandrew.brandtracker.repository.auth.insertTokenAsync;
import com.iconasystems.christoandrew.brandtracker.repository.place.insertPlaceAsync;

import java.util.List;

public class DataRepository {
    private PlaceDao placeDao;
    private TokenDao tokenDao;
    private Token token;
    private List<Place> places;

    public DataRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        placeDao = database.placeDao();
        tokenDao = database.tokenDao();
        places = placeDao.getPlaces();
        token = tokenDao.getToken();
    }

    public List<Place> getPlaces(){
        return this.places;
    }
    
    public void insertPlace(Place... place){
        new insertPlaceAsync(placeDao).execute(place);
    }

    public void insertToken(Token token){
        new insertTokenAsync(tokenDao).execute(token);
    }

    public Token getToken() {
        return this.token;
    }
}
