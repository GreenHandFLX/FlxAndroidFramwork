package com.xinxi.ergatebesh.common.MultiLanguage;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

/**
 * 2021.7.21
 * 多语言切换的帮助类
 */
public class MultiLanguageUtil {

    private static final String TAG = "MultiLanguageUtil";
    private static MultiLanguageUtil instance;
    private Context mContext;
    public static final String SAVE_LANGUAGE = "save_language";

    public static void init(Context mContext) {
        if (instance == null) {
            synchronized (MultiLanguageUtil.class) {   //类锁
                if (instance == null) {
                    instance = new MultiLanguageUtil(mContext);
                }
            }
        }
    }

    public static MultiLanguageUtil getInstance() {
        if (instance == null) {
            throw new IllegalStateException("You must be init MultiLanguageUtil first");
        }
        return instance;
    }

    private MultiLanguageUtil(Context context) {
        this.mContext = context;
    }

    /**
     * 设置语言（Android 7.0 及以下版本）
     */
    public void setConfiguration() {
        Locale targetLocale = getLanguageLocale();
        Configuration configuration = mContext.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(targetLocale);
        } else {
            configuration.locale = targetLocale;
        }
            Resources resources = mContext.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            resources.updateConfiguration(configuration, dm);//语言更换生效的代码!
    }

    /**
     * 如果不是英文、俄文、中文，默认返回简体中文
     * @return
     */
    private Locale getLanguageLocale() {
        int languageType = CommSharedUtil.getInstance(mContext).getInt(MultiLanguageUtil.SAVE_LANGUAGE, 0);
        if (languageType == LanguageType.LANGUAGE_FOLLOW_SYSTEM) {
            Locale sysLocale= getSysLocale();
            return sysLocale;
        } else if (languageType == LanguageType.LANGUAGE_EN) {
            return Locale.ENGLISH;
        } else if (languageType == LanguageType.LANGUAGE_CHINESE_SIMPLIFIED) {
            return Locale.SIMPLIFIED_CHINESE;
        } else if (languageType == LanguageType.LANGUAGE_CHINESE_TRADITIONAL) {
            return Locale.TRADITIONAL_CHINESE;
        }
        getSystemLanguage(getSysLocale());
        Log.e(TAG, "getLanguageLocale" + languageType + languageType);
        return Locale.SIMPLIFIED_CHINESE;
    }

    private String getSystemLanguage(Locale locale) {
        return locale.getLanguage() + "_" + locale.getCountry();

    }

    //以上获取方式需要特殊处理一下
    public Locale getSysLocale() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }

    /**
     * 更新语言，发送订阅消息，将来activity可以取到订阅消息
     *
     * @param languageType
     */
    public void updateLanguage(int languageType) {
        CommSharedUtil.getInstance(mContext).putInt(MultiLanguageUtil.SAVE_LANGUAGE, languageType);
       // MultiLanguageUtil.getInstance().setConfiguration();
        MultiLanguageUtil.createConfigurationResources(mContext);
        EventBus.getDefault().post(new OnChangeLanguageEvent(languageType));
    }

    /*
    *  实际项目中根据项目语言的种类，把此方法打开
    * */
    public String getLanguageName(Context context) {
//        int languageType = CommSharedUtil.getInstance(context).getInt(MultiLanguageUtil.SAVE_LANGUAGE,LanguageType.LANGUAGE_FOLLOW_SYSTEM);
//        if (languageType == LanguageType.LANGUAGE_EN) {
//            return mContext.getString(R.string.setting_language_english);
//        } else if (languageType == LanguageType.LANGUAGE_CHINESE_SIMPLIFIED) {
//            return mContext.getString(R.string.setting_simplified_chinese);
//        } else if (languageType == LanguageType.LANGUAGE_CHINESE_TRADITIONAL) {
//            return mContext.getString(R.string.setting_traditional_chinese);
//        }
//        return mContext.getString(R.string.setting_language_auto);
        return "";
    }

    /**
     * 获取到用户保存的语言类型
     * @return
     */
    public int getLanguageType() {
        int languageType = CommSharedUtil.getInstance(mContext).getInt(MultiLanguageUtil.SAVE_LANGUAGE, LanguageType.LANGUAGE_FOLLOW_SYSTEM);
         if (languageType == LanguageType.LANGUAGE_CHINESE_SIMPLIFIED) {
            return LanguageType.LANGUAGE_CHINESE_SIMPLIFIED;
        } else if (languageType == LanguageType.LANGUAGE_CHINESE_TRADITIONAL) {
            return LanguageType.LANGUAGE_CHINESE_TRADITIONAL;
        } else if (languageType == LanguageType.LANGUAGE_FOLLOW_SYSTEM) {
           return LanguageType.LANGUAGE_FOLLOW_SYSTEM;
        }
        Log.e(TAG, "getLanguageType" + languageType);
        return languageType;
    }


    /*
    * 兼容Android 7 上下版本。   Build.VERSION.SDK_INT 软件app安装在哪个手机上；   Build.VERSION_CODES.N是Android版本号
    * */
    public static Context attachBaseContext(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return createConfigurationResources(context);
        } else {
           MultiLanguageUtil.getInstance().setConfiguration();
            return context;
        }
    }



    /*
     *
     * */
    @TargetApi(Build.VERSION_CODES.N)
    private static Context createConfigurationResources(Context context) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale=getInstance().getLanguageLocale();
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }
}
