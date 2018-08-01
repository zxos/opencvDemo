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
import cn.csg.gjdl.robot.ImgDataBean
import kotlinx.android.synthetic.main.activity_main.*
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import java.util.*

class MainActivity : AppCompatActivity(), CameraClientJava.OnListener {


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

    }

    override fun onDestroy() {
        super.onDestroy()
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
                    client = CameraClientJava(ip , this)
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

        client = CameraClientJava(ip, this)
    }


    private fun converGray(){
        val src = Mat()
        val temp= Mat()
        val dst = Mat()
        Utils.bitmapToMat(selectbp, src)
        Imgproc.cvtColor(src, temp, Imgproc.COLOR_BGRA2BGR)
        Imgproc.cvtColor(temp,dst, Imgproc.COLOR_BGR2GRAY)
        Utils.matToBitmap(dst, selectbp)
        myImageView.setImageBitmap(selectbp)


    }





    override fun onResponse(data: ByteArray) {

        val imgdata = ImgDataBean.ImgData.parseFrom(data) ?: return

        when(imgdata.msgType){
            1   ->{
                showImg(imgdata)
            }
        }

    }

//    Imgproc.matchTemplate(mFind, Input, mResult, Imgproc.TM_SQDIFF) ;
//    bmp3= Bitmap.createBitmap(mResult.cols(),  mResult.rows(),Bitmap.Config.ARGB_8888);
//    Core.normalize(mResult, mResult8u, 0, 255, Core.NORM_MINMAX, CvType.CV_8U);
//    Utils.matToBitmap(mResult8u, bmp3);
//    iv2.setImageBitmap(bmp3);

    val bitmap = Bitmap.createBitmap(480, 640, Bitmap.Config.ARGB_8888)
    var a = true
    private fun showImg(imgData: ImgDataBean.ImgData){


        val src = Mat(640, 480, CvType.CV_8U)
        src.put(0, 0, imgData.imgdata.toByteArray())
        Utils.matToBitmap(src, bitmap)
        camera_view.setBitmap(bitmap)



    }



}


