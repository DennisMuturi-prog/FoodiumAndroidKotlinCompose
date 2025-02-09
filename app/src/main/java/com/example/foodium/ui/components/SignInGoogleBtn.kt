package com.example.foodium.ui.components

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun GoogleButton(modifier: Modifier = Modifier) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://foodiumnodejs.gentledune-9460edf8.southafricanorth.azurecontainerapps.io/auth/google")
    )
    val ctx = LocalContext.current
    Button(onClick = {
        Log.d("example","example")
        ctx.startActivity(intent)}) {
        Text(text="sign in with google",modifier= Modifier.padding(10.dp))
    }

}