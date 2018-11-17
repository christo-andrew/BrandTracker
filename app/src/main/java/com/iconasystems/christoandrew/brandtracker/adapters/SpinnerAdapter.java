package com.iconasystems.christoandrew.brandtracker.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.iconasystems.christoandrew.brandtracker.R;
import com.iconasystems.christoandrew.brandtracker.models.Person;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<Person> {
    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List<Person> items;
    private final int mResource;
    private static final String TAG = SpinnerAdapter.class.getSimpleName();

    public SpinnerAdapter(@NonNull Context context, @LayoutRes int resource,
                          @NonNull List objects) {
        super(context, resource, 0, objects);

        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        items = objects;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);

    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        final View view = mInflater.inflate(mResource, parent, false);
        TextView name = view.findViewById(R.id.name);
        Log.d(TAG, "Name => "+items.get(position).getFirstName());
        name.setText(items.get(position).getName());
        Typeface font = Typeface.createFromAsset(mContext.getAssets(),
                "fonts/Raleway/Raleway-Medium.ttf");
        name.setTypeface(font);

        view.setPadding(0, view.getPaddingTop(), 0,
                view.getPaddingBottom());

        return view;
    }
}
