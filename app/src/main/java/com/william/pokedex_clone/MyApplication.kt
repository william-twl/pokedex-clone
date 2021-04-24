package com.william.pokedex_clone

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig
import com.william.pokedex_clone.model.GeneralObject

class MyApplication : Application() {
    companion object {
        lateinit var instance: MyApplication
    }

    var favouriteList: ArrayList<GeneralObject?>? = ArrayList<GeneralObject?>()

    override fun onCreate() {
        super.onCreate()
        instance = this
        val config = ImagePipelineConfig.newBuilder(this)
            .setProgressiveJpegConfig(SimpleProgressiveJpegConfig())
            .setResizeAndRotateEnabledForNetwork(true)
            .setDownsampleEnabled(true)
            .build()
        Fresco.initialize(this, config)
    }
}