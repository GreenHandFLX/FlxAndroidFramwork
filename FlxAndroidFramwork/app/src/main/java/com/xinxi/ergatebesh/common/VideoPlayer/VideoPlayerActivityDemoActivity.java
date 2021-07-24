package com.xinxi.ergatebesh.common.VideoPlayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.xinxi.ergatebesh.R;
import com.xinxi.ergatebesh.common.VideoPlayer.view.CustomVideoView;

public class VideoPlayerActivityDemoActivity extends AppCompatActivity {

    private CustomVideoView videoPlayer;//  CustomVideoView， 自定义的视频播放view视图。这里面的回调函数就是主要逻辑
    private OrientationUtils orientationUtils; // 处理屏幕旋转的的逻辑
    private boolean isPlay;
    private boolean isPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player_demo);
        // 1 initviews
        initViews();
        // 2 initData
        initData();
    }

    public void initViews() {
        videoPlayer = findViewById(R.id.video_player);
    }

    public void initData() {
        //http://ali.cdn.kaiyanapp.com/1626677990172_a480dda8.mp4?auth_key=1626870042-0-0-7513060f9acad3cf4f8fa25209806730
        // 设置视频播放地址
        videoPlayer.setUp("http://ali.cdn.kaiyanapp.com/1626677990172_a480dda8.mp4?auth_key=1626870042-0-0-7513060f9acad3cf4f8fa25209806730", true, "");
        // 设置视频标题可见性
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        // 设置视频标题
        videoPlayer.getTitleTextView().setText("测试视频标题");
        // 设置标题是否单行
        videoPlayer.getTitleTextView().setSingleLine(true);
        videoPlayer.getTitleTextView().setEllipsize(TextUtils.TruncateAt.END);
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        orientationUtils = new OrientationUtils(this, videoPlayer);
        orientationUtils.setEnable(false);
        videoPlayer.setIsTouchWiget(true);
        videoPlayer.setRotateViewAuto(false);
        videoPlayer.setLockLand(false);
        videoPlayer.setShowFullAnimation(false);
        videoPlayer.setNeedLockFull(true);
        // 设置按钮的监听事件
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
                videoPlayer.startWindowFullscreen(VideoPlayerActivityDemoActivity.this, true, true);
            }
        });
        // 设置底部进度条样式
        videoPlayer.setBottomProgressBarDrawable(getResources().getDrawable(R.drawable.video_new_progress));
        videoPlayer.setDialogVolumeProgressBar(getResources().getDrawable(R.drawable.video_new_volume_progress_bg));
        videoPlayer.setDialogProgressBar(getResources().getDrawable(R.drawable.video_new_progress));
        videoPlayer.setBottomShowProgressBarDrawable(getResources().getDrawable(R.drawable.video_new_seekbar_progress),
                getResources().getDrawable(R.drawable.video_new_seekbar_thumb));
        videoPlayer.setDialogProgressColor(getResources().getColor(R.color.black), -11);
        //设置返回按钮的监听事件
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPause = false;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }


}