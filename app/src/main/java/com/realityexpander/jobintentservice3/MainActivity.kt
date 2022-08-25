package com.realityexpander.jobintentservice3

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.realityexpander.jobintentservice3.JobService.Companion.SHOW_RESULT

class MainActivity : AppCompatActivity(), ServiceResultReceiver.Receiver {

    private var mServiceResultReceiver: ServiceResultReceiver? = null
    private var mTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mServiceResultReceiver = ServiceResultReceiver(Handler(Looper.getMainLooper()))
        mServiceResultReceiver!!.setReceiver(this)
        mTextView = findViewById<TextView>(R.id.textView)

        downloadDataInBackground(this@MainActivity, mServiceResultReceiver!!)
    }

    private fun downloadDataInBackground(
        mainActivity: MainActivity,
        mResultReceiver: ServiceResultReceiver
    ) {
        JobService.enqueueWork(mainActivity, mResultReceiver)
    }

    fun showData(data: String?) {
        mTextView!!.text = String.format("%s\n%s", mTextView!!.text, data)
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        when (resultCode) {
            SHOW_RESULT -> if (resultData != null) {
                showData(resultData.getString("data"))
            }
        }
    }
}

