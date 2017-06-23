package com.example.nguyenvan.getwifiinfo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nguyenvan on 5/17/2017.
 */

public class RecyclerViewListBuildingAdapter extends RecyclerView.Adapter<RecyclerViewListBuildingAdapter.MyViewHolder>{
    private ArrayList<Building> lstBuilding;


    public RecyclerViewListBuildingAdapter(ArrayList<Building> lstBuilding) {
        this.lstBuilding = lstBuilding;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView txtNameBuilding;

        public MyViewHolder(View view) {
            super(view);
            txtNameBuilding = (TextView) view.findViewById(R.id.txtNameBuilding);

        }
    }

    @Override
    public int getItemCount() {
        return lstBuilding.size();
    }

    @Override
    public RecyclerViewListBuildingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.building_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Building building = lstBuilding.get(position);
        holder.txtNameBuilding.setText(building.getNameBuilding());
    }


}
