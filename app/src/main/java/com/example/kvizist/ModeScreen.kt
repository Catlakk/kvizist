
package com.example.kvizist


import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
    //var selectedIndex by remember { mutableStateOf(1) }
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
    val counter = 5
    var selectedIndex by remember { mutableStateOf(1) } // first selected

    FlowRow(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (index in 1..counter) {
            Button(
                onClick = { selectedIndex = index },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedIndex == index)
                        Color(0xFF1E88E5) // selected (blue)
                    else
                        Color(0xFFE0E0E0) // unselected (gray)
                )
            ) {
                Text(
                    text = "Button $index",
                    color = if (selectedIndex == index)
                        Color.White
                    else
                        Color.Black
                )
            }
        }

        // Add button (not part of selection)
        Button(
            onClick = { /* add action */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5BBFA7)
            )
        ) {
            Text("Add", color = Color.White)
        }
    }
}



@Composable
fun ContentList(index: Int) {
    var expanded by remember { mutableStateOf(false) }
    val lessons = (8..13).map { "Lekcija $it" }

    var checked by remember {
        mutableStateOf<Set<Int>>(emptySet())
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        // Shadow
        Card(
            modifier = Modifier
                .matchParentSize()
                .offset(x = 8.dp, y = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF0A7F89)
            ),
            elevation = CardDefaults.cardElevation(0.dp),
            shape = RoundedCornerShape(16.dp)
        ) {}
        Column() {
            // Main card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column {

                    // HEADER (top green button)
                    Button(
                        onClick = { expanded = !expanded },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = if (expanded) 0.dp else 16.dp,
                            bottomEnd = if (expanded) 0.dp else 16.dp
                        ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF5BBFA7)
                        )
                    ) {
                        Text(
                            text = "Drugi dio",
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Checkbox(
                            checked = checked.size == lessons.size,
                            onCheckedChange = { isChecked ->
                                checked = if (isChecked) {
                                    lessons.indices.toSet()   // check all
                                } else {
                                    emptySet()                // uncheck all
                                }
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color.White,
                                uncheckedColor = Color.White,
                                checkmarkColor = Color(0xFF2E9F8F)
                            )
                        )
                    }
                }
            }
            // EXPANDED CONTENT
            if (expanded) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        //.background(Color(0xFF0A7F89))
                        .padding(vertical = 12.dp)
                        .offset(x = 8.dp, y = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(0.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    lessons.forEachIndexed { i, lesson ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${i + 8}. $lesson",
                                color = Color.White,
                                modifier = Modifier.weight(1f),
                                fontSize = 15.sp
                            )
                            Checkbox(
                                checked = checked.contains(i),
                                onCheckedChange = {
                                    checked = checked.toMutableSet().also {
                                        if (it.contains(i)) it.remove(i) else it.add(i)
                                    }
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color.White,
                                    uncheckedColor = Color.White,
                                    checkmarkColor = Color(0xFF2E9F8F)
                                )
                            )
                        }
                    }
                }
            }

        }
    }

    Spacer(modifier = Modifier.height(24.dp))
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

/*
@Composable
fun ModeScreen(navController: NavController, sessionData: SessionData) {
    Scaffold(
        floatingActionButton = {
            Button(onClick = { navController.navigate(Routes.QUIZ) }) { Text("Start Quiz") }
        }
    ) { _ ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { Text(sessionData.selectedSubject ?: "") }
    }
}
*/
