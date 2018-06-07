package com.clientsample.adammaskulka.clientsample

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*


class NumpadActivity : AppCompatActivity() {

//    val numbers: IntArray = StringArrayOf(100, 200, 300, 400, 550)

    val strings = arrayOf("*01", "*02", "*03", "*04", "*05", "*06", "*07")


    companion object {

        public const val CODE_STRING: String = "CODE"

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }


        numpad.setOnTextChangeListner { text, digits_remaining ->

            textView.text = text;

            if (text.isNotEmpty() && strings.contains(text)) {
                val intent = Intent(this@NumpadActivity, ControllerActivity::class.java)
                intent.putExtra(CODE_STRING, text)
                startActivity(intent)
            }

        }

        val timer = MyCountDown(10000, 1000)

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

    private inner class MyCountDown(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {
        init {
            start()
        }

        override fun onFinish() {
            finish()
        }

        override fun onTick(duration: Long) {
        }
    }

}
