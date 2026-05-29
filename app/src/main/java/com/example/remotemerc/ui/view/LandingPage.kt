package com.example.remotemerc.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.Button
import com.example.remotemerc.ui.viewmodel.ProfileViewModel

@Composable
fun LandingPage(
    profileViewModel: ProfileViewModel,
    peopleViewModel: PeopleViewModel,
    onClickMission: (FakePerson) -> Unit
) {
    Column {
        LandingPageTopBar(profileViewModel)
        LandingPageCenterSection(peopleViewModel, onClickMission)
    }
}

@Composable
fun LandingPageTopBar(profileViewModel: ProfileViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .background(color = Color.LightGray)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
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
                modifier = Modifier.size(42.dp),
                tint = Color.Black
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = profileViewModel.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Drone Pilot",
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "Money: $${profileViewModel.cash}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Score: ${profileViewModel.score}",
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun LandingPageCenterSection(
    peopleViewModel: PeopleViewModel,
    onClickMission: (FakePerson) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        LandingPageListBox(
            title = "Active Missions",
            rowItems = peopleViewModel.getMyPeople(),
            onRowClick = { person ->
                peopleViewModel.selectPerson(person.id)
                onClickMission(person)
            }
        )
        AvailableMissionsListBox(
            title = "Available Missions",
            rowItems = peopleViewModel.getAvailablePeople(),
            onAccept = { person ->
                peopleViewModel.acceptPerson(person)
            }
        )
    }
}

@Composable
fun LandingPageListBox(
    title: String,
    rowItems: List<FakePerson>,
    onRowClick: (FakePerson) -> Unit
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
                    PersonBountyCard(
                        person = item,
                        onClick = {
                            onRowClick(item)
                        }
                    )
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

@Composable
fun AvailableMissionsListBox(
    title: String,
    rowItems: List<FakePerson>,
    onAccept: (FakePerson) -> Unit
) {
    var selectedPerson by remember {
        mutableStateOf<FakePerson?>(null)
    }

    Text(
        text = title,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(170.dp)
    ) {
        LazyColumn(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(rowItems) { person ->
                Column {
                    PersonBountyCard(
                        person = person,
                        onClick = {
                            selectedPerson = person
                        }
                    )

                    if (selectedPerson?.id == person.id) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = {
                                    selectedPerson = null
                                }
                            ) {
                                Text("Reject")
                            }

                            Button(
                                onClick = {
                                    onAccept(person)
                                    selectedPerson = null
                                }
                            ) {
                                Text("Accept")
                            }
                        }
                    }
                }
            }
        }
    }
}