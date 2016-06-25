package com.dc.androidtool.utils.control;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * 自定义的volley 请求 ，Gson直接解析
 *
 * 仿照StringRequest 写的
 */
public class GsonRequest<T> extends Request<T> {

    private final Listener<T> mListener;

    private Gson mGson;
    private Class<T> mClass;

    public GsonRequest(int method, String url, Class<T> clazz, Listener<T> listener, Response.ErrorListener errorListener) {

        super(method, url, errorListener);
          mGson = new Gson();
          mClass = clazz;
          mListener = listener;
    }

    public GsonRequest(String url, Class<T> clazz, Listener<T> listener,
                       Response.ErrorListener errorListener) {
        this(Method.GET, url, clazz, listener, errorListener);
    }



    /*其中数据是以字节的形式存放在Response的data变量中的，这里将数据取出然后组装成一个String，解析，并传入Response的success()方法中即可*/
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
             String jsonString = new String(response.data,
                     HttpHeaderParser.parseCharset(response.headers));
              return Response.success(mGson.fromJson(jsonString, mClass),
                                     HttpHeaderParser.parseCacheHeaders(response));
             } catch (UnsupportedEncodingException e) {
                     return Response.error(new ParseError(e));
                }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
