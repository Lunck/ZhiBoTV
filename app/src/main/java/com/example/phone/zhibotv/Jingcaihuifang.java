package com.example.phone.zhibotv;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.phone.zhibotv.utils.CommonUtil;
import com.example.phone.zhibotv.utils.LightnessController;
import com.example.phone.zhibotv.utils.VolumeController;
import com.example.phone.zhibotv.widget.FullSizeVideoView;

import java.util.Timer;
import java.util.TimerTask;

public class Jingcaihuifang extends AppCompatActivity implements Handler.Callback,CompoundButton.OnCheckedChangeListener, MediaPlayer.OnPreparedListener, SeekBar.OnSeekBarChangeListener, View.OnTouchListener {

    private static final int UPDATE_PROGRESS = 59;
    private static final String TAG = MainActivity.class.getSimpleName();
    private FullSizeVideoView mVideoView;
    private CheckBox mPlay;
    private TextView mCurrentProgress;
    private Handler mHandler;
    private SeekBar mSeekProgress;
    private TextView mTotalProgress;
    private CheckBox mFullScreen;
    /**
     * 竖屏情况下VideoView的高度
     */
    private int mVideoHeight;
    /**
     * 横屏的标记
     */
    private boolean isLandscape;
    /**
     * 双击退出的一个标记
     */
    private boolean isExit;
    /**
     * 一个动作开始时 记录的x位置
     */
    private float xPosition;
    /**
     * 一个动作开始时 记录的y的位置
     */
    private float yPosition;
    /**
     *  在一整个动作过程中，上一个回调中x的位置
     */
    private float xLastPosition;
    /**
     *  在一个整个动作过程中，上一个回调中y的位置
     */
    private float yLastPosition;
    /**
     * 滑动时的一个临界值
     */
    private int criticalValue = 20;
    private int mScreenWidth;
    private int mScreenHeight;
    private LinearLayout mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jingcaihuifang);

        initView();
    }

    private void initView() {
        Intent intent=getIntent();
        mVideoView = (FullSizeVideoView) findViewById(R.id.teach_video);
        // 设置播放地址
      //  mVideoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.dance);
        // 设置网络或本地地址   但是别忘了添加权限
            mVideoView.setVideoPath(intent.getStringExtra("url"));
        // 添加媒体控制器
//        mVideoView.setMediaController(new MediaController(this));
        // 播放
//        mVideoView.start();
        // 设置准备完成的监听
        mVideoView.setOnPreparedListener(this);

        mPlay = (CheckBox) findViewById(R.id.teach_media_play);
        mPlay.setOnCheckedChangeListener(this);
        mCurrentProgress = (TextView) findViewById(R.id.teach_media_current_progress);
        mHandler = new Handler(this);
        mSeekProgress = (SeekBar) findViewById(R.id.teach_media_seek_progress);
        mTotalProgress = (TextView) findViewById(R.id.teach_media_total_progress);
        mSeekProgress.setOnSeekBarChangeListener(this);
        mFullScreen = (CheckBox) findViewById(R.id.teach_media_full_screen);
        mFullScreen.setOnCheckedChangeListener(this);
        mVideoView.setOnTouchListener(this);

        // 获取屏幕宽高
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;
        //
        mController = (LinearLayout) findViewById(R.id.teach_media_controller);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.teach_media_play:
                if (isChecked) {
                    mVideoView.start();
                    mHandler.sendEmptyMessage(UPDATE_PROGRESS);
                }else{
                    mVideoView.pause();
                    // Handler 也可以移除MessageQueue中的消息
                    mHandler.removeMessages(UPDATE_PROGRESS);
                }
                break;
            case R.id.teach_media_full_screen:
                if (isChecked) {
                    // 记录原来的高度
                    mVideoHeight = mVideoView.getHeight();
                    // 切换全屏状态 ,给窗口添加一个全屏标记，请求请求横屏
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    // 改变View的高度，将高度设置为MATCH_PARENT
                    ViewGroup.LayoutParams layoutParams = mVideoView.getLayoutParams();
                    layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    mVideoView.setLayoutParams(layoutParams);
                }else{
                    // 取消全屏  清除全屏标记，请求竖屏
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    // 还原View的高度
                    ViewGroup.LayoutParams layoutParams = mVideoView.getLayoutParams();
                    layoutParams.height = mVideoHeight;
                    mVideoView.setLayoutParams(layoutParams);
                }
                break;
        }
    }

    /**
     *  屏幕在旋转的时候，配置信息会发生改变
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 旋转后为横屏
            isLandscape = true;
        }else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            // 旋转后为竖屏
            isLandscape = false;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isLandscape) {
                // 修改为非全屏状态
                mFullScreen.setChecked(false);
                return true;
            }
            if (!isExit) {
                // 修改标记位
                isExit = true;
                Toast.makeText(Jingcaihuifang.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                // 调用定时器的定时任务      ① 具体的任务  ② 延迟时间
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // 还原状态
                        isExit = false;
                    }
                },3 * 1000);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case UPDATE_PROGRESS:
                // 获取当前进度（时间）
                int currentPosition = mVideoView.getCurrentPosition();
                // 将时间设置到当前进度上
                mCurrentProgress.setText(CommonUtil.format(currentPosition));
                // 更改SeekBar的进度
                mSeekProgress.setProgress(currentPosition);
                // 延迟一秒发送消息，继续回调自己
                mHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS,1000);
                break;
        }
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        // 获取总时长
        int duration = mVideoView.getDuration();
        // 设置给进度的最大值
        mSeekProgress.setMax(duration);
        // 设置到总时长的Text上
        mTotalProgress.setText(CommonUtil.format(duration));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            mHandler.removeMessages(UPDATE_PROGRESS);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mVideoView.seekTo(seekBar.getProgress());
        mHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS,500);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (isLandscape) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    xPosition = event.getX();
                    yPosition = event.getY();
                    xLastPosition = event.getX();
                    yLastPosition = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float xDelta = event.getX() - xLastPosition;
                    float yDelta = event.getY() - yLastPosition;
                    if (Math.abs(xDelta) > Math.abs(yDelta) && Math.abs(xDelta) > criticalValue) {
                        // 横向滑动
                        if (xDelta > 0) {
                            // TODO 快进
                            Log.e(TAG, "onTouch: 快进" );
                            goFroward(xDelta);
                        }else{
                            // TODO 快退
                            Log.e(TAG, "onTouch:快退 " );
                            backForward(xDelta);
                        }

                    }else if (Math.abs(yDelta) > Math.abs(xDelta) && Math.abs(yDelta) > criticalValue){
                        // 纵向滑动
                        if (event.getX() > mScreenHeight / 2) {
                            // 改变音量
                            if (yDelta > 0) {
                                // TODO 音量减
                                Log.e(TAG, "onTouch: 音量减" );
                                VolumeController.turnDown(this,yDelta,mScreenWidth);
                            }else{
                                // TODO 音量加
                                Log.e(TAG, "onTouch: 音量加" );
                                VolumeController.turnUp(this,yDelta,mScreenWidth);
                            }
                        }else{
                            // 改变亮度
                            if (yDelta > 0) {
                                // TODO 亮度降低
                                Log.e(TAG, "onTouch: 亮度降低" );
                                LightnessController.turnDown(this,yDelta,mScreenWidth);
                            }else{
                                // TODO 亮度提高
                                Log.e(TAG, "onTouch: 亮度提高" );
                                LightnessController.turnUp(this,yDelta,mScreenWidth);
                            }
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (Math.abs(event.getX() - xPosition) < criticalValue && Math.abs(event.getY() - yPosition) < criticalValue) {
                        showOrHideController();
                    }
                    break;
            }
            return true;
        }
        return false;
    }

    private void showOrHideController() {
        if (mController.getVisibility() == View.GONE) {
            mController.setVisibility(View.VISIBLE);
            mController.startAnimation(AnimationUtils.loadAnimation(this,R.anim.controller_in));
        }else if (mController.getVisibility() == View.VISIBLE){
            mController.setVisibility(View.GONE);
            mController.startAnimation(AnimationUtils.loadAnimation(this,R.anim.controller_out));
        }
    }

    /**
     *  快退
     * @param xDelta  负值
     */
    private void backForward(float xDelta) {
        // 获取当前进度
        int currentPosition = mVideoView.getCurrentPosition();
        // 获取总时间
        int duration = mVideoView.getDuration();
        // 计算变化值
        int change = (int) (0.1 * duration * xDelta / mScreenHeight);
        //　变化后的值
        int endPosition = Math.max(0, currentPosition + change);
        //
        mVideoView.seekTo(endPosition);
    }

    /**
     * 快进
     * @param xDelta
     */
    private void goFroward(float xDelta) {
        // 获取当前进度
        int currentPosition = mVideoView.getCurrentPosition();
        // 获取总时长
        int duration = mVideoView.getDuration();
        // 计算出一个快进的值
        int change = (int) (0.1 * duration * xDelta / mScreenHeight);
        // 计算出变化之后的值
        int endPosition = Math.min(duration, currentPosition + change);
        //
        mVideoView.seekTo(endPosition);
    }
}
