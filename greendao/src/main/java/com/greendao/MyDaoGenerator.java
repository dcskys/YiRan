package com.greendao;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/*用来自动生成数据库*/
public class MyDaoGenerator {

    //辅助文件生成的相对路径
    public static final String DAO_PATH = "../Androidtool/app/src/main/java-gen";
    //辅助文件的包名  ，自己设置  会自动生成
    public static final String PACKAGE_NAME = "com.dc.greendao";
    //数据库的版本号
    public static final int DATA_VERSION_CODE = 1;



    public static void main(String[] args) throws Exception {

        // // 正如你所见的，你创建了一个用于添加实体（Entity）的模式（Schema）对象。
        // 两个参数分别代表：数据库版本号与自动生成代码的包路径。\

        Schema schema = new Schema(DATA_VERSION_CODE, PACKAGE_NAME);
        addCache(schema, "TopMovieCache");  //大写开头 ,top榜单的缓存
        addCache(schema, "NorthAmericaCache"); //北美票房的缓存

        //生成Dao文件路径  使用 DAOGenerator 类的 generateAll() 方法自动生成代码,指定了路径
        new DaoGenerator().generateAll(schema, DAO_PATH);

    }



    /**
     * 添加不同的缓存表
     * @param schema
     * @param tableName
     */
    private static void addCache(Schema schema, String tableName) {
        // 一个实体（类）就关联到数据库中的一张表，此处表名为「tableName」（既类名）
        Entity joke = schema.addEntity(tableName);
        //设置表的字段

        //主键id，接口请求数据result，页码page，添加时间time
        //主键id自增长
        joke.addIdProperty().primaryKey().autoincrement();
        //请求结果
        joke.addStringProperty("result");
        //页数
        joke.addIntProperty("page");
        //插入时间，暂未实现此功能
        joke.addLongProperty("time");

    }
}
