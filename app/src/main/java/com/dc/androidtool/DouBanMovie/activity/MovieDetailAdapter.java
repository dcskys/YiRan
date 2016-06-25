package com.dc.androidtool.DouBanMovie.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.dc.androidtool.MyApplication;
import com.dc.androidtool.R;
import com.dc.androidtool.DouBanMovie.entity.MovieDetailEntity;

import java.util.List;

/**
 * 横向导演列表呈现
 */
public class MovieDetailAdapter extends RecyclerView.Adapter<MovieDetailViewHolder> {

    private List<MovieDetailEntity.DirectorsBean> list;  //数据源是json 中的导演列表
    private Context context;


    public MovieDetailAdapter(Context context,List<MovieDetailEntity.DirectorsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MovieDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MovieDetailViewHolder holder = new MovieDetailViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.activity_movie_detail_item, parent,
                false));

        return holder;
    }

    @Override
    public void onBindViewHolder(MovieDetailViewHolder holder, int position) {

        final  MovieDetailEntity.DirectorsBean directorsBean = list.get(position); //导演列表中某一个导演的具体信息
        final  MovieDetailEntity.DirectorsBean.AvatarsBean avatarsBean = directorsBean.getAvatars();//导演图片类
        holder.name.setText(directorsBean.getName());  //导演名
        //加载导演图片
        ImageLoader imageLoader = new ImageLoader(MyApplication.getRequestQueue(), new MovieImageCache());
        holder.image.setDefaultImageResId(R.drawable.recycler_image_background);
        holder.image.setErrorImageResId(R.mipmap.ic_launcher);
        holder.image.setImageUrl(avatarsBean.getMedium(), imageLoader); //中等图片

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

     //图片缓存
    class MovieImageCache implements ImageLoader.ImageCache {

        private LruCache<String, Bitmap> cache;

        public MovieImageCache() {
            int maxSize = 2 * 1024 * 1024;
            cache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return cache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            cache.put(url, bitmap);
        }
    }
}

class MovieDetailViewHolder extends RecyclerView.ViewHolder {

    public NetworkImageView image;
    public TextView name;

    public MovieDetailViewHolder(View itemView) {
        super(itemView);
        image = (NetworkImageView) itemView.findViewById(R.id.activity_movie_detail_item_image);
        name = (TextView) itemView.findViewById(R.id.activity_movie_detail_item_name);
    }
}
