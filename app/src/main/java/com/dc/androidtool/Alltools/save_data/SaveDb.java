package com.dc.androidtool.Alltools.save_data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.dc.androidtool.utils.ToastUtils;

/**
 * 数据库储存
 * ，数据库文件会存放在/data/data/<package name>/databases/目录下。
 */
public class SaveDb extends SQLiteOpenHelper {

    /*
      * 这里我们希望创建一个名为BookStore.db 的数据库，然后在这个数据库中新建一张Book
      表，表中有id（主键）、作者、价格、页数和书名等列*/
    public static final String CREATE_BOOK = "create table book ("
            + "id integer primary key autoincrement, "
            + "author text, "
            + "price real, "
            + "pages integer, "
            + "name text, "
            + "category_id integer)";  //新增加的字段，为2张表建立连接


    /*升级新添加的表   Category 表中有id（主键）、分类名和分类代码这几个*/
    public static final String CREATE_CATEGORY = "create table Category ("
            + "id integer primary key autoincrement, "
            + "category_name text, "
            + "category_code integer)";

    private Context mContext;


    public SaveDb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_BOOK);

        db.execSQL(CREATE_CATEGORY);  //数据库升级新添加的表
        ToastUtils.showToast(mContext, "Create succeeded", Toast.LENGTH_SHORT);


    }


    /*升级数据库
    *
    * 项目中已经有一张Book 表用于存放书的各种详细数据，如果我们想
再添加一张Category 表用于记录书籍的分类

让onUpgrade()方法能够执行了，还记得SQLiteOpenHelper 的构
造方法里接收的第四个参数吗？它表示当前数据库的版本号，之前我们传入的是1，现在只
要传入一个比1 大的数，就可以让onUpgrade()方法得到执行了。*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

     /* 粗暴的升级方法，会导致本地数据丢失
       db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Category");
      //两条DROP 语句，如果发现数据库中已经存在Book 表或Category 表了，就将这两张表删除掉，
        onCreate(db);//然后再调用onCreate()方法去重新创建*/

        switch (oldVersion) { //当用户的数据库版本是1时，更新新建的表，，，新用户的话会直接创建2张表
//当用户直接安装第三版的程序时，这个新增的列就已经自动添加成功了。然而，如果用户之前已经安装了某一版本的程序，现在需要覆盖安装，就会进入到升级数据库的操作中
            case 1:
                db.execSQL(CREATE_CATEGORY);//只创建新增加的表
            case 2:
                db.execSQL("alter table Book add column category_id integer"); //新增一个列
            default:

        }

/*，switch 中每一个case 的最后都是没有使用break的
这是为了保证在跨版本升级的时候，每一次的数据库修改都能被全部执
行到比如从数据库1直接升级到3*/

    }






}
