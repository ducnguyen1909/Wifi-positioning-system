package com.example.nguyenvan.getwifiinfo;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Nguyenvan on 5/14/2017.
 */

public class MyLocationActivity extends AppCompatActivity {

    private static final String TAG = "MyLocationActivity";

    public static final int MY_REQUEST_CODE = 100;
    private WifiManager mWifiManager;
    private DBLocaltion dbLocaltion;
    private ArrayList<DistancePoint> lstPoint;
    private ArrayList<Building> lstBuild;
    private ArrayList<Wifi> listWifi;
    private ArrayList<Weighted> lstWeight;
    private DBWifi dbWifi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_function);
        dbLocaltion = new DBLocaltion(getApplicationContext());
        dbWifi = new DBWifi(getApplicationContext());

        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        mWifiManager.startScan();
        Button btnAddBuilding = (Button) findViewById(R.id.btnAddBuilding);
        btnAddBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),MainActivity.class);
                startActivityForResult(intent,0);
            }
        });

        Button btnMyLocation = (Button) findViewById(R.id.btnMyLocation);
        btnMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWifiManager.startScan();
                lstPoint = getlistAcsendingDistance();
                lstWeight = new ArrayList<>();
                double w1 = 0.0;
                double w2 = 0.0;
                double w3 = 0.0;
                double w4 = 0.0;
                double w5 = 0.0;
                if(lstPoint.size() < 5){
                    Toast.makeText(MyLocationActivity.this,"Please!! Add more Building. ",Toast.LENGTH_SHORT).show();
                }else{
                    for(int i = 0;i< lstPoint.size(); i++){
                        if(i == 5){
                            Weighted weighted1 = new Weighted(lstPoint.get(0).getNameBuilding(),w1);
                            Weighted weighted2 = new Weighted(lstPoint.get(1).getNameBuilding(),w2);
                            Weighted weighted3 = new Weighted(lstPoint.get(2).getNameBuilding(),w3);
                            Weighted weighted4 = new Weighted(lstPoint.get(3).getNameBuilding(),w4);
                            Weighted weighted5 = new Weighted(lstPoint.get(4).getNameBuilding(),w5);
                            lstWeight.add(weighted1);
                            lstWeight.add(weighted2);
                            lstWeight.add(weighted3);
                            lstWeight.add(weighted4);
                            lstWeight.add(weighted5);
                            break;
                        }
                        for(int j = 0; j < lstPoint.size(); j++){
                            if(j == 5){

                                break;
                            }
                            if(i == 0){
                                if(lstPoint.get(i).getNameBuilding().equals(lstPoint.get(j).getNameBuilding())){

                                    w1 = w1 + calculateWeighted(lstPoint.get(j).getDistancePoint());

                                }
                            }
                            if(i == 1){
                                if(lstPoint.get(i).getNameBuilding().equals(lstPoint.get(j).getNameBuilding())){
                                    w2 = w2 + calculateWeighted(lstPoint.get(j).getDistancePoint());
                                }
                            }
                            if(i == 2){
                                if(lstPoint.get(i).getNameBuilding().equals(lstPoint.get(j).getNameBuilding())){

                                    w3 = w3 + calculateWeighted(lstPoint.get(j).getDistancePoint());                                }
                            }
                            if(i == 3){
                                if(lstPoint.get(i).getNameBuilding().equals(lstPoint.get(j).getNameBuilding())){
                                    w4 = w4 + calculateWeighted(lstPoint.get(j).getDistancePoint());                                }
                            }
                            if(i == 4){
                                if(lstPoint.get(i).getNameBuilding().equals(lstPoint.get(j).getNameBuilding())){
                                    w5 = w5 + calculateWeighted(lstPoint.get(j).getDistancePoint());                                }
                            }
                        }
                    }

                    double max = lstWeight.get(0).getWeight();;
                    Log.e(TAG,"Max: " + max);
                    for(int i = 0; i < lstWeight.size(); i++) {
                        if(max == 0.0){
                            max = lstWeight.get(i).getWeight();
                            break;
                        }
                        if(lstWeight.get(i).getWeight() > max){
                            max = lstWeight.get(i).getWeight();
                        }

                    }
                    for(int i = 0; i < lstWeight.size(); i++) {
                        Log.e("Du lieu: "," Ten: "+ lstWeight.get(i).getNameBuilding()+ " weight: "+ lstWeight.get(i).getWeight());
                        if(lstWeight.get(i).getWeight() == max){
                            String nameBuild = lstWeight.get(i).getNameBuilding();
                            Intent intent = new Intent(v.getContext(),ResultActivity.class);
                            intent.putExtra("nameBuild",nameBuild);
                            startActivityForResult(intent,0);
                            break;
                        }
                    }
                }


            }
        });
    }

    private double calculateWeighted(double disPoint) {
        return 1/disPoint;
    }


    private ArrayList<DistancePoint> getlistAcsendingDistance(){
        listWifi = new ArrayList<>();
        getWifiFromDB();
        lstBuild = dbLocaltion.getBuilding();
        Double d1 = 9999999.0;
        Double d2 = 9999999.0;
        Double d3 = 9999999.0;
        Double d4 = 9999999.0;
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


                    //Log.e(TAG,"wifi name: " + wifi.getName() + ", distance: " + wifi.getDistance());
                }
            }

        }

        lstPoint = new ArrayList<>();

        for (int i = 0 ; i < lstBuild.size() ; i++){
            double kq = calculateDistancePoint(d1,d2,d3,d4,lstBuild.get(i).getDistance1(),lstBuild.get(i).getDistance2(),lstBuild.get(i).getDistance3(),lstBuild.get(i).getDistance4());
            DistancePoint distancePoint = new DistancePoint(lstBuild.get(i).getNameBuilding(),kq,d1,d2,d3,d4,lstBuild.get(i).getDistance1(),lstBuild.get(i).getDistance2(),lstBuild.get(i).getDistance3(),lstBuild.get(i).getDistance4());
            lstPoint.add(distancePoint);
        }
        Collections.sort(lstPoint, new Comparator<DistancePoint>() {
            @Override
            public int compare(DistancePoint o1, DistancePoint o2) {
                return o1.getDistancePoint().compareTo(o2.getDistancePoint());
            }
        });

        return lstPoint;

    }


    private void getWifiFromDB(){
        String kq = dbWifi.getWifi();
        String[] listKQ = kq.split(";");
        listWifi.clear();
        for(int i = 0 ; i < listKQ.length ; i++){
            Wifi wifi = new Wifi(listKQ[i]);
            listWifi.add(wifi);
        }
    }
    public double calculateDistance(double signallevelIndb, double freqInMHz){
        double exp = (27.55 -(20*Math.log10(freqInMHz)) + Math.abs(signallevelIndb)) / 20.0;
        return Math.pow(10.0,exp);
    }

    private double calculateDistancePoint(double d1, double d2, double d3,double d4, double d5, double d6, double d7,double d8){
        double kq = Math.pow(d1 - d5,2) + Math.pow(d2 - d6,2) + Math.pow(d3 - d7,2) + Math.pow(d4 - d8,2);
        return Math.sqrt(kq);
    }


}
