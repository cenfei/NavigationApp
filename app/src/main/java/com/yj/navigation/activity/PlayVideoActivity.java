package com.yj.navigation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;

import android.widget.TextView;

import com.yj.navigation.R;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang on 2015/8/7.
 */


import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;


public class PlayVideoActivity extends BaseActivity implements OnClickListener{
        private ImageButton btnplay, btnstop, btnpause;
        private SurfaceView surfaceView;
        private MediaPlayer mediaPlayer;
        private int position;
        TextView textView1;
        private String filename = null;;

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.play_video);


            filename=getIntent().getStringExtra("filename");

            btnplay=(ImageButton)this.findViewById(R.id.btnplay);
            btnstop=(ImageButton)this.findViewById(R.id.btnstop);
            btnpause=(ImageButton)this.findViewById(R.id.btnpause);
            textView1=(TextView)this.findViewById(R.id.textView1);

            btnstop.setOnClickListener(this);
            btnplay.setOnClickListener(this);
            btnpause.setOnClickListener(this);

            mediaPlayer=new MediaPlayer();
            surfaceView=(SurfaceView) this.findViewById(R.id.surfaceView);

            mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    textView1.setText("播放完毕!...");
                }
            });
            mediaPlayer.setOnErrorListener(new OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    textView1.setText("播放出错!...");
                    return false;
                }
            });
            mediaPlayer.setOnPreparedListener(new OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {
                    textView1.setText("准备就绪!...");
                }
            });
            mediaPlayer.setOnSeekCompleteListener(new OnSeekCompleteListener() {

                @Override
                public void onSeekComplete(MediaPlayer mp) {
                    textView1.setText("进度拖放完毕!...");
                }
            });


            //设置SurfaceView自己不管理的缓冲区
            surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            surfaceView.getHolder().addCallback(new Callback() {
                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {

                }

                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    if (position>0) {
                        try {
                            //开始播放
                            play();
                            //并直接从指定位置开始播放
                            mediaPlayer.seekTo(position);
                            position=0;
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }
                }
                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                           int height) {
                }
            });
        }

    @Override
    protected void initActivityName() {

    }

    @Override
    protected void setActivityBg() {

    }

    @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnplay:
                    play();
                    break;
                case R.id.btnpause:
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }else{
                        mediaPlayer.start();
                    }
                    break;
                case R.id.btnstop:
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    break;
                default:
                    break;
            }
        }
        @Override
        protected void onPause() {
            //先判断是否正在播放
            if (mediaPlayer.isPlaying()) {
                //如果正在播放我们就先保存这个播放位置
                position=mediaPlayer.getCurrentPosition();
                mediaPlayer.stop();
            }
            super.onPause();
        }

        private void play() {
            try {
                mediaPlayer.reset();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                //设置需要播放的视频
                mediaPlayer.setDataSource(filename);
                //把视频画面输出到SurfaceView
                mediaPlayer.setDisplay(surfaceView.getHolder());
                mediaPlayer.prepare();
                //播放
                mediaPlayer.start();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

    }



