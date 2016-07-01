package com.dc.androidtool.Alltools.save_data;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class save_dataActivity extends BaseActivity {

    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_data);

        initView();


    }

    private void initView() {
        edit = (EditText) findViewById(R.id.edit);
        String inputText = load();

        if (!TextUtils.isEmpty(inputText)) {//两次判断 当传入的字符串等于null 或者等于 空字符串的时候，这个方法都会返回true
            edit.setText(inputText);
            edit.setSelection(inputText.length()); //光标移动到末尾
            showToast("读取文件成功");
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText = edit.getText().toString();
        save(inputText);
    }


    /*openFileOutput 内部储存文件
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


}
