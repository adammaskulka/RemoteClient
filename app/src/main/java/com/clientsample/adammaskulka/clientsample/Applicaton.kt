package com.clientsample.adammaskulka.clientsample

import android.app.Application
import com.chibatching.kotpref.Kotpref

class Applicaton : Application() {


    override fun onCreate() {
        super.onCreate()
        Kotpref.init(this)
    }

}