package com.iconasystems.christoandrew.brandtracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.iconasystems.christoandrew.brandtracker.adapters.PlacesAdapter;
import com.iconasystems.christoandrew.brandtracker.api.ApiClient;
import com.iconasystems.christoandrew.brandtracker.api.ApiService;
import com.iconasystems.christoandrew.brandtracker.network.AuthInterceptor;
import com.iconasystems.christoandrew.brandtracker.models.Place;
import com.iconasystems.christoandrew.brandtracker.viewmodel.PlaceViewModel;
import com.iconasystems.christoandrew.brandtracker.viewmodel.TokenViewModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    List<Place> places = new ArrayList<>();
    boolean isCheckedIn = false;
    private TokenViewModel tokenViewModel;
    private PlaceViewModel placeViewModel;
    private ApiService apiService;
    private AuthInterceptor authInterceptor = null;
    private List<Place> dbPlaces = new ArrayList<Place>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        placeViewModel = ViewModelProviders.of(this).get(PlaceViewModel.class);

        tokenViewModel = ViewModelProviders.of(this).get(TokenViewModel.class);
        if (getIntent().getStringExtra("is_checked_in") != null) {
            isCheckedIn = Boolean.parseBoolean(getIntent().getStringExtra("is_checked_in"));
            Log.d(TAG, "Is Checked In ? ".concat(getIntent().getStringExtra("is_checked_in")));
        }

        authInterceptor = new AuthInterceptor(tokenViewModel.getToken().getToken());
        apiService = ApiClient.getClient(authInterceptor).create(ApiService.class);

        View recyclerView = findViewById(R.id.bar_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        try{
            dbPlaces = placeViewModel.getPlaces();
        }catch (Exception ex){
            Log.e(TAG, " Error => "+ ex.getMessage());
        }


        places.add(new Place(1, "Monot Place & Lounge", "Bugolobi-Kampala"));
        places.add(new Place(2, "Fame Lounge", "Kololo-Kampala"));
        places.add(new Place(3, "The Wave", "Kololo-Kampala"));
        places.add(new Place(4, "Bugatti Place", "Ntinda-Kampala"));
        places.add(new Place(5, "The Place Place", "Mengo-Kampala"));
        places.add(new Place(6, "Alchemist", "Bugolobi-Kampala"));
        places.add(new Place(7, "Mad hatters", "Bugolobi-Kampala"));
        places.add(new Place(8, "Kyandondo Rugby Club", "Kyadondo-Kampala"));
        places.add(new Place(9, "Liquid Silk", "Bugolobi-Kampala"));
        places.add(new Place(10, "Casablanca Place & Lounge", "Kololo-Kampala"));
        places.add(new Place(11, "Sky Lounge", "Kololo-Kampala"));
        places.add(new Place(12, "Club Amnesia", "Nakasero-Kampala"));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        PlacesAdapter.OnItemClickListener onItemClickListener = null;
        if (!isCheckedIn) {
            onItemClickListener = new PlacesAdapter.OnItemClickListener() {

                @Override
                public void onItemClick(Place place) {
                    Intent checkIn = new Intent(getApplicationContext(), Checkin.class);
                    checkIn.putExtra("place", place.getName());
                    startActivity(checkIn);
                }
            };

        }
        recyclerView.setAdapter(new PlacesAdapter(this.places, onItemClickListener));
    }

    public static class CheckoutDialogFragment extends DialogFragment {

        public static CheckoutDialogFragment newInstance() {
            CheckoutDialogFragment frag = new CheckoutDialogFragment();
            Bundle args = new Bundle();
            args.putString("title", "Do you want to checkout?");
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            return new AlertDialog.Builder(getActivity())
                    .setTitle("Do you want to checkout?")
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    startActivity(new Intent(getActivity(), LoginActivity.class));
                                }
                            }
                    )
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                }
                            }
                    )
                    .create();
        }
    }

    void showDialog() {
        DialogFragment newFragment = CheckoutDialogFragment.newInstance();
        newFragment.show(getSupportFragmentManager(), "dialog");
    }

}
