package com.realityexpander.jobintentservice3

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log
import androidx.core.app.JobIntentService

class JobService : JobIntentService() {
    /**
     * Result receiver object to send results
     */
    private var mResultReceiver: ResultReceiver? = null

    @SuppressLint("DefaultLocale")
    override fun onHandleWork(intent: Intent) {
        Log.d(
            TAG,
            "onHandleWork() called with: intent = [$intent]"
        )
        if (intent.action != null) {
            when (intent.action) {
                ACTION_DOWNLOAD -> {
                    mResultReceiver = intent.getParcelableExtra(RECEIVER)

                    var i = 0
                    while (i < 10) {
                        try {
                            Thread.sleep(1000) // simulate a long running task

                            val bundle = Bundle()
                            bundle.putString(
                                "data",
                                String.format("Showing data From JobIntent Service %d", i)
                            )

                            mResultReceiver!!.send(SHOW_RESULT, bundle)

                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }

                        i++
                    }
                }
            }
        }
    }

    companion object {
        private val TAG = JobService::class.java.simpleName
        const val RECEIVER = "receiver"
        const val SHOW_RESULT = 123

        /**
         * Unique job ID for this service.
         */
        const val DOWNLOAD_JOB_ID = 1000

        /**
         * Actions download
         */
        private const val ACTION_DOWNLOAD = "action.DOWNLOAD_DATA"

        /**
         * Convenience method for enqueuing work in to this service.
         */
        fun enqueueWork(context: Context?, workerResultReceiver: ServiceResultReceiver?) {
            val intent = Intent(context, JobService::class.java)
            intent.putExtra(RECEIVER, workerResultReceiver)
            intent.action = ACTION_DOWNLOAD

            enqueueWork(
                context!!,
                JobService::class.java, DOWNLOAD_JOB_ID, intent
            )
        }
    }
}