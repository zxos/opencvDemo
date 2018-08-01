package com.gjdl.carameaccept

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_camera.*
import org.opencv.android.BaseLoaderCallback
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import org.opencv.core.Mat

class CameraActivity : AppCompatActivity(){

    lateinit var helper: JavaCameraViewHelper

    var a = 1

    companion object {

        fun start(context: Context){
            val intent = Intent(context, CameraActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

//        cv_pic.post {
//            sendBitmap()
//        }
        helper = JavaCameraViewHelper(this, javaCameraView)


    }


    override fun onPause() {
        super.onPause()
        helper.onPause()
    }

    override fun onResume() {
        super.onResume()
        helper.onResume()
    }


//    private fun sendBitmap() {
//       for (a: Int in 1..100){
//           for (i in PictureInterface.srcId){
//               cv_pic.setBitmap(i)
//           }
//       }
//    }


}
