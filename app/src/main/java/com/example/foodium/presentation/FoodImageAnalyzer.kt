package com.example.foodium.presentation

import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.foodium.domain.Classification
import com.example.foodium.domain.FoodClassifier



class FoodImageAnalyzer(
    private val classifier: FoodClassifier,
    private val onResults: (List<Classification>) -> Unit
): ImageAnalysis.Analyzer {

    private var frameSkipCounter = 0

//    @OptIn(ExperimentalGetImage::class)
    override fun analyze(image: ImageProxy) {
//        val convertedimage=image.image

        if(frameSkipCounter % 60 == 0) {
            val rotationDegrees = image.imageInfo.rotationDegrees
            val bitmap = image.toBitmap()
//            Log.d("shape of pic",bitmap.colorSp)
            val results=classifier.classify(bitmap,rotationDegrees)
            onResults(results)
//            val bitmap= convertedimage?.let { toBitmap(it) }
//            val results = bitmap?.let { classifier.classify(it, rotationDegrees) }
//            if (results != null) {
//                onResults(results)
//            }
        }
        frameSkipCounter++

        image.close()
    }
}