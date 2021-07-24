package com.xinxi.ergatebesh;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.xinxi.ergatebesh.common.MultiLanguage.MultiLanguageUtil;
import com.xuexiang.xui.XUI;

public class App  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiLanguageUtil.init(this);
        XUI.init(this);
        Fresco.initialize(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    // 放到全局中，不重新创建对象
    private String token;
    private String i18n;

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getI18n() {
        return i18n;
    }
    public void setI18n(String i18n) {
        this.i18n = i18n;
    }
}
