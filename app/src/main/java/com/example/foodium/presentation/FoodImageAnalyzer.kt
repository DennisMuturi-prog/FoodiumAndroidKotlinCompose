package com.example.foodium.presentation

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.foodium.domain.Classification
import com.example.foodium.domain.FoodClassifier

class FoodImageAnalyzer(
    private val classifier: FoodClassifier,
    private val onResults: (List<Classification>) -> Unit
): ImageAnalysis.Analyzer {

    private var frameSkipCounter = 0

    override fun analyze(image: ImageProxy) {
        if(frameSkipCounter % 60 == 0) {
            val rotationDegrees = image.imageInfo.rotationDegrees
            val bitmap = image
                .toBitmap()
                .centerCrop(192, 192)

            val results = classifier.classify(bitmap, rotationDegrees)
            onResults(results)
        }
        frameSkipCounter++

        image.close()
    }
}