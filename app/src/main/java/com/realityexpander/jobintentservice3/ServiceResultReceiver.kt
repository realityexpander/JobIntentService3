package com.realityexpander.jobintentservice3

import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver

class ServiceResultReceiver
/**
 * Create a new ResultReceive to receive results.  Your
 * [.onReceiveResult] method will be called from the thread running
 * <var>handler</var> if given, or from an arbitrary thread if null.
 *
 * @param handler the handler object
 */
    (handler: Handler?) : ResultReceiver(handler) {
    private var mReceiver: Receiver? = null
    fun setReceiver(receiver: Receiver?) {
        mReceiver = receiver
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
        if (mReceiver != null) {
            mReceiver!!.onReceiveResult(resultCode, resultData)
        }
    }

    interface Receiver {
        fun onReceiveResult(resultCode: Int, resultData: Bundle?)
    }
}