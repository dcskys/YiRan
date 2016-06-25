package com.dc.androidtool.search;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
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
import com.dc.androidtool.search.entity.BookDetailEntity;
import com.google.gson.Gson;

import java.util.List;


/*书籍详情界面*/
public class BookDetailActivity extends AppCompatActivity implements View.OnClickListener {

    //url
  public static String url = "https://api.douban.com/v2/book/";

    //book id
    private String bookId;
    private String mobileUrl;

    //init view
    private RelativeLayout bookImageBackground;
    private ImageView bookImage;
    private TextView bookTitle;
    private TextView bookSubtitle;
    private TextView bookRealname;
    private TextView bookPublisher;
    private TextView bookTranslator;
    private TextView bookPubdate;
    private TextView bookPrice;
    private TextView bookAuthor;
    private TextView bookAuthorSummary;
    private TextView bookSummary;
    private CardView bookMine;
    private TextView bookMyStatus;
    private TextView bookMySummary;
    private ImageView bookMyStar1;
    private ImageView bookMyStar2;
    private ImageView bookMyStar3;
    private ImageView bookMyStar4;
    private ImageView bookMyStar5;
    private TextView bookCatalog;
    private LinearLayout bookTranLayout;
    private LinearLayout bookRealLayout;
    private TextView bookPoints;

    //init window
    private Window window;
    //init toolbar
    private Toolbar toolbar;

    private FloatingActionButton  floatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        initView();
    }

    private void initView() {
        //set toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("书籍详情");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //init view
        bookImageBackground = (RelativeLayout) findViewById(R.id.book_detail_image_background);
        bookImage = (ImageView) findViewById(R.id.book_detail_image);
        bookTitle = (TextView) findViewById(R.id.book_detail_title);
        bookSubtitle = (TextView) findViewById(R.id.book_detail_subtitle);
        bookRealname = (TextView) findViewById(R.id.book_detail_realname);
        bookPublisher = (TextView) findViewById(R.id.book_detail_publisher);
        bookTranslator = (TextView) findViewById(R.id.book_detail_translator);
        bookPubdate = (TextView) findViewById(R.id.book_detail_pubdate);
        bookPrice = (TextView) findViewById(R.id.book_detail_price);
        bookAuthor = (TextView) findViewById(R.id.book_detail_author);
        bookAuthorSummary = (TextView) findViewById(R.id.book_detail_author_summary);
        bookSummary = (TextView) findViewById(R.id.book_detail_summary);
        bookMine = (CardView) findViewById(R.id.book_detail_mine);
      /*  bookMyStatus = (TextView) findViewById(R.id.book_detail_status);
        bookMySummary = (TextView) findViewById(R.id.book_detail_mysummary);
        bookMyStar1 = (ImageView) findViewById(R.id.book_detail_star_1);
        bookMyStar2 = (ImageView) findViewById(R.id.book_detail_star_2);
        bookMyStar3 = (ImageView) findViewById(R.id.book_detail_star_3);
        bookMyStar4 = (ImageView) findViewById(R.id.book_detail_star_4);
        bookMyStar5 = (ImageView) findViewById(R.id.book_detail_star_5);*/
        bookCatalog = (TextView) findViewById(R.id.book_detail_catalog);
        bookTranLayout = (LinearLayout) findViewById(R.id.book_detail_tran_layout);
        bookRealLayout = (LinearLayout) findViewById(R.id.book_detail_real_layout);
        bookPoints = (TextView) findViewById(R.id.book_detail_points);

         //悬浮按钮
        floatingButton = (FloatingActionButton) findViewById(R.id.floating_button);
        floatingButton.setOnClickListener(this);


        //init window
        window = getWindow();
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(getResources().getColor(R.color.movie_detail_default_background));
        }

       //get book id
        Intent intent = getIntent();
        bookId = intent.getStringExtra("bookId");

        Log.e("书籍id","书籍id"+bookId);

        //set data
        getData(bookId);


    }

    private void getData(String bookId) {

        StringRequest stringRequest = new StringRequest
                (Request.Method.GET, url + bookId, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //解析json 数据
                        addData(new Gson().fromJson(s, BookDetailEntity.class));

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(BookDetailActivity.this, "加载出错", Toast.LENGTH_SHORT).show();
                    }
                });

        stringRequest.setTag("GET");

        MyApplication.getRequestQueue().add(stringRequest);

    }

    //数据呈现
    private void addData(BookDetailEntity bookDetailEntity) {

        mobileUrl = bookDetailEntity.getAlt();//原文url

        bookPubdate.setText(bookDetailEntity.getPubdate());//出版日期
        bookSubtitle.setText(bookDetailEntity.getSubtitle());
        bookPublisher.setText(bookDetailEntity.getPublisher());
        bookPoints.setText(bookDetailEntity.getRating().getAverage());//评分

        bookTitle.setText(bookDetailEntity.getTitle());
        bookPrice.setText(bookDetailEntity.getPrice());
        bookAuthorSummary.setText(bookDetailEntity.getAuthor_intro()); //作者简介
        bookSummary.setText(bookDetailEntity.getSummary());
        bookCatalog.setText(bookDetailEntity.getCatalog());

        //原名的处理
        if (bookDetailEntity.getOrigin_title().equals("")) {
            bookRealname.setText(bookDetailEntity.getOrigin_title());
        } else {
            bookRealLayout.setVisibility(View.GONE);
        }

        //作者的处理
        List<String> author1 = bookDetailEntity.getAuthor();
        for (int i = 0; i < author1.size(); i++) {
            StringBuilder author = new StringBuilder();
            if (i != author1.size() - 1) {
                author.append(author1.get(i));
                author.append("、");
            } else {
                author.append(author1.get(i));
            }
            bookAuthor.setText(author.toString());
        }

        //翻译者处理
        List<String> translator1 = bookDetailEntity.getTranslator();
        if (translator1.size() != 0) {
            for (int i = 0; i < translator1.size(); i++) {
                StringBuilder translator = new StringBuilder();
                if (i != translator1.size() - 1) {
                    translator.append(translator1.get(i));
                    translator.append("、");
                } else {
                    translator.append(translator1.get(i));
                }
                bookTranslator.setText(translator.toString());
            }
        } else {
            bookTranLayout.setVisibility(View.GONE);
        }


        //加载书籍封面
        addBookView(bookDetailEntity.getImages().getLarge());



    }




    private void addBookView(String url) {
        //set image
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Bitmap response) {
                if (Build.VERSION.SDK_INT >= 21) {  //5.0版本
                    bookImage.setImageBitmap(response);
                    // //Palatte 需要手动导包了，它就是用来从Bitmap中提取颜色的，然后把颜色设置给title啊content
                    try {
                        Palette palette = Palette.from(response).generate();
                        bookImageBackground.setBackgroundColor(palette.getDarkVibrantSwatch().getRgb());
                        window.setStatusBarColor(palette.getDarkVibrantSwatch().getRgb());
                        toolbar.setBackgroundColor(palette.getDarkVibrantSwatch().getRgb());
                    } catch (Exception e) {//出错加载默认颜色
                        bookImageBackground.setBackgroundColor(getResources().getColor(R.color.movie_detail_default_background));
                        window.setStatusBarColor(getResources().getColor(R.color.movie_detail_default_background));
                        toolbar.setBackgroundColor(getResources().getColor(R.color.movie_detail_default_background));
                    }
                } else {
                    bookImage.setImageBitmap(response);
                    //set image layout background
                    try { //少了窗体颜色
                        Palette palette = Palette.from(response).generate();
                        bookImageBackground.setBackgroundColor(palette.getDarkVibrantSwatch().getRgb());
                        toolbar.setBackgroundColor(palette.getDarkVibrantSwatch().getRgb());
                    } catch (Exception e) {
                        bookImageBackground.setBackgroundColor(getResources().getColor(R.color.movie_detail_default_background));
                        toolbar.setBackgroundColor(getResources().getColor(R.color.movie_detail_default_background));
                    }
                }
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BookDetailActivity.this, "图片加载错误", Toast.LENGTH_SHORT).show();
            }
        });

        imageRequest.setTag("GET");
        MyApplication.getRequestQueue().add(imageRequest);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.book_open_in_browser) {
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


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.floating_button: //Snackbar使用了builder模式。有多种效果 .setAction 可以有取消效果
                Snackbar.make(v,"暂未实现这个功能哦",Snackbar.LENGTH_SHORT).show();

                break;
        }

    }
}



