package com.iconasystems.christoandrew.brandtracker.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.iconasystems.christoandrew.brandtracker.models.Place;

import java.util.List;

@Dao
public interface PlaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Place place);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlaces(Place... places);

    @Delete
    void delete(Place place);

    @Query("DELETE FROM places WHERE id=:id")
    void deleteId(int id);

    @Query("SELECT * FROM places")
    List<Place> getPlaces();

    @Update
    void update(Place place);

    @Query("DELETE FROM places")
    void deleteAll();


}
