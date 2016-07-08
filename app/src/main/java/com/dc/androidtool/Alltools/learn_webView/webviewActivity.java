package com.dc.androidtool.Alltools.learn_webView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dc.androidtool.BaseActivity;
import com.dc.androidtool.R;

public class webviewActivity extends BaseActivity {


    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        initView();


    }

    private void initView() {
        webView = (WebView) findViewById(R.id.web_view);
        //设置支持浏览器的属性
        webView.getSettings().setJavaScriptEnabled(true); //WebView 支持JavaScript 脚本。


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String
                    url) {
                view.loadUrl(url); // 根据传入的参数再去加载新的网页
                return true; // 表示当前WebView可以处理打开新网页的请求，不用借助系统浏览器

                /*表明当需要从一个网页跳转到另一个网页时，我们希望目标网页仍然在当前WebView 中显
示，而不是打开系统浏览器。*/
            }
        });
        webView.loadUrl("http://www.baidu.com");
    }


}
