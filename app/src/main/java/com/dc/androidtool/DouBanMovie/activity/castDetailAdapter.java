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
 * 演员横向列表呈现
 */
public class castDetailAdapter extends RecyclerView.Adapter<CastDetailViewHolder> {

    private List<MovieDetailEntity.CastsBean> list;  //数据源是json 中的演员列表
    private Context context;

    public castDetailAdapter(List<MovieDetailEntity.CastsBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public CastDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CastDetailViewHolder holder = new CastDetailViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.activity_movie_detail_item, parent,
                false));
        return holder;
    }


    @Override
    public void onBindViewHolder(CastDetailViewHolder holder, int position) {

        final  MovieDetailEntity.CastsBean castsBean = list.get(position); //演员列表中某一个导演的具体信息
        final  MovieDetailEntity.CastsBean.AvatarsBean avatarsBean = castsBean.getAvatars();//演员图片类
        holder.name.setText(castsBean.getName());  //演员名

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

class CastDetailViewHolder extends RecyclerView.ViewHolder {

    NetworkImageView image;
    TextView name;

    public CastDetailViewHolder(View itemView) {
        super(itemView);
        image = (NetworkImageView) itemView.findViewById(R.id.activity_movie_detail_item_image);
        name = (TextView) itemView.findViewById(R.id.activity_movie_detail_item_name);
    }
}
