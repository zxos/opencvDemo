package com.gjdl.carameaccept;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private BitmapLruCache lruCache;
    private int width;
    private int height;

    public CameraView(Context context) {
        this(context, null);
        init();
    }

    public CameraView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public CameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        holder = getHolder();
        holder.addCallback(this);

        lruCache = new BitmapLruCache();


    }



    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void setBitmap(int srcId){



        Message message = handler.obtainMessage();
        message.what = 1;
        message.arg1 = srcId;
        handler.sendMessage(message);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    int picId = msg.arg1;

                    if (lruCache.get(String.valueOf(picId)) == null){
                        lruCache.put(String.valueOf(picId), BitmapFactory.decodeResource(getResources(), picId));
                    }

                    Canvas canvas = holder.lockCanvas();

                    canvas.drawBitmap(lruCache.get(String.valueOf(picId)), 0, 0, null);

                    holder.unlockCanvasAndPost(canvas);


                    break;
            }
        }
    };


    public void setBitmap(Bitmap bitmap){


        Canvas canvas = holder.lockCanvas();

        canvas.drawBitmap(bitmap, 0, 0, null);

        holder.unlockCanvasAndPost(canvas);

    }


    private class DrawThread extends Thread{

        private SurfaceHolder holder;

        public DrawThread(SurfaceHolder holder){
            this.holder = holder;
        }

        @Override
        public void run() {


            Canvas canvas = holder.lockCanvas();

            holder.unlockCanvasAndPost(canvas);


        }
    }




}
