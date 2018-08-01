package com.gjdl.carameaccept;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;

public class JavaCameraViewHelper implements CameraBridgeViewBase.CvCameraViewListener2{

    private JavaCameraView javaCameraView;
    private Activity       activity;

    String[] permission = new String[]{
            Manifest.permission.CAMERA
    };

    private BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(activity) {
        @Override
        public void onManagerConnected(int status) {
            switch (status){
                case LoaderCallbackInterface.SUCCESS:{
                    javaCameraView.enableView();
                }
                break;
                default:
                    super.onManagerConnected(status);
                    break;
            }

        }
    };


    public JavaCameraViewHelper(Activity context, JavaCameraView view){
        activity = context;
        javaCameraView = view;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        view.setVisibility(View.VISIBLE);
        view.setCvCameraViewListener(this);

        ActivityCompat.requestPermissions(context, permission, 1);
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Mat mRgba = inputFrame.rgba();
        return mRgba;
    }

    public void onPause(){
        if (javaCameraView != null){
            javaCameraView.disableView();
        }
    }

    public void onResume() {
        if (!OpenCVLoader.initDebug()){
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0,activity,baseLoaderCallback);
        }else{
            baseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }
}
