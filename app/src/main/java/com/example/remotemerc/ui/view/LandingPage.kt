package com.example.remotemerc.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.remotemerc.data.FakePerson
import com.example.remotemerc.data.PeopleViewModel

@Composable
fun LandingPage(peopleViewModel: PeopleViewModel, onClickMission: () -> Unit) {
    Column() {
        LandingPageTopBar()
        LandingPageCenterSection(peopleViewModel, onClickMission)
    }
}

@Composable
fun LandingPageTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color=Color.LightGray)
            .padding(start = 10.dp, end = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile picture",
                modifier = Modifier.size(56.dp),
                tint = Color.Black,
                )
        }
        Text("Score: 900")
        Text("Money: \$190000")
    }
}

@Composable
fun LandingPageCenterSection(peopleViewModel: PeopleViewModel, onClickMission: () -> Unit) {
    val placeholderItems = List(3) { "placeholder text" }
    val randomActiveTargets = peopleViewModel.fakePeople.take(5)
    val randomAvailableTargets = peopleViewModel.fakePeople.drop(5).take(5)
    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        LandingPageListBox("Active Missions", randomActiveTargets, onClickMission)
        LandingPageListBox("Available Missions", randomAvailableTargets, onClickMission)
    }
}

@Composable
fun LandingPageListBox(
    title: String,
    rowItems: List<FakePerson>,
    onRowClick: () -> Unit
) {
    Text(text=title,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(170.dp)
    ) {
        Column {
            LazyColumn (
                modifier = Modifier.padding(all = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(rowItems) { item ->
//                    Text(text=item.toString(),
////                        fontSize = 20.sp,
//                        modifier = Modifier.clickable {
//                            onRowClick()
//                        })
                    PersonBountyCard(item, onRowClick)
                }
            }
        }
    }
}

@Composable
fun PersonBountyCard(
    person: FakePerson,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp),
        border = BorderStroke(2.dp, Color.Black),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = person.fullName,
//                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Bounty: $${person.bounty}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = person.location,
                    fontSize = 14.sp
                )
            }
        }
    }
}