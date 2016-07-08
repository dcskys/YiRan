package com.dc.androidtool.Alltools.learn_location;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.dc.androidtool.BaseActivity;
import com.dc.androidtool.R;

import java.util.List;

public class learn_locationActivity extends BaseActivity {


    private TextView positionTextView;
    private LocationManager locationManager;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_location);

        initView();

    }

    private void initView() {
        positionTextView = (TextView) findViewById(R.id.position_text_view);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //表明gps 可用，已打开
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            // 获取所有可用的位置提供器
            List<String> providerList = locationManager.getProviders(true);

            if (providerList.contains(LocationManager.GPS_PROVIDER)) { //gps定位
                provider = LocationManager.GPS_PROVIDER;
            } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {//网络定位
                provider = LocationManager.NETWORK_PROVIDER;
            } else {
                // 当没有可用的位置提供器时，弹出Toast提示用户
                showToast("没有开启定位功能，请开启");
                System.out.println("没有可用的位置提供器");
                return;
            }

            //api 23 的新要求  需要加上权限许可
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
// 显示当前设备的位置信息
                showLocation(location);
            }
            //当位置发生变化时随时进行更新      时间 毫秒  距离1米
            locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);
        } else {
            //无法定位：1、提示用户打开定位服务；2、跳转到设置界面
            Toast.makeText(this, "无法定位，请打开定位服务", Toast.LENGTH_SHORT).show();
            Intent i = new Intent();
            i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(i);

        }

    }


    LocationListener locationListener = new LocationListener() {

        //调用这个函数
        @Override
        public void onLocationChanged(Location location) {

            // 更新当前设备的位置信息
            showLocation(location);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void showLocation(Location location) {
        String currentPosition = "latitude is " + location.getLatitude() + "\n"
                + "longitude is " + location.getLongitude();
        positionTextView.setText(currentPosition);
    }



    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
         // 关闭程序时将监听器移除
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.removeUpdates(locationListener);
        }
    }
}
