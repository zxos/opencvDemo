package com.gjdl.carameaccept

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_camera.*

class CameraActivity : AppCompatActivity() {

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

        cv_pic.post {
            sendBitmap()
        }

    }



    private fun sendBitmap() {

       for (a: Int in 1..3){
           for (i in PictureInterface.srcId){
               cv_pic.setBitmap(i)
           }
       }




    }


}
