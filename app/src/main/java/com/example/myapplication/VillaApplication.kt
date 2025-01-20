package com.example.myapplication

import android.app.Application
import com.example.myapplication.dependeciesinjection.AppContainer
import com.example.myapplication.dependeciesinjection.Container

class VillaApplication: Application(){
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = Container()
    }
}