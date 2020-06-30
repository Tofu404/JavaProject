package com.alltheway.forward;

import android.Manifest;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.pw.WinLib;
import com.pw.us.AdInfo;
import com.pw.us.IAdListener;
import com.pw.us.IRewardAdListener;
import com.pw.us.Setting;
import com.pw.view.NativeAdContainer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    //原生视频广告
    public static final String NATIVE = BuildConfig.NATIVEADID;

    //激励视频广告
    public static final String VIDEO = BuildConfig.REWARDADID;

    //加载广告次数
    private static int LOAD_COUNT = 0;

    @BindView(R.id.native_btn)
    Button nativeBtn;
    @BindView(R.id.reward_btn)
    Button rewardBtn;
    @BindView(R.id.app_icon)
    ImageView appIcon;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.layout)
    RelativeLayout layout;
    @BindView(R.id.container)
    NativeAdContainer container;
    private MediaPlayer mediaPlayer;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // 动态申请权限
        Utils.requsetPermission(this, new String[]{Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.REQUEST_INSTALL_PACKAGES, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE});
        
    }

    @OnClick(R.id.native_btn)
    public void clickNativeBtn() {
        showNativeAd();
    }

    @OnClick(R.id.reward_btn)
    public void clickLoadRewardBtn() {
        showRewardAd();
    }

    /**
     * 加载和展示原生广告
     */
    private void showNativeAd() {
        Setting setting = new Setting(this, Setting.TYPE_NATIVE, NATIVE, new IAdListener() {
            @Override
            public void onError(String s) {
                Utils.toastUtil(MainActivity.this, s);
                //当没有加载到广告的时候（填充率的问题）继续进行广告的加载，直到成功加载到广告
                if (LOAD_COUNT < 5) {
                    nativeBtn.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showNativeAd();
                            LOAD_COUNT++;
                        }
                    }, 2000);
                } else {
                    Utils.toastUtil(MainActivity.this, "没有加载到广告");
                }
            }

            @Override
            public void onLoaded(AdInfo adInfo, Setting setting) {
                Utils.logErrorUtil("onLoaded");
                Glide.with(MainActivity.this).load(adInfo.getIconUrl()).into(appIcon);
                title.setText(adInfo.getTitle());
                description.setText(adInfo.getDesc());
                WinLib.regView(setting);
            }

            @Override
            public void onShowed() {
                Utils.logErrorUtil("onShowed");
                Utils.toastUtil(MainActivity.this, "加载成功");

                //自动点击广告位,使其触发下载
                layout.performClick();
            }

            @Override
            public void onClicked() {
                Utils.logErrorUtil("onClicked");

            }

            @Override
            public void onDownloadStarted() {
                Utils.logErrorUtil("onDownloadStarted");
            }

            @Override
            public void onDownloadFinished(String s) {
                Utils.logErrorUtil("onDownloadFinished");

                //完成下载的时候去提示测试人员去点击安装应用
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.tip_sound);
                mediaPlayer.start();
            }

            @Override
            public void onInstalled() {
                Utils.logErrorUtil("onInstalled");
            }
        });

        setting.setAdViewContainer(container);
        setting.setAdViewGroup(layout);
        WinLib.load(setting);
    }

    /**
     *
     */
    private void showRewardAd() {
        Setting setting = new Setting(MainActivity.this, Setting.TYPE_REWARDED, VIDEO, new IRewardAdListener() {
            @Override
            public void onError(String s) {
                Utils.toastUtil(MainActivity.this, s);
                Utils.logErrorUtil("onError");
            }

            @Override
            public void onLoaded(Setting setting) {
                Utils.toastUtil(MainActivity.this, "加载成功");
                WinLib.show(setting);
                Utils.logErrorUtil("onLoaded");
            }

            @Override
            public void onShowed() {
                Utils.logErrorUtil("onShowed");
            }

            @Override
            public void onClosed() {
                Utils.logErrorUtil("onClosed");
            }

            @Override
            public void onVideoComplete() {
                Utils.logErrorUtil("onVideoComplete");
            }

            @Override
            public void onDownloadStarted(String s) {
                Utils.logErrorUtil("onDownloadStarted");
            }

            @Override
            public void onDownloadFinished(String s, String s1) {
                Utils.logErrorUtil("onDownloadFinished");
            }

            @Override
            public void onInstalled(String s, String s1) {
                Utils.logErrorUtil("onInstalled");
            }
        });

        WinLib.load(setting);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //释放音乐播放器的资源
        mediaPlayer.release();
        mediaPlayer = null;
    }
}