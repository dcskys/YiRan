package com.dc.androidtool.Alltools.ui_fullSize;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.dc.androidtool.BaseActivity;
import com.dc.androidtool.R;


/*适配平板的 布局*/

public class Ui_fullSizeActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ui_full_size); // 加载2个不同的布局

   }
}
