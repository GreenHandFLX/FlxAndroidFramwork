package com.xinxi.ergatebesh.common.Base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.xinxi.ergatebesh.R;
import com.xinxi.ergatebesh.common.MultiLanguage.MultiLanguageUtil;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import java.util.ArrayList;
import java.util.List;
/*
*          2021.7.21   by   flx
* */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MultiLanguageUtil.attachBaseContext(newBase));
    }

    /*
    *
    * 关于授权：
    *   第一步：请求权限   requestPermission
    *   第二步：检测所有的权限是否都已授权   checkPermissions
    *   第三步：获取权限集中需要申请权限的列表  getDeniedPermissions
    *   第四步：处理权限请求回调   onRequestPermissionsResult
    *   第五步：确认所有的权限是否都已授权  verifyPermissions
    *   第六步：授权成功处理函数   permissionSuccess   or   permissionFail
     *
    * */


    //权限相关
    private final String TAG = "PermissionsUtil";
    private int REQUEST_CODE_PERMISSION = 0x00099;

    /**
     * 请求权限
     *
     * @param permissions 请求的权限
     * @param requestCode 请求权限的请求码
     */
    public void requestPermission(String[] permissions, int requestCode) {
        this.REQUEST_CODE_PERMISSION = requestCode;
        if (checkPermissions(permissions)) {
            permissionSuccess(REQUEST_CODE_PERMISSION);
        } else {
            List<String> needPermissions = getDeniedPermissions(permissions);
            ActivityCompat.requestPermissions(this, needPermissions.toArray(new String[needPermissions.size()]), REQUEST_CODE_PERMISSION);
        }
    }

    /**
     * 检测所有的权限是否都已授权
     *
     * @param permissions
     * @return
     */
    private boolean checkPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     */
    private List<String> getDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                needRequestPermissionList.add(permission);
            }
        }
        return needRequestPermissionList;
    }


    /**
     * 系统请求权限回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (verifyPermissions(grantResults)) {
                permissionSuccess(REQUEST_CODE_PERMISSION);
            } else {
                permissionFail(REQUEST_CODE_PERMISSION);
            }
        }
    }

    /**
     * 确认所有的权限是否都已授权
     *
     * @param grantResults
     * @return
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取权限成功
     *
     * @param requestCode
     */
    public void permissionSuccess(int requestCode) {
        new MaterialDialog.Builder(this)
                .iconRes(R.mipmap.icon_tip)
                .title("提示")
                .content("获取权限成功")
                .positiveText("确定")
                .show();
    }

    /**
     * 权限获取失败
     *
     * @param requestCode
     */
    public void permissionFail(int requestCode) {
        new MaterialDialog.Builder(this)
                .iconRes(R.mipmap.icon_tip)
                .title("提示")
                .content("权限获取失败")
                .positiveText("确定")
                .show();
    }
}
