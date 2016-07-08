package com.dc.androidtool.utils.cache;

import android.content.Context;
import android.util.Log;

import com.dc.androidtool.DouBanMovie.entity.MovieItemData;
import com.dc.androidtool.MyApplication;
import com.dc.greendao.TopMovieCacheDao;
import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * top250 表  数据缓存的操作类
 */
public class TopMovieCache extends BaseCache{

    private static TopMovieCache instance;
    private static TopMovieCacheDao mTopMovieCacheDao;

    // 私有化的构造方法
    private TopMovieCache(){

    }

     //单例
    public static TopMovieCache getInstance(Context context) {

        if (instance == null) {

            synchronized (TopMovieCache.class) {
                if (instance == null) {
                    instance = new TopMovieCache();//单例模式
                }
            }
            //获取DaoSession
            mDaoSession = MyApplication.getDaoSession(context);

            mTopMovieCacheDao = mDaoSession.getTopMovieCacheDao();
        }
        return instance;
    }


    /**
     * 清除全部缓存
     */
    @Override
    public void clearAllCache() {
        mTopMovieCacheDao.deleteAll();
    }


    /*
    * 注意返回的是整个实体类 中列表数据的集合*/
    @Override
    public ArrayList<MovieItemData.SubjectsBean> getCacheByPage(int page) {

        ArrayList<MovieItemData.SubjectsBean> list =  new ArrayList<>();
        QueryBuilder<com.dc.greendao.TopMovieCache> query = mTopMovieCacheDao.queryBuilder().where(TopMovieCacheDao
                .Properties.Page.eq("" + page));

        if (query.list().size() > 0) { //查找到
            Log.e("tag","查找到"+page);
            //json 转化成实体类
            MovieItemData movieItemData =  new Gson().fromJson(query.list().get(0).getResult(), MovieItemData.class);
            for (MovieItemData.SubjectsBean subjectsBean : movieItemData.getSubjects()) {
                if (!list.contains(subjectsBean)) {
                    list.add(subjectsBean);
                }
            }
            return list;
        } else {

            Log.e("tag","缓存数据");
            return new ArrayList<>();
        }
    }



    /*json 字符串储存到数据库*/

    @Override
    public void addResultCache(String result, int page) {

        com.dc.greendao.TopMovieCache  topMovieCache = new  com.dc.greendao.TopMovieCache();

        topMovieCache.setResult(result);
        topMovieCache.setPage(page);
        topMovieCache.setTime(System.currentTimeMillis());
        mTopMovieCacheDao.insert(topMovieCache);

    }
}
