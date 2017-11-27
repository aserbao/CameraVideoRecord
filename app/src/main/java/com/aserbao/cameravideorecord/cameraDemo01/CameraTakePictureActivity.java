package com.aserbao.cameravideorecord.cameraDemo01;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import com.aserbao.cameravideorecord.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraTakePictureActivity extends AppCompatActivity implements SurfaceHolder.Callback, Camera.PictureCallback {
    private static final String TAG = "CameraTakePictureActivi";


    @BindView(R.id.picture_sv)
    SurfaceView mPictureSv;
    @BindView(R.id.image_view)
    ImageView mImageView;
    private Camera mCamera;
    private SurfaceHolder mHolder;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_take_picture);
        ButterKnife.bind(this);
        mHolder = mPictureSv.getHolder();
        mHolder.setFormat(PixelFormat.TRANSPARENT);
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void openCamera() {
        mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        Camera.Parameters parameters = mCamera.getParameters();
        mCamera.setParameters(parameters);
        mCamera.setDisplayOrientation(90);
        try {
            mCamera.setPreviewDisplay(mHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.startPreview();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        if (data != null) {
            Log.e(TAG, "onPictureTaken: " + data.toString());
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        Log.e(TAG, "onPointerCaptureChanged: " + hasCapture);
    }

    @OnClick({R.id.take_picture, R.id.btn_recording, R.id.btn_start_preview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start_preview:
                openCamera();
                mImageView.setVisibility(View.GONE);
                break;
            case R.id.take_picture:
                mCamera.takePicture(new Camera.ShutterCallback() {
                    @Override
                    public void onShutter() {
                        Log.e(TAG, "onShutter: " + "按下快门");
                    }
                }, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        if (data != null) {
                            showImageView(data);
                            Log.e(TAG, "onPictureTaken: " + data.toString());
                        }
                    }
                }, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        if (data != null) {
                            showImageView(data);
                            Log.e(TAG, "onPictureTaken2: " + data.toString());
                        }
                    }
                });
                break;
            case R.id.btn_recording:

                break;
        }
    }

    private void showImageView(byte[] data) {
        mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        mImageView.setImageBitmap(mBitmap);
        mImageView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCamera.stopPreview();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCamera.release();
    }
}
