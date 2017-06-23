package com.example.nguyenvan.getwifiinfo;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private WifiManager mWifiManager;
    private RecyclerView recyclerViewListWifi;
    private RecyclerViewListWifiAdapter mAdapter;

    private RecyclerView recyclerViewListBuilding;
    private RecyclerViewListBuildingAdapter mBuildingAdapter;

    private DBLocaltion dbLocaltion;
    //private ArrayList<String> lstNameWifi;
    private DBWifi dbWifi;
    private Building build;
    private Wifi wifi;
    private ArrayList<Building> lstBuild;
    private Double[] arrayDis;
    private EditText editBuidling;
    private Button btnGetRouter, btnSave, btnClear;
    private List<Wifi> listWifi;

    private Building building;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        dbWifi = new DBWifi(getApplicationContext());
        dbLocaltion = new DBLocaltion(getApplicationContext());

        lstBuild = dbLocaltion.getBuilding();
        for(int i=0; i < lstBuild.size() ; i++){
            Log.e("Building ",lstBuild.get(i).getNameBuilding() + " " + lstBuild.get(i).getDistance1()+ " " + lstBuild.get(i).getDistance2()+ " " + lstBuild.get(i).getDistance3()+ " " + lstBuild.get(i).getDistance4() );
        }
        mWifiManager.startScan();

        editBuidling = (EditText) findViewById(R.id.editBuilding);
        editBuidling.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0){
                    btnSave.setEnabled(false);
                } else {
                    btnSave.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btnGetRouter = (Button) findViewById(R.id.btnGetRouter);
        recyclerViewListWifi = (RecyclerView) findViewById(R.id.recyclerViewListWifi);
        setAdapterListWifi();
        recyclerViewListBuilding = (RecyclerView) findViewById(R.id.recyclerViewListBuilding);
        setAdapterListBuilding();

        getWifiFromDB();
        getBuildingFromDB();
        btnGetRouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbWifi.deleteWifi();
                dbLocaltion.deleteDBLocation();
                getWifiFromDB();
                mAdapter.notifyDataSetChanged();
                getWifi();
                String stringWifi = convertListToString(listWifi);
                dbWifi.addWifi(stringWifi);

                recyclerViewListWifi.setVisibility(View.VISIBLE);
                mAdapter.notifyDataSetChanged();

            }
        });
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWifiManager.startScan();

                String textBuilding = editBuidling.getText().toString();
                Double d1 = 999999.0;
                Double d2 = 999999.0;
                Double d3 = 999999.0;
                Double d4 = 999999.0;
                List<ScanResult> mScanResults = mWifiManager.getScanResults();
                for (int i = 0; i < mScanResults.size(); i++){

                    Wifi wifi = new Wifi(mScanResults.get(i).SSID);
                    for(int j = 0 ; j < listWifi.size() ; j ++){
                        if(wifi.getName().equals(listWifi.get(j).getName())){
                            if(j==1){
                                d1 = calculateDistance(mScanResults.get(i).level,mScanResults.get(i).frequency);
                            }
                            if(j==2){
                                d2 = calculateDistance(mScanResults.get(i).level,mScanResults.get(i).frequency);
                            }
                            if(j==3){
                                d3 = calculateDistance(mScanResults.get(i).level,mScanResults.get(i).frequency);
                            }
                            if(j==4){
                                d4 = calculateDistance(mScanResults.get(i).level,mScanResults.get(i).frequency);
                            }



                        }
                    }

                }
                building = new Building(textBuilding,d1,d2,d3,d4);
                dbLocaltion.addLocation(building);
                lstBuild = dbLocaltion.getBuilding();
                Toast.makeText(MainActivity.this,"You added " + textBuilding + " successfully!!",Toast.LENGTH_SHORT).show();
                mBuildingAdapter.notifyDataSetChanged();

            }
        });
        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editBuidling.getText().clear();
            }
        });


    }
    private void getBuildingFromDB(){
        lstBuild = dbLocaltion.getBuilding();
        mBuildingAdapter.notifyDataSetChanged();
        recyclerViewListBuilding.setVisibility(View.VISIBLE);
    }

    private void getWifiFromDB(){
        String kq = dbWifi.getWifi();
        String[] listKQ = kq.split(";");
        listWifi.clear();
        for(int i = 0 ; i < listKQ.length ; i++){
            Wifi wifi = new Wifi(listKQ[i]);
            listWifi.add(wifi);
        }
        mAdapter.notifyDataSetChanged();
        recyclerViewListWifi.setVisibility(View.VISIBLE);
    }


    private void getWifi(){
        List<ScanResult> mScanResults = mWifiManager.getScanResults();

        dbWifi = new DBWifi(getApplicationContext());
        for (int i = 0; i < mScanResults.size(); i++){

            if(i > 3){
                break;
            }
            Wifi wifi = new Wifi(mScanResults.get(i).SSID);
            listWifi.add(wifi);
        }
    }


    private String convertListToString(List<Wifi> list){
        String stringWifi = "";
        for(int i = 0 ; i < list.size() ; i++){
            if(i < list.size() - 1){
                stringWifi = stringWifi + list.get(i).getName() + ";";
            }else{
                stringWifi = stringWifi + list.get(i).getName();
            }
        }
        return stringWifi;
    }


    private void setAdapterListWifi(){
        listWifi = new ArrayList<>();
        mAdapter = new RecyclerViewListWifiAdapter(listWifi);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewListWifi.setLayoutManager(mLayoutManager);
        recyclerViewListWifi.setItemAnimator(new DefaultItemAnimator());
        recyclerViewListWifi.setAdapter(mAdapter);
    }
    private void setAdapterListBuilding(){
        lstBuild = new ArrayList<>();
        mBuildingAdapter = new RecyclerViewListBuildingAdapter(lstBuild);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewListBuilding.setLayoutManager(mLayoutManager);
        recyclerViewListBuilding.setItemAnimator(new DefaultItemAnimator());
        recyclerViewListBuilding.setAdapter(mBuildingAdapter);
    }

    public double calculateDistance(double signallevelIndb, double freqInMHz){
        double exp = (27.55 -(20*Math.log10(freqInMHz)) + Math.abs(signallevelIndb)) / 20.0;
        return Math.pow(10.0,exp);
    }



}
