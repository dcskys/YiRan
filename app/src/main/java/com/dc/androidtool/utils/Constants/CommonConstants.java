package com.dc.androidtool.utils.Constants;

/**
 * 通用常量   ，可以用接口形式实现
 */
public interface CommonConstants {

    public static final String SP_NAME = "sp_config";
    boolean isShowLog = true;
    int ELAPSED_TIME =10* 1000;
    int BROADCAST_ELAPSED_TIME_DELAY = 2*60*1000;
    int RETRIVE_SERVICE_COUNT = 50;

    String POI_SERVICE = "com.dc.androidtool.Alltools.learn_srevice.LongRunningService";
    String POI_SERVICE_ACTION = "com.dc.androidtool.Alltools.learn_srevice.LongRunningService.action";

}
