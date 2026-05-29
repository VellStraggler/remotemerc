package com.example.remotemerc.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.remotemerc.R
import com.example.remotemerc.data.FakePerson

@Composable
fun MissionProfilePage(target: FakePerson) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Image(
                    painter = painterResource(id = R.drawable.lego_head),
                    contentDescription = "Profile picture",
                    modifier = Modifier.size(110.dp)
                        .padding(top=24.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    ProfileInfoRow("Name:", target.fullName)
                    ProfileInfoRow("Goal:", "Eliminate")
                    ProfileInfoRow("Location:", target.location)
                    ProfileInfoRow("Reward:", "\$${target.bounty}")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Description",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "This person must be eliminated at all costs. Find and eliminate ASAP.",
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Reference Images",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ReferenceImage(R.drawable.lego_house, "House picture")
            ReferenceImage(R.drawable.lego_female, "Wife picture")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "Target Eliminated",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
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
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(90.dp)
        )

        Text(
            text = value,
            fontSize = 18.sp
        )
    }
}

@Composable
fun ReferenceImage(
    imageId: Int,
    description: String
) {
    Card(
        border = BorderStroke(2.dp, Color.Black),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = description,
            modifier = Modifier
                .size(100.dp)
                .padding(8.dp),
        )
    }
}