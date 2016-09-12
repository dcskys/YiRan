package com.dc.androidtool.DouBanMovie.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.dc.androidtool.MyApplication;
import com.dc.androidtool.R;
import com.dc.androidtool.DouBanMovie.entity.MovieDetailEntity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


/*
* 其实可以直接传实体解析  ，这里传id.主要对豆瓣api不熟悉  ，怕出错，json对不上，，多用了一次网络请求
*
* */
public class MovieDetailActivity extends AppCompatActivity {

    public static final String  URL = "https://api.douban.com/v2/movie/subject/";
    private String mobileUrl;
    private MovieDetailAdapter directorsAdapter;
    private castDetailAdapter castsAdapter;
    //init window
    private Window window;

    //init toolbar
    private Toolbar toolbar;

    //init view
    private ImageView movieImage;
    private TextView movieTitle;
    private TextView movieRealname;
    private TextView movieYear;
    private TextView movieCountry;
    private TextView movieSummary;
    private TextView moviePoints;

    private RelativeLayout movieImageBackground;
    private LinearLayout movieRealnameParent;


    private List<MovieDetailEntity.DirectorsBean> directorList;  //导演
    private List<MovieDetailEntity.CastsBean> castsList; //演员

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initView();
    }

    private void initView() {

        directorList =  new ArrayList<>();
        castsList =  new ArrayList<>();

        //set toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("电影详情");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //get movie id
        Intent intent = getIntent();
        String movieId = intent.getStringExtra("movieId");

        Log.e("电影详情",movieId);

        //init view
        movieImage = (ImageView) findViewById(R.id.movie_detail_image);//封面
        movieTitle = (TextView) findViewById(R.id.movie_detail_title); //电影名
        movieRealname = (TextView) findViewById(R.id.movie_detail_realname);//原名
        movieYear = (TextView) findViewById(R.id.movie_detail_year); //上映时间
        movieCountry = (TextView) findViewById(R.id.movie_detail_country);//国家
        movieSummary = (TextView) findViewById(R.id.movie_detail_summary);//摘要
        moviePoints = (TextView) findViewById(R.id.movie_detail_points);//评分
        //电影封面的背景
        movieImageBackground = (RelativeLayout) findViewById(R.id.movie_detail_image_background);
        movieRealnameParent = (LinearLayout) findViewById(R.id.movie_detail_realname_parent);

        //init window
        window = getWindow();

        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(getResources().getColor(R.color.movie_detail_default_background));
        }


        //导演列表
        RecyclerView directorsRecycler = (RecyclerView) findViewById(R.id.movie_detail_directors);
        directorsAdapter = new MovieDetailAdapter(this,directorList);
        LinearLayoutManager directorsManager = new LinearLayoutManager(this);
        directorsManager.setOrientation(LinearLayoutManager.HORIZONTAL);//设置横向的RecycleView
        directorsRecycler.setLayoutManager(directorsManager);
        directorsRecycler.setItemAnimator(new DefaultItemAnimator());//默认动画
        directorsRecycler.setAdapter(directorsAdapter);


        //演员列表
        RecyclerView castsRecycler = (RecyclerView) findViewById(R.id.movie_detail_casts);
        castsAdapter = new castDetailAdapter(castsList,this);
        LinearLayoutManager castsManager = new LinearLayoutManager(this);
        castsManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        castsRecycler.setLayoutManager(castsManager);
        castsRecycler.setItemAnimator(new DefaultItemAnimator());
        castsRecycler.setAdapter(castsAdapter);

        //get data
        getData(movieId);

    }

    private void getData(String movieId) {
        StringRequest stringRequest = new StringRequest
                (Request.Method.GET, URL+movieId, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //将解析的json 数据 s 添加到集合中去
                        addData(new Gson().fromJson(s, MovieDetailEntity.class));

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MovieDetailActivity.this, "加载出错", Toast.LENGTH_SHORT).show();
                    }
                });

        stringRequest.setTag("GET");

        MyApplication.getRequestQueue().add(stringRequest);

    }



    private void addData(MovieDetailEntity movieDetailEntity) {

        mobileUrl = movieDetailEntity.getMobile_url(); //电影原文url
        movieTitle.setText(movieDetailEntity.getTitle());
        movieYear.setText(movieDetailEntity.getYear());
        movieSummary.setText(movieDetailEntity.getSummary());
        moviePoints.setText(movieDetailEntity.getRating().getAverage()+""); //评分

        //原名的处理
        if (movieDetailEntity.getOriginal_title().equals("")) {
            movieRealnameParent.setVisibility(View.GONE);
        } else {
            movieRealname.setText(movieDetailEntity.getOriginal_title());
        }

        //加载电影封面
        addMovieView(movieDetailEntity.getImages().getLarge());

        //国家的处理
        StringBuilder movieCountries = new StringBuilder();
        for (int i = 0; i <  movieDetailEntity.getCountries().size(); i++) {
            if (i < movieDetailEntity.getCountries().size() - 1) {
                movieCountries.append(movieDetailEntity.getCountries().get(i));
                movieCountries.append("、");
            } else {
                movieCountries.append(movieDetailEntity.getCountries().get(i));
            }
        }
        movieCountry.setText(movieCountries.toString());

        directorList.clear();
        //取出导演的数组
        for (MovieDetailEntity.DirectorsBean directorsBean : movieDetailEntity.getDirectors()) {
            if (!directorList.contains(directorsBean)) {
                directorList.add(directorsBean);
            }
        }
        directorsAdapter.notifyDataSetChanged();

        castsList.clear();
        //演员数组
        for (MovieDetailEntity.CastsBean castsBean : movieDetailEntity.getCasts()) {
            if (!castsList.contains(castsBean)) {
                castsList.add(castsBean);
            }
        }
        directorsAdapter.notifyDataSetChanged();



    }

    //加载图片封面
    private void addMovieView(String url) {
        //set image
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Bitmap response) {
                if (Build.VERSION.SDK_INT >= 21) {   //5.0版本
                    movieImage.setImageBitmap(response);
                    //Palatte 需要手动导包了，它就是用来从Bitmap中提取颜色的，然后把颜色设置给title啊content
                    try {
                        Palette palette = Palette.from(response).generate(); //用来从Bitmap中提取颜色的
                        movieImageBackground.setBackgroundColor(palette.getDarkVibrantSwatch().getRgb());//整个布局的背景色
                        window.setStatusBarColor(palette.getDarkVibrantSwatch().getRgb());
                        toolbar.setBackgroundColor(palette.getDarkVibrantSwatch().getRgb());//toolbar和窗体颜色
                    } catch (Exception e) { //出错加载默认颜色
                        movieImageBackground.setBackgroundColor(getResources().getColor(R.color.movie_detail_default_background));
                        window.setStatusBarColor(getResources().getColor(R.color.movie_detail_default_background));
                        toolbar.setBackgroundColor(getResources().getColor(R.color.movie_detail_default_background));
                    }
                } else { //5.0版本以下
                    movieImage.setImageBitmap(response);

                    try {         //少了窗体颜色
                        Palette palette = Palette.from(response).generate();
                        movieImageBackground.setBackgroundColor(palette.getDarkVibrantSwatch().getRgb());
                        toolbar.setBackgroundColor(palette.getDarkVibrantSwatch().getRgb());
                    } catch (Exception e) {
                        movieImageBackground.setBackgroundColor(getResources().getColor(R.color.movie_detail_default_background));
                        toolbar.setBackgroundColor(getResources().getColor(R.color.movie_detail_default_background));
                    }
                }
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(">>> 图片加载错误 <<<");
            }
        });

        imageRequest.setTag("GET");
        MyApplication.getRequestQueue().add(imageRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.movie_open_in_browser) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(mobileUrl));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        MyApplication.getRequestQueue().cancelAll("GET"); //取消请求
    }
}
