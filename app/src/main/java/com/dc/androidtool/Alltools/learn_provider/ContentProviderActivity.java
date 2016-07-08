package com.dc.androidtool.Alltools.learn_provider;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dc.androidtool.BaseActivity;
import com.dc.androidtool.R;

import java.util.ArrayList;
import java.util.List;

public class ContentProviderActivity extends BaseActivity {

    private ListView contactsView;
    private ArrayAdapter<String> adapter;
    private List<String> contactsList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        inintView();
    }

    private void inintView() {

        contactsView = (ListView) findViewById(R.id.contacts_view);
        adapter = new ArrayAdapter<String>(this, android.R.layout.
                simple_list_item_1, contactsList);
        contactsView.setAdapter(adapter);
        readContacts();
    }


    /*有调用Uri.parse() 方法去解析一个内容URI 字符串呢？ 这是因为
ContactsContract.CommonDataKinds.Phone类已经帮我们做好了封装，提供了一个CONTENT_URI
常量，而这个常量就是使用Uri.parse()方法解析出来的结果*/
    private void readContacts() {
        Cursor cursor = null;

        try {
// 查询联系人数据
            cursor = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null);
            while (cursor.moveToNext()) {
// 获取联系人姓名
                String displayName = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
// 获取联系人手机号
                String number = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactsList.add(displayName + "\n" + number);//添加到列表中呈现
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }
}
