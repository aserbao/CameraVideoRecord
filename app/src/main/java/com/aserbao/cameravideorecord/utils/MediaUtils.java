package com.aserbao.cameravideorecord.utils;

import android.media.MediaRecorder;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

/**
 * description:
 * Created by aserbao on 2017/11/29.
 */


public class MediaUtils {


    public static MediaRecorder startRecording(String mAbsolutePath){
        if (TextUtils.isEmpty(mAbsolutePath)){
            return null;
        }
        try {
            MediaRecorder mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            mRecorder.setOutputFile(mAbsolutePath);
            mRecorder.prepare();
            mRecorder.start();
            return mRecorder;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
