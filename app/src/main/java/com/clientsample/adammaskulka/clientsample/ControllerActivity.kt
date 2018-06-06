package com.clientsample.adammaskulka.clientsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.util.Log
import android.util.Log.e
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.clientsample.adammaskulka.clientsample.rest.RestService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.operators.flowable.FlowableReplay.observeOn
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_controller.*
import java.util.concurrent.TimeUnit

class ControllerActivity : AppCompatActivity() {

    var level:Int = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_controller)

        var restService = RestService.getRestApi()

        urlEditText.setText(RestService.BASE_URL)
        levelEditText.setText(level.toString())

        changeURLButton.setOnClickListener({
            RestService.changeBaseUrl(urlEditText.text.toString())
            restService = RestService.getRestApi()
        })


        button1.setOnClickListener({
            restService.search(1,levelEditText.text.toString().toInt())
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
            restService.search(2,levelEditText.text.toString().toInt())
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
            restService.search(3,levelEditText.text.toString().toInt())
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
            restService.search(4,levelEditText.text.toString().toInt())
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

        val actionBar = supportActionBar
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

}
