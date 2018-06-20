package com.clientsample.adammaskulka.clientsample

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.util.Log.i
import android.view.*
import kotlinx.android.synthetic.main.activity_main.*


class NumpadActivity : AppCompatActivity() {

//    val numbers: IntArray = StringArrayOf(100, 200, 300, 400, 550)

    val strings = arrayOf("*01#", "*02#", "*03#", "*04#", "*05#", "*06#", "*07#", "*08#", "*09#", "*10#")


    companion object {

        public const val CODE_STRING: String = "CODE"

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        val metrics = this.resources.displayMetrics
        val width = metrics.widthPixels
        val height = metrics.heightPixels
        val freeSpace = height - width

//        i("H", height.toString())
//        i("W", width.toString())
//        i("freeSpace", freeSpace.toString())
//
//        setMargins(view, 0, freeSpace/2, 0, 0)

        var params = numpad.getLayoutParams()
        params.width = width
        params.height = width
        numpad.setLayoutParams(params)


        i("viewpaddingTop", view.paddingTop.toString())


        params = numpad.getLayoutParams()

        i("H", params.height.toString())
        i("W", params.width.toString())

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }


        numpad.setOnTextChangeListner { text, digits_remaining ->

            i("TEXT", text)

            if (text.length > 1 && text[text.length - 1].toString() == "*") {
                numpad.resetDigits()
                textView.text = "*"
            } else {
                textView.text = text;
            }

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

    private fun setMargins(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        if (view.getLayoutParams() is ViewGroup.MarginLayoutParams) {
            val p = view.getLayoutParams() as ViewGroup.MarginLayoutParams
            p.setMargins(left, top, right, bottom)
            view.requestLayout()
        }
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
