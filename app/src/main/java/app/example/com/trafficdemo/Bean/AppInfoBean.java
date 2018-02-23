package app.example.com.trafficdemo.Bean;

import android.support.annotation.NonNull;

/**
 * Created by mnkj on 2018/2/23.
 */

public class AppInfoBean implements Comparable<AppInfoBean>{
    private String appName;//app 名称
    private int appUuid;//app uuid
    private String appPackageName;//app PackageName
    private String app_Tcp_Snd;//app上传数据大小
    private String app_Tcp_Rcv;//app获取数据大小
    private String app_Tcp_Tot_MB;//app使用数据总量
    private long app_Tcp_Tot;//app使用数据总量

    public long getApp_Tcp_Tot() {
        return app_Tcp_Tot;
    }

    public void setApp_Tcp_Tot(long app_Tcp_Tot) {
        this.app_Tcp_Tot = app_Tcp_Tot;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getAppUuid() {
        return appUuid;
    }

    public void setAppUuid(int appUuid) {
        this.appUuid = appUuid;
    }

    public String getAppPackageName() {
        return appPackageName;
    }

    public void setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
    }

    public String getApp_Tcp_Snd() {
        return app_Tcp_Snd;
    }

    public void setApp_Tcp_Snd(String app_Tcp_Snd) {
        this.app_Tcp_Snd = app_Tcp_Snd;
    }

    public String getApp_Tcp_Rcv() {
        return app_Tcp_Rcv;
    }

    public void setApp_Tcp_Rcv(String app_Tcp_Rcv) {
        this.app_Tcp_Rcv = app_Tcp_Rcv;
    }

    public String getApp_Tcp_Tot_MB() {
        return app_Tcp_Tot_MB;
    }

    public void setApp_Tcp_Tot_MB(String app_Tcp_Tot_MB) {
        this.app_Tcp_Tot_MB = app_Tcp_Tot_MB;//上传获取数据总和
    }

//    @Override
//    public int compare(AppInfoBean o1, AppInfoBean o2) {
//        if(o1.getApp_Tcp_Tot()>o2.getApp_Tcp_Tot()){
//            return -1;
//        }else if(o1.getApp_Tcp_Tot()<o2.getApp_Tcp_Tot()){
//            return 1;
//        }else{
//            return 0;
//        }
//    }

    @Override
    public int compareTo(@NonNull AppInfoBean o) {
        if (this.app_Tcp_Tot < o.getApp_Tcp_Tot())
            return -1;
        else if (this.app_Tcp_Tot > o.getApp_Tcp_Tot())
            return 1;
        else
            return 0;

    }
}
