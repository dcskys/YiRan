package com.dc.androidtool.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dc.androidtool.R;
import com.dc.androidtool.utils.FileUtil;
import com.dc.androidtool.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;

import java.io.File;
import java.text.DecimalFormat;

/**
 * 个人设置界面
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {

       private  View view;
       private LinearLayout base_setting;
       private TextView  clear_cache;

    public static final String  ACTION = "com.dc.androidtool.settings.BaseSettingActivity";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

      view = inflater.inflate(R.layout.fragment_setingsfragment, container, false);

        initView(view);
        initData();

        return view;
    }


    private void initView(View view) {

        base_setting = (LinearLayout) view.findViewById(R.id.base_setting);
        base_setting.setOnClickListener(this);

        clear_cache = (TextView) view.findViewById(R.id.clear_cache);
        clear_cache.setOnClickListener(this);

    }


    private void initData() {

        //缓存的文件夹
        File cacheFile = ImageLoader.getInstance().getDiskCache().getDirectory();
        //，用于格式化十进制数字
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        //计算缓存文件的大小
        clear_cache.setText("缓存大小：" + decimalFormat.format(FileUtil.getDirSize(cacheFile)) + "M");

        //根据 url 查找 缓存文件 的位置
        // File imgCacheFile = DiskCacheUtils.findInCache(url, ImageLoader.getInstance().getDiskCache());


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.base_setting:

                 //启动一个带有附加信息的隐式Intent
                Intent i = new Intent(ACTION);
                i.addCategory("com.dc.androidtool.MY_CATE");
                startActivity(i);
                break;


            case R.id.clear_cache:  //清空缓存

                ImageLoader.getInstance().clearDiskCache();
                ToastUtils.Short("清除缓存成功");
                clear_cache.setText("缓存大小：0.00M");

                break;





        }

    }
}
