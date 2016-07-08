package com.dc.androidtool.utils.cache;

import android.content.Context;

import com.dc.androidtool.DouBanMovie.entity.MovieListItemData;
import com.dc.androidtool.MyApplication;
import com.dc.greendao.NorthAmericaCacheDao;
import com.google.gson.Gson;

import java.util.ArrayList;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * 北美票房榜的缓存
 */
public class NorthAmericaCache extends BaseCache{

    private static NorthAmericaCache instance;
    private static NorthAmericaCacheDao mNorthAmericaCacheDao;

    // 私有化的构造方法
    private NorthAmericaCache(){

    }

    //单例  ,这样的同步锁可用于多线程  ，双重锁定
    public static NorthAmericaCache getInstance(Context context) {

        if (instance == null) {

            synchronized (NorthAmericaCache.class) {
                if (instance == null) {
                    instance = new NorthAmericaCache();//单例模式
                }
            }
            //获取DaoSession
            mDaoSession = MyApplication.getDaoSession(context);

            mNorthAmericaCacheDao = mDaoSession.getNorthAmericaCacheDao();
        }
        return instance;
    }


    /**
     * 清除全部缓存
     */
    @Override
    public void clearAllCache() {
        mNorthAmericaCacheDao.deleteAll();
    }


    /*
    * 注意返回的是整个实体类 中列表数据的集合*/
    @Override
    public ArrayList<MovieListItemData.SubjectsBean> getCacheByPage(int page) {

        ArrayList<MovieListItemData.SubjectsBean> list =  new ArrayList<>();

        QueryBuilder<com.dc.greendao.NorthAmericaCache> query = mNorthAmericaCacheDao.queryBuilder().where(NorthAmericaCacheDao
                .Properties.Page.eq("" + page));

        if (query.list().size() > 0) { //查找到
            //json 转化成实体类
            MovieListItemData movieListItemData =  new Gson().fromJson(query.list().get(0).getResult(), MovieListItemData.class);
            for (MovieListItemData.SubjectsBean subjectsBean : movieListItemData.getSubjects()) {
                list.add(subjectsBean);
            }
            return list;
        } else {
            return new ArrayList<>();
        }
    }

    /*json 字符串储存到数据库*/

    @Override
    public void addResultCache(String result, int page) {

        com.dc.greendao.NorthAmericaCache  northAmericaCache = new  com.dc.greendao.NorthAmericaCache();

        northAmericaCache.setResult(result);
        northAmericaCache.setPage(page);
        northAmericaCache.setTime(System.currentTimeMillis());
        mNorthAmericaCacheDao.insert(northAmericaCache);
    }
}
