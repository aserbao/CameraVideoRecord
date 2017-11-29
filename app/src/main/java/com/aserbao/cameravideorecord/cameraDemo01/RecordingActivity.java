package com.aserbao.cameravideorecord.cameraDemo01;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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
    private MediaRecorder mRecorder;
    private boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.start_recording, R.id.play_recording})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start_recording:
                if(!isRecording) {
                    mStartRecording.setText("停止录音");
                    isRecording = true;
                    mStartRecording();
                }else{
                    mStartRecording.setText("开始录音");
                    isRecording = false;
                    mStopRecording();
                }
                break;
            case R.id.play_recording:
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
            mRecorder.setAudioChannels(80);
            String s = Environment.getExternalStorageDirectory().getCanonicalFile().getAbsolutePath() + "/aserbao";
            boolean b = new File(s).mkdir();
            mRecorder.setOutputFile(new File(s + "/"+ String.valueOf(System.currentTimeMillis()) + ".3gp").getAbsolutePath());
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
