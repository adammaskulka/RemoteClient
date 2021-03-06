package com.clientsample.adammaskulka.clientsample

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Bundle
import android.support.v4.view.MotionEventCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.util.Log.e
import android.util.Log.i
import android.view.*
import com.chibatching.kotpref.KotprefModel
import com.clientsample.adammaskulka.clientsample.NumpadActivity.Companion.CODE_STRING
import com.clientsample.adammaskulka.clientsample.rest.RestService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_controller.*
import java.net.NetworkInterface
import java.util.*
import java.util.concurrent.TimeUnit

object RestInfo : KotprefModel() {

    var URL by stringPref()

}

class ControllerActivity : AppCompatActivity() {

    var level:Int = 1;

    var code: String = "*01#"

    var toolbar: Toolbar? = null
    var actionBar: ActionBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)


        val ip: String? = getIPAddress(true)
        i("IP", "IP: " + ip)


        val wifii = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val d = wifii.getDhcpInfo();
        val gateway = intToIp(d.gateway)

        if (RestInfo.URL.isEmpty()) {
            i("gateway", "gateway: " + gateway)
            RestService.changeBaseUrl("http://" + gateway + "/cgi-bin/")
            RestInfo.URL = "http://" + gateway + "/cgi-bin/";
        } else {
            RestService.changeBaseUrl(RestInfo.URL)
        }

        actionBar = supportActionBar

        val bundle = intent.extras
        if (bundle != null) {
            code = intent.getStringExtra(CODE_STRING)
        }

        actionBar?.title = code


        var restService = RestService.getRestApi()

        urlEditText.setText(RestService.BASE_URL)
        levelEditText.setText(level.toString())

        changeURLButton.setOnClickListener({
            RestService.changeBaseUrl(urlEditText.text.toString())
            RestInfo.URL = urlEditText.text.toString()
            restService = RestService.getRestApi()
        })


        button1.setOnClickListener({
            restService.search(1, levelEditText.text.toString().toInt(), code)
                    .subscribeOn(Schedulers.io())
                    .timeout(5, TimeUnit.SECONDS)
                    .onErrorResumeNext(Observable.empty())
                    .retry(2)
                    .observeOn(AndroidSchedulers.mainThread()).subscribe({

                    }, {
                        error ->
                        e("Error", error.message)
                        error(error)
                    })
        })
        button2.setOnClickListener({
            restService.search(2, levelEditText.text.toString().toInt(), code)
                    .subscribeOn(Schedulers.io())
                    .timeout(5, TimeUnit.SECONDS)
                    .onErrorResumeNext(Observable.empty())
                    .retry(2)
                    .observeOn(AndroidSchedulers.mainThread()).subscribe({

                    }, {
                        error ->
                        error(error)
                    })
        })
        button3.setOnClickListener({
            restService.search(3, levelEditText.text.toString().toInt(), code)
                    .subscribeOn(Schedulers.io())
                    .timeout(5, TimeUnit.SECONDS)
                    .onErrorResumeNext(Observable.empty())
                    .retry(2)
                    .observeOn(AndroidSchedulers.mainThread()).subscribe({

                    }, {
                        error ->
                        error(error)
                    })
        })
        button4.setOnClickListener({
            restService.search(4, levelEditText.text.toString().toInt(), code)
                    .subscribeOn(Schedulers.io())
                    .timeout(5, TimeUnit.SECONDS)
                    .onErrorResumeNext(Observable.empty())
                    .retry(2)
                    .observeOn(AndroidSchedulers.mainThread()).subscribe({

                    }, {
                        error ->
                        error(error)
                    })
        })
        button5.setOnClickListener({
            restService.search(5, levelEditText.text.toString().toInt(), code)
                    .subscribeOn(Schedulers.io())
                    .timeout(5, TimeUnit.SECONDS)
                    .onErrorResumeNext(Observable.empty())
                    .retry(2)
                    .observeOn(AndroidSchedulers.mainThread()).subscribe({

                    }, { error ->
                        error(error)
                    })
        })
        button6.setOnClickListener({
            restService.search(6, levelEditText.text.toString().toInt(), code)
                    .subscribeOn(Schedulers.io())
                    .timeout(5, TimeUnit.SECONDS)
                    .onErrorResumeNext(Observable.empty())
                    .retry(2)
                    .observeOn(AndroidSchedulers.mainThread()).subscribe({

                    }, { error ->
                        error(error)
                    })
        })


//        val actionBar = supportActionBar
//        if(actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true)
//        }
    }

    fun intToIp(i: Int): String {

        return (i and 0xFF).toString() + "." +
                (i shr 8 and 0xFF) + "." +
                (i shr 16 and 0xFF) + "." +
                (i shr 24 and 0xFF)
    }

    fun ClosedRange<Int>.random() =
            Random().nextInt(endInclusive - start) + start

    fun addToLevel() {
        if (level < 240) {
            level += (1..15).random()
        } else {
            level = 1
        }
        levelEditText.setText(level.toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }

        code = data.getStringExtra("BRCode")

        actionBar?.title = code

        Log.i("CODE", code)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.photoMenu -> {
//                val intent = Intent(this, ScannerActivity::class.java)
//                //startActivity(intent)
//                startActivityForResult(intent, 1)

                val intent = Intent(this, NumpadActivity::class.java)
                //startActivity(intent)
                startActivity(intent)
            }
            R.id.keyboardMenu -> {
                val intent = Intent(this, NumpadActivity::class.java)
                //startActivity(intent)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    fun getIPAddress(useIPv4: Boolean): String {
        try {
            val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (intf in interfaces) {
                val addrs = Collections.list(intf.inetAddresses)
                for (addr in addrs) {
                    if (!addr.isLoopbackAddress) {
                        val sAddr = addr.hostAddress
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        val isIPv4 = sAddr.indexOf(':') < 0

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr
                        } else {
                            if (!isIPv4) {
                                val delim = sAddr.indexOf('%') // drop ip6 zone suffix
                                return if (delim < 0) sAddr.toUpperCase() else sAddr.substring(0, delim).toUpperCase()
                            }
                        }
                    }
                }
            }
        } catch (ex: Exception) {
        }
        // for now eat exceptions
        return ""
    }


    override fun onGenericMotionEvent(event: MotionEvent): Boolean {

        val action = MotionEventCompat.getActionMasked(event)

        val DEBUG_TAG = "SWIPE"
        i(DEBUG_TAG, DEBUG_TAG)

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(DEBUG_TAG, "Action was DOWN")
                val intent = Intent(this, NumpadActivity::class.java)
                //startActivity(intent)
                startActivity(intent)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d(DEBUG_TAG, "Action was MOVE")
                return true
            }
            MotionEvent.ACTION_UP -> {
                Log.d(DEBUG_TAG, "Action was UP")
                return true
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.d(DEBUG_TAG, "Action was CANCEL")
                return true
            }
            MotionEvent.ACTION_OUTSIDE -> {
                Log.d(DEBUG_TAG, "Movement occurred outside bounds " + "of current screen element")
                return true
            }
            else -> return super.onTouchEvent(event)
        }
    }

}
