package com.gjdl.carameaccept

import android.util.Log
import cn.csg.gjdl.robot.ImgDataBean
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.MatOfByte
import org.opencv.imgcodecs.Imgcodecs
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.InputStreamReader
import java.net.Proxy
import java.net.Socket
import java.net.SocketAddress
import java.nio.ByteBuffer
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.math.log

class CameraClient(val ip:String){

    val TAG         = "CameraClient"

    val addr_port   = 8880
    val width       = 640
    val height      = 480
    val src         = 888 + 15  //双方确定传输帧数，（888）为校验值
    val interval    = 0         //图片播放时间间隔
    val img_fps     = 15        //每秒传输多少帧数

    var isInterrupt   = false




    //b'\x87\x03\x00\x00\x80\x02\x00\x00\xe0\x01\x00\x00'
    val task = Thread {
        run {

                val client = Socket(ip, 8880)

                Log.e("CameraClient", " " + Thread.currentThread().name)
                Log.e("CameraClient", ip)



                if (client.isConnected){
                    Log.e("CameraClient", "-----isConnected-------")

                    val ios = client.getInputStream()
                    val isr = InputStreamReader(ios)
                    val br  = BufferedReader(isr)


                    while (!isInterrupt){

                        var str = null


                        val buffer = br.readLines()
                        //Log.e("CameraClient", buffer)
                        Log.e("CameraClient", buffer.toString())
                        //val img = ImgDataBean.ImgData.parseFrom(buffer.toByteArray(Charsets.UTF_8))
                        //val img = ImgDataBean.ImgData.newBuilder().mergeFrom(buffer.toByteArray(Charsets.UTF_8)).build()
                        //Log.e("CameraClient", img.msgType.toString())
                        //Log.e("CameraClient", img.imgdata.toString())




                    }
                }
        }
    }




    fun start(){
        isInterrupt = false
        task.start()

    }


    fun stop(){
        isInterrupt = true
        task.interrupt()
    }





}


