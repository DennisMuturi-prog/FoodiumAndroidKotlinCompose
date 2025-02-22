package com.example.foodium.domain

import android.graphics.Bitmap

interface FoodClassifier {
    fun classify(bitmap: Bitmap, rotation: Int): List<Classification>
}