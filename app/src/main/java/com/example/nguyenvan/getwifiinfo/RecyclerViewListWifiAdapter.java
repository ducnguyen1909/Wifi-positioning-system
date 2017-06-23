package com.example.nguyenvan.getwifiinfo;

import android.net.wifi.ScanResult;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nguyenvan on 4/28/2017.
 */

public class RecyclerViewListWifiAdapter extends RecyclerView.Adapter<RecyclerViewListWifiAdapter.MyViewHolder>{

    private List<Wifi> mScanResults;

    public RecyclerViewListWifiAdapter(List<Wifi> mScanResults) {
        this.mScanResults = mScanResults;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView txtWifiName;

        public MyViewHolder(View view) {
            super(view);
            txtWifiName = (TextView) view.findViewById(R.id.txtWifiName);

        }
    }

    @Override
    public int getItemCount() {
        return mScanResults.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wifi_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Wifi wifi = mScanResults.get(position);
        holder.txtWifiName.setText(wifi.getName());

    }
}
