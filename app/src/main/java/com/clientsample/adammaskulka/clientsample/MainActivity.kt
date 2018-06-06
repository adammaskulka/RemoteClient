package com.clientsample.adammaskulka.clientsample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.clientsample.adammaskulka.clientsample.R.id.numpad
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

//    val numbers: IntArray = StringArrayOf(100, 200, 300, 400, 550)

    val strings = arrayOf("*100", "*200", "*300")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        numpad.setOnTextChangeListner { text, digits_remaining ->

            textView.text = text;

            if(text.isNotEmpty() && strings.contains(text)) {
                val intent = Intent(this@MainActivity,ControllerActivity::class.java)
                startActivity(intent)
            }

        }

    }
}
