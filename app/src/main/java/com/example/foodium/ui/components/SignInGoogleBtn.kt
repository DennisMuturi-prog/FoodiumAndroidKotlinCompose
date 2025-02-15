package com.example.foodium.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.foodium.R

@Composable
fun GoogleButton(modifier: Modifier = Modifier) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://foodiumnodejs.gentledune-9460edf8.southafricanorth.azurecontainerapps.io/auth/google")
    )
    val ctx = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text="Or sign in with google", fontSize = 20.sp)
        IconButton(onClick = {
            ctx.startActivity(intent)
        }) {
            Image(painter =  painterResource(R.drawable.icons8_google_48),
                contentDescription = "google sign in")
        }

    }

}