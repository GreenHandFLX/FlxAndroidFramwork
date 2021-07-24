package com.xinxi.ergatebesh.common;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.facebook.drawee.backends.pipeline.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ShareUtils {
    //1 图片在本系统中将以一定格式存储，比如/myAndroidApp/now_image_id/+ ".png"
    //2 now_image_id为图片id，图片id很重要，主要应用在：1 根据图片id阻止多次下载。       2 下载以后，放在具体位置，并用这种有id的形式保存 ，分享照片的时候，确定分享的是那张      3  唯一性
    private static String now_image_id;

    // 分享照片
    public static void shareMsg(String activityTitle, String msgTitle, String msgText, String imgPath, Activity activity) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/jpg");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".fileProvider", new File(imgPath));
            intent.setDataAndType(contentUri, "image/jpg");
        }
        activity.startActivity(Intent.createChooser(intent, activityTitle));
    }

    // 下载照片
   // https://photo.tuchong.com/15818428/f/439017104.jpg
    public static void downloadImage(){
        File f = new File("/sdcard/" + now_image_id + ".png");
        if (f.exists()) {
            System.out.println("早已经下载成功");
            return;
        }
        // 调用线程下载
        // 1 下载完成后，在handler中,将数据转成bytes
        // final byte[] bytes = response.body().bytes();
        // 2 把bytes转成Bitmap
        // bitmap_cache = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        // 3 保存照片
        // saveImageByBitmap(bitmap_cache)
    }



    // 保存照片
    public static void saveImageByBitmap(Bitmap bitmap){
        File f = new File("/sdcard/" + now_image_id + ".png");
        System.out.println("存储 -> " + f.getPath());
        try {
            f.createNewFile();
        } catch (IOException e) {
            System.out.println("存储失败" );
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("存储失败" );
        }
    }
}
