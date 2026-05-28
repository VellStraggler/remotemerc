package com.example.remotemerc.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.remotemerc.R
import com.example.remotemerc.data.FakePerson

@Composable
fun MissionProfilePage(target: FakePerson) {
    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.lego_head),
                contentDescription = "Profile picture",
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text("Name: ${target.fullName}")
                Text("Goal: Eliminate")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text("Description:")
        Text("This is where the target description will go.")

        Spacer(modifier = Modifier.height(24.dp))

        Text("Reference Images")

        Spacer(modifier = Modifier.height(8.dp))
    }
}