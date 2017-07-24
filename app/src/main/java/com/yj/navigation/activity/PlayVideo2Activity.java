package com.yj.navigation.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yj.navigation.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by zhang on 2015/8/7.
 */


public class PlayVideo2Activity  extends AppCompatActivity {
        private ImageButton btnplay, btnstop, btnpause;
        private SurfaceView surfaceView;
        private MediaPlayer mediaPlayer;
        private int position;
        TextView textView1;
        private String filename = null;;

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            Window window = getWindow();

            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayUseLogoEnabled(false);
//            getSupportActionBar().setTitle("PlayDirectlyWithoutLayout");
            setContentView(R.layout.play_video2);


            filename=getIntent().getStringExtra("filename");

            JCVideoPlayerStandard player = (JCVideoPlayerStandard) findViewById(R.id.player_list_video);
            boolean setUp = player.setUp(filename, JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
            if (setUp) {
//                Glide.with(PlayVideo2Activity.this).load("http://a4.att.hudong.com/05/71/01300000057455120185716259013.jpg").into(player.thumbImageView);
            }
            player.startFullscreen(this, JCVideoPlayerStandard.class, filename, ""); //模拟用户点击开始按钮，NORMAL状态下点击开始播放视频，播放中点击暂停视频
             player.startButton.performClick();
//            JCVideoPlayerStandard.startFullscreen(this, JCVideoPlayerStandard.class,filename, "本地视频");


        }




    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }




    }



