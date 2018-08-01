package com.gjdl.carameaccept;

import android.content.Context;
import android.util.AttributeSet;

import org.opencv.android.CameraBridgeViewBase;

public class TestCamera extends CameraBridgeViewBase {

    public TestCamera(Context context, int cameraId) {
        super(context, cameraId);
    }

    public TestCamera(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean connectCamera(int width, int height) {
        return false;
    }

    @Override
    protected void disconnectCamera() {

    }
}
