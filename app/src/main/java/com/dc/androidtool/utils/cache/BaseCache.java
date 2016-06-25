package com.dc.androidtool.utils.cache;

import com.dc.greendao.DaoSession;

import java.util.ArrayList;

/**
 * 缓存逻辑基本相同，所以抽取了一个基类
 */
public abstract class BaseCache<T> {

    //数据库名
    public static final String DB_NAME = "androidtools-db";

    protected static DaoSession mDaoSession;

    public abstract void clearAllCache(); //清除所有缓存

    public abstract ArrayList<T> getCacheByPage(int page); //添加

    public abstract void addResultCache(String result, int page);//获取

}