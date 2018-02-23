package app.example.com.trafficdemo.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import app.example.com.trafficdemo.Bean.AppInfoBean;

import static android.content.ContentValues.TAG;

/**
 * Created by mnkj on 2018/2/23.
 */

public class AppsName {
    private static PackageManager packageManager;

    public static List<AppInfoBean> getAllAppNames(Context context) {
//        PackageManager pm=context.getPackageManager();
        packageManager = context.getPackageManager();
        //PackageManager.GET_UNINSTALLED_PACKAGES==8192
        List<PackageInfo> list2 = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        //PackageManager.GET_SHARED_LIBRARY_FILES==1024
        //List<PackageInfo> list2=pm.getInstalledPackages(PackageManager.GET_SHARED_LIBRARY_FILES);
        //PackageManager.GET_META_DATA==128
        //List<PackageInfo> list2=pm.getInstalledPackages(PackageManager.GET_META_DATA);
        //List<PackageInfo> list2=pm.getInstalledPackages(0);
        //List<PackageInfo> list2=pm.getInstalledPackages(-10);
        //List<PackageInfo> list2=pm.getInstalledPackages(10000);
        List<AppInfoBean> appBeans = new ArrayList<>();
        int j = 0;
        for (PackageInfo packageInfo : list2) {
            AppInfoBean bean = new AppInfoBean();
            //得到手机上已经安装的应用的名字,即在AndriodMainfest.xml中的app_name。
            String appName = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
            //得到应用所在包的名字,即在AndriodMainfest.xml中的package的值。
            String packageName = packageInfo.packageName;
            //得到app uuid
            int uuid = getUid(packageInfo.packageName);
            //得到app snd 上传
            long snd = getTxTcpTraffic(uuid);
            //得到app rcv  获取
            long rcv = getRxTcpTraffic(uuid);

            bean.setAppName(appName);
            bean.setAppPackageName(packageName);
            bean.setAppUuid(uuid);
            bean.setApp_Tcp_Rcv(getMB(rcv));
            bean.setApp_Tcp_Snd(getMB(snd));
            bean.setApp_Tcp_Tot_MB(getMB(rcv + snd));
            bean.setApp_Tcp_Tot(rcv + snd);

            appBeans.add(bean);

            Log.i(TAG, "应用的名字:" + appName);
            Log.i(TAG, "应用的包名字:" + packageName);
            j++;
        }
        Log.i(TAG, "应用的总个数:" + j);
        Collections.sort(appBeans);
        return appBeans;
    }


    /**
     * @param uid 程序的uid
     * @return 上传的流量（tcp+udp）  返回-1 表示不支持得机型
     * @Description: 获取uid上传的流量(wifi+3g/4g)
     * @author fengao
     */
    public static long getTxTraffic(int uid) {
        return TrafficStats.getUidTxBytes(uid);
    }

    /**
     * @param uid 程序的uid
     * @return 上传的流量（tcp）  返回-1 表示出现异常
     * @Description: 获取uid上传的流量(wifi+3g/4g)  通过读取/proc/uid_stat/uid/tcp_snd文件获取
     * @author fengao
     */
    public static long getTxTcpTraffic(int uid) {
        RandomAccessFile rafSnd = null;
        String sndPath = "/proc/uid_stat/" + uid + "/tcp_snd";
        long sndTcpTraffic;
        try {
            rafSnd = new RandomAccessFile(sndPath, "r");
            sndTcpTraffic = Long.parseLong(rafSnd.readLine());
        } catch (FileNotFoundException e) {
            sndTcpTraffic = -1;
        } catch (IOException e) {
            e.printStackTrace();
            sndTcpTraffic = -1;
        } finally {
            try {
                if (rafSnd != null) {
                    rafSnd.close();
                }
            } catch (IOException e) {
                sndTcpTraffic = -1;
            }
        }
        return sndTcpTraffic;
    }

    /**
     * @param uid 程序的uid
     * @return 下載的流量(tcp+udp) 返回-1表示不支持的机型
     * @Description: 获取uid下載的流量(wifi+3g/4g)
     * @author fengao
     */
    public static long getRxTraffic(int uid) {
        return TrafficStats.getUidRxBytes(uid);
    }

    /**
     * @param uid 程序的uid
     * @return 下载的流量（tcp）  返回-1 表示出现异常
     * @Description: 获取uid上传的流量(wifi+3g/4g) 通过读取/proc/uid_stat/uid/tcp_rcv文件获取
     * @author fengao
     */
    public static long getRxTcpTraffic(int uid) {
        RandomAccessFile rafRcv = null; // 用于访问数据记录文件
        String rcvPath = "/proc/uid_stat/" + uid + "/tcp_rcv";
        long rcvTcpTraffic;
        try {
            rafRcv = new RandomAccessFile(rcvPath, "r");
            rcvTcpTraffic = Long.parseLong(rafRcv.readLine()); // 读取流量统计
        } catch (FileNotFoundException e) {
            rcvTcpTraffic = -1;
        } catch (IOException e) {
            rcvTcpTraffic = -1;
        } finally {
            try {
                if (rafRcv != null) {
                    rafRcv.close();
                }
            } catch (IOException e) {
                rcvTcpTraffic = -1;
            }
        }
        return rcvTcpTraffic;
    }

    /**
     * @param uid 程序的uid
     * @return uid的总流量   当设备不支持方法且没有权限访问/proc/uid_stat/uid时 返回-1
     * @Description: 得到uid的总流量（上传+下载）
     * @author fengao
     */
    public static long getTotalTraffic(int uid) {
        long txTraffic = (getTxTraffic(uid) == -1) ? getTxTcpTraffic(uid) : getTxTraffic(uid);
        if (txTraffic == -1) {
            return -1;
        }
        long rxTraffic = (getRxTraffic(uid) == -1) ? getRxTcpTraffic(uid) : getRxTraffic(uid);
        if (rxTraffic == -1) {
            return -1;
        }
        return txTraffic + rxTraffic;
    }

    /**
     * @return 当前程序的uid  返回-1表示出现异常
     * @Description: 取得程序的uid
     * @author fengao
     */
    public static int getUid(String packageName) {
        try {
            @SuppressLint("WrongConstant") ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName
                    , PackageManager.GET_ACTIVITIES);
            return applicationInfo.uid;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public static String getMB(long size) {
        //获取到的size为：1705230
        int GB = 1024 * 1024 * 1024;//定义GB的计算常量
        int MB = 1024 * 1024;//定义MB的计算常量
        int KB = 1024;//定义KB的计算常量
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        String resultSize = "";
        if (size / GB >= 1) {
            //如果当前Byte的值大于等于1GB
            resultSize = df.format(size / (float) GB) + "GB   ";
        } else if (size / MB >= 1) {
            //如果当前Byte的值大于等于1MB
            resultSize = df.format(size / (float) MB) + "MB   ";
        } else if (size / KB >= 1) {
            //如果当前Byte的值大于等于1KB
            resultSize = df.format(size / (float) KB) + "KB   ";
        } else {
            resultSize = size + "B   ";
        }
        return resultSize;
    }

}
