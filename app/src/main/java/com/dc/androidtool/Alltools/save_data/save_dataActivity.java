package com.dc.androidtool.Alltools.save_data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dc.androidtool.BaseActivity;
import com.dc.androidtool.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class save_dataActivity extends BaseActivity implements View.OnClickListener {

    private EditText edit;

    private Button saveData;
    private Button restoreData;

    private SaveDb dbHelper; //数据库对象
    private Button createDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_data);

        initView();


    }

    private void initView() {

        //文件读取
        edit = (EditText) findViewById(R.id.edit);
        String inputText = load();
        if (!TextUtils.isEmpty(inputText)) {//两次判断 当传入的字符串等于null 或者等于 空字符串的时候，这个方法都会返回true
            edit.setText(inputText);
            edit.setSelection(inputText.length()); //光标移动到末尾
            showToast("读取文件成功");
        }
        //SharedPreferences
        saveData = (Button) findViewById(R.id.save_data);
        restoreData = (Button) findViewById(R.id.restore_data);
        saveData.setOnClickListener(this);
        restoreData.setOnClickListener(this);

        //数据库 ,版本号大于上一个版本，数据库会进行升级
        dbHelper = new SaveDb(this, "BookStore.db", null, 2);
        createDatabase = (Button) findViewById(R.id.create_database);
        createDatabase.setOnClickListener(this);


        Button updateData = (Button) findViewById(R.id.update_data);
        updateData.setOnClickListener(this);

        Button deleteButton = (Button) findViewById(R.id.delete_data);
        deleteButton.setOnClickListener(this);

        Button queryButton = (Button) findViewById(R.id.query_data);
        queryButton.setOnClickListener(this);

        Button replaceData = (Button) findViewById(R.id.replace_data);
        replaceData.setOnClickListener(this);
    }


    /* //保存数据
    * 例：然后进入到/data/data/com.example.sharedpreferencestest/shared_prefs /目录
    * 用XML 格式来对数据进行管理的。
    * */
    private void SaveSharedPreferences() {

        SharedPreferences.Editor editor = getSharedPreferences("data",
                MODE_PRIVATE).edit();
        editor.putString("name", "Tom");
        editor.putInt("age", 28);
        editor.putBoolean("married", false);
        editor.commit();

    }

    /*应用程序中的偏好设置功能等都可以使用*/
    private void restoreSharedPreferences() {

        SharedPreferences pref = getSharedPreferences("data",
                MODE_PRIVATE);
        String name = pref.getString("name", "");
        int age = pref.getInt("age", 0);
        boolean married = pref.getBoolean("married", false);
        showLog("name is " + name);
        showLog("age is " + age);
        showLog("married is " + married);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText = edit.getText().toString();
        save(inputText);
    }


    /*openFileOutput 内部储存文件

    例：文件位置在这里进入到/data/data/com.example.filepersistencetest/files/目录
    * */
    public void save(String data) {

        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("data", Context.MODE_PRIVATE); //中MODE_PRIVATE 是默认的操作模式，表示当指定同样文件名的时候，所写入的内容将会覆盖原文件中的内容
            writer = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {

                    writer.close();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /*读取文件*/
    public String load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput("data"); //文件名
            reader = new BufferedReader(new InputStreamReader(in));
            String line ="";

            while ((line = reader.readLine()) != null) {
                content.append(line); //一行一行呈现
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_data:
                SaveSharedPreferences(); //保存
                break;
            case R.id.restore_data:
                restoreSharedPreferences(); //读取
                break;

            case R.id.create_database:
                SQLiteDatabase db = dbHelper.getWritableDatabase(); //创建（不存在进行创建）和打开一个数据库

                ContentValues values = new ContentValues();
                // 开始组装第一条数据
                values.put("name", "The Da Vinci Code");
                values.put("author", "Dan Brown");
                values.put("pages", 454);
                values.put("price", 16.96);
                db.insert("Book", null, values); // 插入第一条数据

                values.clear();
                // 开始组装第二条数据
                values.put("name", "The Lost Symbol");
                values.put("author", "Dan Brown");
                values.put("pages", 510);
                values.put("price", 19.95);
                db.insert("Book", null, values); // 插入第二条数据


                break;

            case R.id.update_data: //更新数据

                SQLiteDatabase dbs = dbHelper.getWritableDatabase();

                ContentValues valuess = new ContentValues();
                valuess.put("price", 10.99);
                dbs.update("Book", valuess, "name = ?", new String[] { "The Da Vinci Code" });

                break;

            case R.id.delete_data: //删除数据

                SQLiteDatabase db1 = dbHelper.getWritableDatabase();
                db1.delete("Book", "pages > ?", new String[] { "500" }); //删除页数大于500的书籍

                break;

            case R.id.query_data: //查询数据

                SQLiteDatabase db2 = dbHelper.getWritableDatabase();
                // 查询Book表中所有的数据
                Cursor cursor = db2.query("Book", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {  //数据指针移动到第一行

                    do {
                      // 遍历Cursor对象，取出数据并打印
                        String name = cursor.getString(cursor.
                                getColumnIndex("name"));
                        String author = cursor.getString(cursor.
                                getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex
                                ("pages"));
                        double price = cursor.getDouble(cursor.
                                getColumnIndex("price"));
                        Log.d("MainActivity", "book name is " + name);
                        Log.d("MainActivity", "book author is " + author);
                        Log.d("MainActivity", "book pages is " + pages);
                        Log.d("MainActivity", "book price is " + price);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                break;

            /*  比如Book 表中的数据都已经很老了，现在准备全部废弃掉替换成新数据，可以
            先使用delete()方法将Book表中的数据删除，然后再使用insert()方法将新的数据添加到表中。
            我们要保证的是，删除旧数据和添加新数据的操作必须一起完成，否则就还要继续保留原来
            的旧数据。*/
            case R.id.replace_data: //使用事务替换数据

                SQLiteDatabase db3 = dbHelper.getWritableDatabase();
                db3.beginTransaction(); // 开启事务

                try {
                    db3.delete("Book", null, null);
                  /*  if (true) {
                  // 在这里手动抛出一个异常，让事务失败
                        throw new NullPointerException();
                    }*/
                    ContentValues values1 = new ContentValues();
                    values1.put("name", "Game of Thrones");
                    values1.put("author", "George Martin");
                    values1.put("pages", 720);
                    values1.put("price", 20.85);
                    db3.insert("Book", null, values1);
                    db3.setTransactionSuccessful(); // 事务已经执行成功
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    db3.endTransaction(); // 结束事务
                }



                break;



        }
    }


}
