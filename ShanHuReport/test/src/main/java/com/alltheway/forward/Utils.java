package com.alltheway.forward;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Utils {

    /**
     * 申请权限
     * 例如: Manifest.permission.WRITE_EXTERNAL_STORAGE
     **/
    public static void requsetPermission(Activity activity, String[] permissions) {
        try {
            if (permissions == null) {
                return;
            }
            int len = permissions.length;
            if (len > 0) {
                for (int i = 0; i < len; i++) {
                    // 使用兼容库，无需判断系统版本
                    int hasPermission = ContextCompat.checkSelfPermission(activity.getApplicationContext(),
                            permissions[i]);
                    if (hasPermission == PackageManager.PERMISSION_GRANTED) {
                        // 拥有权限，执行操作
                    } else {
                        // 没有权限，向用户请求权限
                        ActivityCompat.requestPermissions(activity, permissions, 200);
                    }
                }
            }
        } catch (Throwable e) {
        }
    }


    /**
     * toast弹窗工具类
     *
     * @param context
     * @param msg
     */
    public static void toastUtil(Context context, String msg) {
        Toast toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }

    /**
     * log日志类，用于记录上报日志日记
     */
    public static void logErrorUtil(String msg) {
        Log.e("tianze", "事件: == >" + msg);
    }

}
