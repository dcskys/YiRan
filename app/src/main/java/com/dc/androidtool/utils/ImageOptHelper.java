package com.dc.androidtool.utils;

import android.graphics.Bitmap;

import com.dc.androidtool.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

/**
 * 图片工具类
 */
public class ImageOptHelper {

    public static DisplayImageOptions getImgOptions() {
        DisplayImageOptions imgOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)  //缓存sd卡
                .cacheInMemory(true)
                .cacheInMemory(true)  //缓存内存
                .bitmapConfig(Bitmap.Config.RGB_565) //设置图片解码方式，颜色类型
                .resetViewBeforeLoading(true)//下载前图片进行重置
                .imageScaleType(ImageScaleType.EXACTLY)
                .considerExifParams(true)
                .displayer(new SimpleBitmapDisplayer())  //防止图片出现闪烁
                .showImageOnLoading(R.drawable.recycler_image_background) //加载中呈现的图片
                .showImageForEmptyUri(R.drawable.recycler_image_background)//空地址图片
                .showImageOnFail(R.drawable.recycler_image_background) //失败图片
                .build();
        return imgOptions;
    }


    //头像的设置Option  ,头像的缓存信息，圆角头像
    public static DisplayImageOptions getAvatarOptions() {
        DisplayImageOptions	avatarOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)  //缓存在sd卡中
                .cacheInMemory(true)   //下载的图片是否缓存在内存中
                .bitmapConfig(Bitmap.Config.RGB_565)  //解码方式
                .showImageOnLoading(R.drawable.recycler_image_background) //加载中呈现的图片
                .showImageForEmptyUri(R.drawable.recycler_image_background)
                .showImageOnFail(R.drawable.recycler_image_background)
                .displayer(new RoundedBitmapDisplayer(999)) //图片以圆角边的形式进行展现
                .build();
        return avatarOptions;
    }

}
