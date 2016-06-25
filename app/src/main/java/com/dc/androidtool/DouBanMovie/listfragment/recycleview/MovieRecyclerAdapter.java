package com.dc.androidtool.DouBanMovie.listfragment.recycleview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.toolbox.NetworkImageView;

import com.dc.androidtool.R;
import com.dc.androidtool.DouBanMovie.activity.MovieDetailActivity;
import com.dc.androidtool.DouBanMovie.entity.MovieItemData;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 *top250的适配器 ,搜索电影界面 公用
 */
public class MovieRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private List<MovieItemData.SubjectsBean> list;   //列表是整个json中 SubjectsBean数组
    private Context context;

    private ImageLoader imageLoader;



    private static final int TYPE_ITEM = 0;        //用来实现上拉加载的功能
    private static final int TYPE_FOOTER = 1;

    //构造函数
    public MovieRecyclerAdapter(Context context,List<MovieItemData.SubjectsBean> list) {
        this.context = context;
         this.list = list;
        imageLoader = ImageLoader.getInstance();

    }

     //绑定ViewHolder
    @Override
    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

      /*  if (viewType == TYPE_FOOTER) {   //列表item的不同布局 ，加载更多布局
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.list_item_text, null);
            return new ItemViewHolder(view);
        }else {*/

            //优化每一个item 布局
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.fragment_movie_item, parent,
                    false));

            return holder;
        //}

    }

    //数据呈现
    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {

        final MovieItemData.SubjectsBean subjectsBean = list.get(position);;  //movieItemData类的某一项
        final MovieItemData.SubjectsBean.ImagesBean  imagesBean =subjectsBean.getImages();
        final MovieItemData.SubjectsBean.RatingBean ratingBean =subjectsBean.getRating();
        final List<MovieItemData.SubjectsBean.DirectorsBean> directorsBean = subjectsBean.getDirectors(); //导演列表
        final List<MovieItemData.SubjectsBean.CastsBean> castsBeans = subjectsBean.getCasts(); //卡司列表

        imageLoader.displayImage(imagesBean.getMedium(), holder.fragmentMovieImage);  //加载中等图片，另一种加载方式

      /* /*//** //使用volley加载缓存图片 (图片会出现闪烁)
        ImageLoader imageLoader = new ImageLoader(MyApplication.getRequestQueue(), new MovieImageCache());
        holder.fragmentMovieImage.setDefaultImageResId(R.drawable.recycler_image_background);
        holder.fragmentMovieImage.setErrorImageResId(R.mipmap.ic_launcher);
        holder.fragmentMovieImage.setImageUrl(imagesBean.getMedium(), imageLoader);  //呈现中等图片*/

        //init others
        holder.fragmentMovieTitle.setText(subjectsBean.getTitle());
        holder.fragmentMoviePoints.setText(ratingBean.getAverage()+""); //评分


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

        holder.fragmentMovieYear.setText(subjectsBean.getYear());  //上映时间

        holder.fragmentMovieCollections.setText(subjectsBean.getCollect_count()+""); //内容

        //整个布局的点击事件
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra("movieId", subjectsBean.getId()); //电影的id
               // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });



    }

    // RecyclerView的count设置为数据总条数+ 1（footerView）
    @Override
    public int getItemCount() {
        return list.size();
    }


  /*//为列表设置不同布局的方法
    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;    //呈现加载布局
        } else {
            return TYPE_ITEM;
        }
    }*/

   /* //图片缓存
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
       }*/
}


//viewHolder  优化  防止多次find
class MyViewHolder extends RecyclerView.ViewHolder {

    TextView fragmentMovieTitle;
    TextView fragmentMoviePoints;
    TextView fragmentMovieDirector;
    TextView fragmentMovieCast;
    TextView fragmentMovieYear;
    TextView fragmentMovieCollections;
    ImageView fragmentMovieImage;  //volley自带的图片控件
    CardView cardView;  //谷歌的v7包圆角卡片

    public MyViewHolder(View itemView) {
        super(itemView);
        fragmentMovieImage = (ImageView) itemView.findViewById(R.id.fragment_movie_item_image);
        fragmentMovieTitle = (TextView) itemView.findViewById(R.id.fragment_movie_item_title);
        fragmentMoviePoints = (TextView) itemView.findViewById(R.id.fragment_movie_item_points);
        fragmentMovieDirector = (TextView) itemView.findViewById(R.id.fragment_movie_item_director);
        fragmentMovieCast = (TextView) itemView.findViewById(R.id.fragment_movie_item_cast);
        fragmentMovieYear = (TextView) itemView.findViewById(R.id.fragment_movie_item_year);
        fragmentMovieCollections = (TextView) itemView.findViewById(R.id.fragment_movie_item_collections);
        cardView = (CardView) itemView.findViewById(R.id.fragment_movie_item);
    }
}



