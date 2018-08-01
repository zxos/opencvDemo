package com.gjdl.carameaccept;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.protobuf.InvalidProtocolBufferException;
import com.vilyever.socketclient.SocketClient;
import com.vilyever.socketclient.helper.SocketClientDelegate;
import com.vilyever.socketclient.helper.SocketClientReceivingDelegate;
import com.vilyever.socketclient.helper.SocketPacketHelper;
import com.vilyever.socketclient.helper.SocketResponsePacket;
import com.vilyever.socketclient.util.CharsetUtil;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

import cn.csg.gjdl.robot.ImgDataBean;

public class CameraClientJava {

    private String ip;
    private int  addr_port   = 8880;
    private int  width       = 640;
    private int  height      = 480;
    private int  src         = 888 + 15 ; //双方确定传输帧数，（888）为校验值
    private int  interval    = 0;         //图片播放时间间隔
    private int  img_fps     = 15;        //每秒传输多少帧数

    private boolean  isInterrupt   = false;

    private SocketClient client;

    private OnListener listener;

    public interface OnListener{

        void onResponse(byte[] data);
    }


    public CameraClientJava(String ip, OnListener listener){
        this.ip = ip;
        this.listener = listener;
        initClient();
    }


    public void  start(){
        client.connect();

    }


    public void  stop(){
        client.disconnect();
    }


    private void initClient() {
        client = new SocketClient();
        client.getAddress().setRemoteIP(ip);
        client.getAddress().setRemotePort("8880");
        client.getAddress().setConnectionTimeout(15* 1000);
        client.setCharsetName(CharsetUtil.UTF_8);
//        client.getHeartBeatHelper().setSendHeartBeatEnabled(false);
        client.getSocketPacketHelper().setReadStrategy(SocketPacketHelper.ReadStrategy.AutoReadToTrailer);
       client.getSocketPacketHelper().setReceiveTrailerData("<end>".getBytes());

        client.registerSocketClientDelegate(new SocketClientDelegate() {
            @Override
            public void onConnected(SocketClient client) {
                Log.e("999999999", "onConnected");

            }

            @Override
            public void onDisconnected(SocketClient client) {
                Log.e("999999999", "onDisconnected");
            }

            @Override
            public void onResponse(SocketClient client, @NonNull SocketResponsePacket responsePacket) {
                Log.e("999999999", "onResponse");
                byte[] data = responsePacket.getData(); // 获取接收的byte数组，不为null
               // String message = responsePacket.getMessage(); // 获取按默认设置的编码转化的String，可能为null
                //Log.e("999999999", message);
                //Log.e("999999999", String.valueOf(data.length));
                //Log.e("999999999", Arrays.toString(data));
                if (listener != null && data != null)
                    listener.onResponse(data);

            }
        });


    }
}
