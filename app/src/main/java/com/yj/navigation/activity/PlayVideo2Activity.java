//package com.yj.navigation.activity;
//
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.os.Bundle;
//import android.view.SurfaceView;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import com.yj.navigation.R;
//
///**
// * Created by zhang on 2015/8/7.
// */
//
//
//public class PlayVideo2Activity extends BaseActivity {
//        private ImageButton btnplay, btnstop, btnpause;
//        private SurfaceView surfaceView;
//        private MediaPlayer mediaPlayer;
//        private int position;
//        TextView textView1;
//        private String filename = null;;
//
//        public void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.play_video);
//
//
//            filename=getIntent().getStringExtra("filename");
//
//            JCVideoPlayerStandard player = (JCVideoPlayerStandard) findViewById(R.id.player_list_video);
//            boolean setUp = player.setUp(filename, JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
//            if (setUp) {
////                Glide.with(PlayVideo2Activity.this).load("http://a4.att.hudong.com/05/71/01300000057455120185716259013.jpg").into(player.thumbImageView);
//            }
//
//
//        }
//
//    @Override
//    protected void initActivityName() {
//
//    }
//
//    @Override
//    protected void setActivityBg() {
//
//    }
//
//
//    @Override
//    public void onBackPressed() {
//        if (JCVideoPlayer.backPress()) {
//            return;
//        }
//        super.onBackPressed();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        JCVideoPlayer.releaseAllVideos();
//    }
//
//
//
//
//    }
//
//
//
