package com.aserbao.cameravideorecord.cameraDemo01;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aserbao.cameravideorecord.R;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 这个一个带录音功能的Activity
 */
public class RecordingActivity extends AppCompatActivity {
    @BindView(R.id.start_recording)
    Button mStartRecording;
    @BindView(R.id.play_recording)
    Button mPlayRecording;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.image_view)
    ImageView mImageView;
    private MediaRecorder mRecorder;
    private boolean isRecording = false;
    private String mAbsolutePath;
    private boolean isPlaying = false;
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        ButterKnife.bind(this);
        mPlayRecording.setClickable(false);
    }

    @OnClick({R.id.start_recording, R.id.play_recording})
    public void onViewClicked(View view){
        switch (view.getId()) {
            case R.id.start_recording:
                if (!isRecording) {
                    mPlayRecording.setClickable(false);
                    mStartRecording.setText("停止录音");
                    isRecording = true;
                    mStartRecording();
                } else {
                    mPlayRecording.setClickable(true);
                    mStartRecording.setText("开始录音");
                    isRecording = false;
                    mStopRecording();
                }
                break;
            case R.id.play_recording:
                if (!isPlaying) {
                    try {
                        mStartRecording.setClickable(false);
                        isPlaying = true;
                        mPlayRecording.setText("暂停播放");
                        mMediaPlayer = new MediaPlayer();
                        mMediaPlayer.setDataSource(mAbsolutePath);
                        mMediaPlayer.prepare();
                        mMediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    mStartRecording.setClickable(true);
                    if (mMediaPlayer != null) {
                        isPlaying = false;
                        mPlayRecording.setText("开始播放");
                        mMediaPlayer.pause();
                    }
                }
                break;
        }
    }

    private void mStopRecording() {
        mRecorder.stop();
        mRecorder.reset();
        mRecorder.release();
    }

    private void mStartRecording() {
        try {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            String s = Environment.getExternalStorageDirectory().getCanonicalFile().getAbsolutePath() + "/aserbao";
            boolean b = new File(s).mkdir();
            mAbsolutePath = new File(s + "/" + String.valueOf(System.currentTimeMillis()) + ".3gp").getAbsolutePath();
            mRecorder.setOutputFile(mAbsolutePath);
            mRecorder.prepare();
            mRecorder.start();
            updateAudioSize();
            mTextView.setText("录音保存的路径名为：" + mAbsolutePath.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final Handler mHandler = new Handler();
    private Runnable mUpdateMicStatusTimer = new Runnable() {
        public void run() {
            updateAudioSize();
        }
    };

        private int BASE = 600;
        private int SPACE = 300;// 间隔取样时间
    private void updateAudioSize() {
            if (mRecorder != null && mImageView != null){
                int audio = mRecorder.getMaxAmplitude()/BASE;
                int db = 0;
                if (audio > 1)
                    db = (int) (20 * Math.log10(audio));
                Log.e("audio_size", "updateAudioSize: " + String.valueOf(db/4) + "==================getMaxAmplitude: " + String.valueOf(mRecorder.getMaxAmplitude()));
                switch (db / 4) {
                    case 0:
                       mImageView.setImageResource(R.drawable.message_vioce01);
                        break;
                    case 1:
                        mImageView.setImageResource(R.drawable.message_vioce01);
                        break;
                    case 2:
                       mImageView.setImageResource(R.drawable.message_vioce02);
                        break;
                    case 3:
                       mImageView.setImageResource(R.drawable.message_vioce03);
                        break;
                    case 4:
                       mImageView.setImageResource(R.drawable.message_vioce04);
                        break;
                    case 5:
                       mImageView.setImageResource(R.drawable.message_vioce05);
                        break;
                    case 6:
                       mImageView.setImageResource(R.drawable.message_vioce06);
                        break;
                    case 7:
                        mImageView.setImageResource(R.drawable.message_vioce07);
                        break;
                    case 8:
                        mImageView.setImageResource(R.drawable.message_vioce08);
                        break;
                    default:
                       mImageView.setImageResource(R.drawable.message_vioce08);
                        break;
                }
                mHandler.postDelayed(mUpdateMicStatusTimer, SPACE);
            }
        }

}
