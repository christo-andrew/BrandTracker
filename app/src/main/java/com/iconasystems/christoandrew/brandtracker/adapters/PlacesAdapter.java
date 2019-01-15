package com.iconasystems.christoandrew.brandtracker.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iconasystems.christoandrew.brandtracker.R;
import com.iconasystems.christoandrew.brandtracker.models.Place;

import java.util.List;


public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private final List<Place> mValues;

    public PlacesAdapter(List<Place> items, OnItemClickListener onItemClickListener) {
        mValues = items;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bar_list_content, parent, false);
        return new ViewHolder(parent.getContext(), view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlacesAdapter.ViewHolder holder, int position) {
        holder.bind(mValues.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mPubName;
        final TextView mLocation;
        final View view;

        ViewHolder(final Context context, View view) {
            super(view);
            this.view  = view;
            mPubName = view.findViewById(R.id.pub_name);
            mLocation = view.findViewById(R.id.location);
            Typeface fontBold = Typeface.createFromAsset(context.getAssets(),
                    "fonts/Raleway/Raleway-Bold.ttf");
            Typeface fontMedium = Typeface.createFromAsset(context.getAssets(),
                    "fonts/Raleway/Raleway-Medium.ttf");

            mPubName.setTypeface(fontBold);
            mLocation.setTypeface(fontMedium);
        }

        void bind(final Place place, final OnItemClickListener onItemClickListener) {
            mPubName.setText(place.getName());
            mLocation.setText(place.getLocation());
            this.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(place);
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Place place);
    }
}
