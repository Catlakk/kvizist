package com.example.kvizist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


/*
@Composable
fun ModeScreen(navController: NavController, sessionData: SessionData){
    NavHostTestForMode(navController)

    //sessionData test:
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
    {Text("${sessionData.selectedSubject}") }

}
*/
@Composable
fun ModeScreen(navController: NavController, sessionData: SessionData) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Routes.QUIZ) },
                containerColor = Color(0xFF0D3C78)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Next",
                    tint = Color.White
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(padding)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TopBar("Strukture Podataka")
            Spacer(modifier = Modifier.height(24.dp))
            FilterCard()
            val counter = 3
            for (index in 1..counter){
                Spacer(modifier = Modifier.height(24.dp))
                ContentList(index)
            }
        }
    }
}
@Composable
fun TopBar(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF0D3C78),
                        Color(0xFF5BBFA7)
                    )
                )
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )

            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun FilterCard() {

    Box(
        modifier = Modifier.wrapContentSize()
    ) {

        // Shadow card
        Card(
            modifier = Modifier
                .matchParentSize()
                .offset(x = 8.dp, y = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF002F5B)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {}

        // Main card
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {
            ButtonFlowRow()
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ButtonFlowRow() {
    FlowRow(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
    ) {
        val counter = 5
        for (index in 1..counter) {
            Button(onClick = {}) {
                Text("Button $index")
            }

        }
        Button(onClick = {}) {
            Text("Add")}
    }
}

@Composable
fun ContentList(index: Int) {
    var shadowActive by remember { mutableStateOf(false) }
    var checked by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.wrapContentSize()
    ) {
        // Shadow card
        Card(
            modifier = Modifier
                .matchParentSize()
                .offset(x = 8.dp, y = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF002F5B)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            if(shadowActive){
                repeat(10) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = { checked = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.White,
                            checkmarkColor = Color(0xFF2E9F8F)
                        )
                    )
                }
            }

        }
        Button( onClick = {shadowActive = !shadowActive},
            modifier = Modifier
                .width(350.dp)
                .padding(vertical = 6.dp)
                .background(
                    color = Color(0xFF2E9F8F),
                    shape = RoundedCornerShape(12.dp)
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color(0xFF002F5B)
            )

        ) {
            Text(
                text = "Broj $index",
                color = Color.White,
                modifier = Modifier.weight(1f),
                fontSize = 16.sp
            )
            Checkbox(
                checked = checked,
                onCheckedChange = { checked = it  },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.White,
                    checkmarkColor = Color(0xFF2E9F8F)
                )
            )
        }


    }

}





@Composable
fun NavHostTestForMode(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ){Text("ModeScreen")}

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(onClick = {
            navController.navigate(Routes.QUIZ)
        })
        {
            Text("Go to QuizScreen")
        }
    }

}