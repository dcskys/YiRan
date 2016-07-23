package com.dc.androidtool.Alltools.learn_location;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dc.androidtool.BaseActivity;
import com.dc.androidtool.R;

import java.util.List;

public class learn_locationActivity extends BaseActivity {


    private TextView positionTextView;
    private LocationManager locationManager;
    private String provider;
    private Button  btn_Permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_location);

        initView();

    }

    private void initView() {
        positionTextView = (TextView) findViewById(R.id.position_text_view);

        //api 23 的新要求  需要加上权限许可
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //测试小米5无法打开对话框
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        } else{
            gpsLocation();
        }


        btn_Permission = (Button) findViewById(R.id.btn_Permission);
        btn_Permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //判断是否有权限
                if (ContextCompat.checkSelfPermission(learn_locationActivity.this,
                        Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
                    //当用户拒绝掉权限时.
                    if (ActivityCompat.shouldShowRequestPermissionRationale(learn_locationActivity.this,
                            Manifest.permission.READ_CONTACTS)) {

                        Toast.makeText(learn_locationActivity.this, "true", Toast.LENGTH_SHORT).show();
                        AlertDialog dialog = new AlertDialog.Builder(learn_locationActivity.this).setTitle("该权限保证手机不会爆炸^ ^").setPositiveButton("我需要此权限!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(learn_locationActivity.this,
                                        new String[]{Manifest.permission.READ_CONTACTS},
                                        124);
                            }
                        }).setNegativeButton("炸吧炸吧~", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(learn_locationActivity.this, "准备爆炸了", Toast.LENGTH_SHORT).show();
                            }
                        }).show();

                    } else {

                        Toast.makeText(learn_locationActivity.this, "false", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(learn_locationActivity.this,
                                new String[]{Manifest.permission.READ_CONTACTS},
                                124);
                    }
                }else {  //表示已经拥有权限了

                    showLog("没有反应");
                }
                showLog("结束了");

            }
        });
}


     //定位操作
    private void gpsLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //表明gps 可用，已打开（手机没有关闭位置服务）
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            // 获取所有可用的位置提供器
            List<String> providerList = locationManager.getProviders(true);

            if (providerList.contains(LocationManager.GPS_PROVIDER)) { //gps定位
                provider = LocationManager.GPS_PROVIDER;
                System.out.println("gps定位");
            } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {//网络定位
                provider = LocationManager.NETWORK_PROVIDER;
                System.out.println("网络定位");
            } else {
                // 当没有可用的位置提供器时，弹出Toast提示用户
                showToast("没有开启定位功能，请开启");
                System.out.println("没有可用的位置提供器");
                return;
            }
            showCheckLocation();
        } else {
            //无法定位：1、提示用户打开定位服务；2、跳转到设置界面
            Toast.makeText(this, "无法定位，请打开定位服务", Toast.LENGTH_SHORT).show();
            Intent i = new Intent();
            i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(i);

        }


    }

    private void showCheckLocation() {

        //api 23 的新要求  需要加上权限许可
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
        }else{

            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {  //为null 可能是gps被禁用，网络又不好
                // 显示当前设备的位置信息
                showLocation(location);
                showLog("位置已显示");
            }else {
                showToast("没有获取到定位信息");
            }
            //当位置发生变化时随时进行更新      时间 毫秒  距离1米
            locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);

        }


    }

    //关于请求 权限的回调函数
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case 123:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gpsLocation();
                    showLog("权限已授权");
                } else {
                    // Permission Denied
                    showToast("没有权限定位失败");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
