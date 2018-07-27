package com.gjdl.carameaccept;

import android.support.annotation.NonNull;
import android.util.Log;

import com.vilyever.socketclient.SocketClient;
import com.vilyever.socketclient.helper.SocketClientDelegate;
import com.vilyever.socketclient.helper.SocketClientReceivingDelegate;
import com.vilyever.socketclient.helper.SocketPacketHelper;
import com.vilyever.socketclient.helper.SocketResponsePacket;
import com.vilyever.socketclient.util.CharsetUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.ByteBuffer;

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

    public CameraClientJava(String ip){
        this.ip = ip;


        initClient();
    }




    private Thread task = new Thread(){
        @Override
        public void run() {
            try {
                Socket client = new Socket(ip, 8880);

                Log.e("CameraClient", " " + Thread.currentThread().getName());
                Log.e("CameraClient", ip);

                if (client.isConnected()){
                    Log.e("CameraClient", "-----isConnected-------");

                    InputStream ios = client.getInputStream();
                    InputStreamReader isr = new InputStreamReader(ios);
                    BufferedReader br  = new BufferedReader(isr);


                    while (!isInterrupt){

                        String str = null;

                        while ((str = br.readLine()) != null){
                            Log.e("CameraClient", str);
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };


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
//        client.getSocketPacketHelper().setReadStrategy(SocketPacketHelper.ReadStrategy.Manually);


        client.registerSocketClientDelegate(new SocketClientDelegate() {
            @Override
            public void onConnected(SocketClient client) {
                Log.e("999999999", "onConnected");
                if (client.getSocketPacketHelper().getReadStrategy() == SocketPacketHelper.ReadStrategy.Manually) {
                    client.readDataToLength(10 * 1024);
                }

            }

            @Override
            public void onDisconnected(SocketClient client) {
                Log.e("999999999", "onDisconnected");
            }

            @Override
            public void onResponse(SocketClient client, @NonNull SocketResponsePacket responsePacket) {
                Log.e("999999999", "onResponse");
//                byte[] data = responsePacket.getData(); // 获取接收的byte数组，不为null
                String message = responsePacket.getMessage(); // 获取按默认设置的编码转化的String，可能为null
                Log.e("999999999", message);
            }
        });


    }
}
