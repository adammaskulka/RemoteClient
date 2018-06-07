package com.clientsample.adammaskulka.clientsample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.util.Log.e
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.clientsample.adammaskulka.clientsample.NumpadActivity.Companion.CODE_STRING
import com.clientsample.adammaskulka.clientsample.rest.RestService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_controller.*
import java.util.*
import java.util.concurrent.TimeUnit

class ControllerActivity : AppCompatActivity() {

    var level:Int = 1;

    var code: String = "*01"

    var toolbar: Toolbar? = null
    var actionBar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)

        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

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
            addToLevel()
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
            addToLevel()
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
            addToLevel()
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
            addToLevel()
        })

//        val actionBar = supportActionBar
//        if(actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true)
//        }
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
                val intent = Intent(this, ScannerActivity::class.java)
                //startActivity(intent)
                startActivityForResult(intent, 1)
            }
            R.id.keyboardMenu -> {
                val intent = Intent(this, NumpadActivity::class.java)
                //startActivity(intent)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }

}
