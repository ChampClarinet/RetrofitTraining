package com.example.retrofittraining

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.retrofittraining.Apis.NetworkConnectionManager
import com.example.retrofittraining.Apis.OnNetworkCallbackListener
import com.example.retrofittraining.Models.User
import okhttp3.ResponseBody
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private var TAG = this::class.java.simpleName

    private lateinit var layoutForm: ConstraintLayout
    private lateinit var edUsername: EditText
    private lateinit var btSend: Button
    private lateinit var loadingView: LinearLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var layoutResult: LinearLayout
    private lateinit var tvResults: TextView

    private val onBtnSendOnClickListener = View.OnClickListener {
        hideViews(layoutForm, layoutResult)
        showViews(loadingView)
        NetworkConnectionManager().callServer(networkCallbackListener, edUsername.text.toString())
    }
    private val networkCallbackListener = object : OnNetworkCallbackListener<User> {
        override fun onResponse(data: User, retrofit: Retrofit) {
            //200
            val resText = "Github username :${data.name}" +
                    "\nBlog :${data.blog}" +
                    "\nCompany Name :${data.company}"
            tvResults.text = resText
            showViews(layoutResult)
            hideViews(layoutForm, loadingView)
        }

        override fun onBodyError(responseBodyError: ResponseBody) {
            //404 (have body error)
            Log.w(TAG, responseBodyError.string())
        }

        override fun onBodyErrorNull() {
            //404 (nobody nobody but you clap! clap!)
            Log.w(TAG, "Body error")
        }

        override fun onFailure(t: Throwable) {
            //fail
            Log.w(TAG, "Fetch Failed", t)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun showViews(vararg views: View) {
        for (v in views) v.visibility = View.VISIBLE
    }

    private fun hideViews(vararg views: View) {
        for (v in views) v.visibility = View.GONE
    }

    private fun init() {
        //Form
        layoutForm = findViewById(R.id.viewForm)
        edUsername = findViewById(R.id.githubUsernameEditText)
        btSend = findViewById(R.id.sendButton)

        //Progress
        loadingView = findViewById(R.id.viewLoading)
        progressBar = findViewById(R.id.progressBar)

        //Results
        layoutResult = findViewById(R.id.viewResult)
        tvResults = findViewById(R.id.tvResult)

        //Listeners
        btSend.setOnClickListener(onBtnSendOnClickListener)

    }

}
