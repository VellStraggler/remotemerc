package com.example.remotemerc.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.remotemerc.R
import com.example.remotemerc.data.FakePerson
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Arrangement

@Composable
fun MissionProfilePage(target: FakePerson) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(all = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.lego_head),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(128.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column (modifier = Modifier
                .padding(top = 16.dp)
            ) {
                ProfileInfoRow("Name:", target.fullName)
                ProfileInfoRow("Goal:", "Eliminate")
                ProfileInfoRow("Location:", target.location)
                ProfileInfoRow("Reward:", "\$${target.bounty}")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(text="Description:", fontSize = 18.sp,)
        Text(text="This person must be eliminated at all costs. Find and eliminate ASAP.",
            fontSize = 18.sp,)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text="Reference Images:", fontSize = 18.sp)

        Row() {
            Image(
                painter = painterResource(id = R.drawable.lego_house),
                contentDescription = "House picture",
                modifier = Modifier
                    .size(80.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = R.drawable.lego_female),
                contentDescription = "Wife picture",
                modifier = Modifier
                    .size(80.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Target Eliminated")
        }

    }
}

@Composable
fun ProfileInfoRow(
    label: String,
    value: String
) {
    Row {
        Text(
            text = label,
            fontSize = 18.sp,
            modifier = Modifier.width(90.dp)
        )

        Text(text = value, fontSize = 18.sp,)
    }
}