package com.dc.androidtool.DouBanMovie.listfragment.listview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.dc.androidtool.MyApplication;
import com.dc.androidtool.R;
import com.dc.androidtool.DouBanMovie.activity.MovieDetailActivity;
import com.dc.androidtool.DouBanMovie.entity.MovieListItemData;

import java.util.List;

/**
 * ListView的适配器
 */
public class MovieListViewAdapter extends BaseAdapter{

    private List<MovieListItemData.SubjectsBean> list;   //列表是整个json中 SubjectsBean数组
    private Context context;
    private com.nostra13.universalimageloader.core.ImageLoader imageLoader;


    public MovieListViewAdapter(List<MovieListItemData.SubjectsBean> list, Context context) {
        this.list = list;
        this.context = context;
        imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolders holder; //优化机制

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.fragment_movie_item, null);//绑定布局
            holder = new ViewHolders();
            holder.fragmentMovieImage = (ImageView) convertView.findViewById(R.id.fragment_movie_item_image);
            holder.  fragmentMovieTitle = (TextView) convertView.findViewById(R.id.fragment_movie_item_title);
            holder. fragmentMoviePoints = (TextView) convertView.findViewById(R.id.fragment_movie_item_points);
            holder. fragmentMovieDirector = (TextView) convertView.findViewById(R.id.fragment_movie_item_director);
            holder.fragmentMovieCast = (TextView) convertView.findViewById(R.id.fragment_movie_item_cast);
            holder. fragmentMovieYear = (TextView) convertView.findViewById(R.id.fragment_movie_item_year);
            holder. fragmentMovieCollections = (TextView) convertView.findViewById(R.id.fragment_movie_item_collections);
            holder.  cardView = (CardView) convertView.findViewById(R.id.fragment_movie_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolders) convertView.getTag();
        }

        final MovieListItemData.SubjectsBean subjectsBeans = list.get(position);;  //某一项
        final MovieListItemData.SubjectsBean.SubjectBean subjectBean = subjectsBeans.getSubject();//真正获取的类
        final MovieListItemData.SubjectsBean.SubjectBean.ImagesBean  imagesBean =subjectBean.getImages();
        final MovieListItemData.SubjectsBean.SubjectBean.RatingBean ratingBean =subjectBean.getRating();
        final List<MovieListItemData.SubjectsBean.SubjectBean.DirectorsBean> directorsBean = subjectBean.getDirectors(); //导演列表
        final List<MovieListItemData.SubjectsBean.SubjectBean.CastsBean> castsBeans = subjectBean.getCasts(); //卡司列表


        imageLoader.displayImage(imagesBean.getMedium(),  holder.fragmentMovieImage);  //加载中等图片，另一种加载方式
       /* //使用volley加载缓存图片
        ImageLoader imageLoader = new ImageLoader(MyApplication.getRequestQueue(), new MovieImageCache());
        holder.fragmentMovieImage.setDefaultImageResId(R.drawable.recycler_image_background);
        holder.fragmentMovieImage.setErrorImageResId(R.mipmap.ic_launcher);
        holder.fragmentMovieImage.setImageUrl(imagesBean.getMedium(), imageLoader);  //呈现中等图片*/


        //init others
        holder.fragmentMovieTitle.setText(subjectBean.getTitle());
        holder.fragmentMoviePoints.setText(ratingBean.getAverage() + ""); //评分

        StringBuilder directorsString = new StringBuilder();  //导演

        for (int i = 0; i < directorsBean.size(); i++) {
            if (i < directorsBean.size()-1){
                directorsString.append(directorsBean.get(i).getName());
                directorsString.append("、");
            } else {
                directorsString.append(directorsBean.get(i).getName());
            }
        }
        holder.fragmentMovieDirector.setText(directorsString.toString());  //导演


        StringBuilder MovieCastsString = new StringBuilder();  //卡司
        for (int i = 0; i < castsBeans.size(); i++) {
            if (i < castsBeans.size()-1){
                MovieCastsString.append(castsBeans.get(i).getName());
                MovieCastsString.append("、");
            } else {
                MovieCastsString.append(castsBeans.get(i).getName());
            }
        }
        holder.fragmentMovieCast.setText(MovieCastsString.toString());//卡司

        holder.fragmentMovieYear.setText(subjectBean.getYear());  //上映时间

        holder.fragmentMovieCollections.setText(subjectBean.getCollect_count()+""); //内容

        //整个布局的点击事件
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra("movieId", subjectBean.getId()); //电影的id
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Log.e("id",subjectBean.getId().toString());
                context.startActivity(intent);
            }
        });


        return convertView;
    }



    //图片缓存
    private class MovieImageCache implements ImageLoader.ImageCache {

        private LruCache<String, Bitmap> cache;

        public MovieImageCache() {
            int maxSize = 10 * 1024 * 1024;
            cache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String s) {
            return cache.get(s);
        }

        @Override
        public void putBitmap(String s, Bitmap bitmap) {
            cache.put(s, bitmap);
        }
    }



    public static class ViewHolders {

        TextView fragmentMovieTitle;
        TextView fragmentMoviePoints;
        TextView fragmentMovieDirector;
        TextView fragmentMovieCast;
        TextView fragmentMovieYear;
        TextView fragmentMovieCollections;
        ImageView fragmentMovieImage;  //volley自带的图片控件
        CardView cardView;  //谷歌的v7包圆角卡片

    }
}
