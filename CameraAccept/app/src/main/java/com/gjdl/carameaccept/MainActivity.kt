package com.gjdl.carameaccept

import android.content.Context
import android.content.ServiceConnection
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc

class MainActivity : AppCompatActivity() {

    var ip = ""
    var client: CameraClientJava ?= null

    val max_size = 1024
    val PICK_IMAGE_REQIEST = 1
    lateinit var selectbp : Bitmap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val load = OpenCVLoader.initDebug()
        if (load){
            Log.e("999999999999", "opencv be load")
        }

        selectbp = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)

        initlayout()

        startServer()
    }

    private fun initlayout(){

        btn_commit.setOnClickListener {

            val ipStr = et_ip.text.toString().trim()
            if (ip.equals(ipStr)){
                showToast("IP未修改")
            }else{
                ip = ipStr
                commit()
                showToast("提交")
            }
        }

        btn_connect.setOnClickListener {
            if (ip != "") {
                if (client == null)
                    client = CameraClientJava(ip)
                client?.start()
            }
        }

        btn_disconnect.setOnClickListener {
            if (ip != "")
                client?.stop()
        }

        btn_opencv_test.setOnClickListener {
            //converGray()
            CameraActivity.start(this)
        }

        ip = getSp().getString("ip", "")
        if (ip != ""){
            et_ip.setText(ip)
        }

    }

    private fun commit(){

        getSp().edit().putString("ip", ip).apply()

        client = CameraClientJava(ip)
    }


    private fun converGray(){
        val src = Mat()
        val temp= Mat()
        val dst = Mat()
        val t   = Mat(144,144, CvType.CV_8UC4,Scalar(1.0,1.0, 1.0))
 //       Utils.bitmapToMat(selectbp, t)
        Utils.bitmapToMat(selectbp, src)
        Imgproc.cvtColor(src, temp, Imgproc.COLOR_BGRA2BGR)
        Imgproc.cvtColor(temp,dst, Imgproc.COLOR_BGR2GRAY)
        Utils.matToBitmap(dst, selectbp)

        //Utils.matToBitmap(dst, selectbp)
        myImageView.setImageBitmap(selectbp)


        //Log.e("9999999999999", t.toString())
    }


    private fun startServer(){


    }
}


