package com.kapps.mergesort

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kapps.mergesort.domain.model.SortState
import com.kapps.mergesort.presentation.SortViewModel
import com.kapps.mergesort.ui.theme.QuickSortTheme
import com.kapps.mergesort.ui.theme.gray
import com.kapps.mergesort.ui.theme.orange

class MainActivity : ComponentActivity() {

    private val sortViewModel by viewModels<SortViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = orange.toArgb()
        window.navigationBarColor = orange.toArgb()

        setContent {
            QuickSortTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(gray)
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ){
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.TopCenter),
                        verticalArrangement = Arrangement.spacedBy(30.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        itemsIndexed(
                            sortViewModel.sortInfoUiItemList,
                            key = { _, it ->
                                it.id
                            }
                        ){ index, it ->
                            val depthParts = it.sortParts
                            if(index == 0){
                                Text(
                                    "Dividing",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 26.sp,
                                    modifier = Modifier
                                        .padding(bottom = 20.dp)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Start
                                )
                            }
                            if(index == 4){
                                Text(
                                    "Merging",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 26.sp,
                                    modifier = Modifier
                                        .padding(bottom = 20.dp)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Start
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ){
                                for(part in depthParts){
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                                        modifier = Modifier
                                            .padding(start = if (depthParts.indexOf(part) == 0) 0.dp else 17.dp)
                                            .background(it.color, RoundedCornerShape(10.dp))
                                            .padding(5.dp)

                                    ){
                                        for(numberInformation in part){
                                            if (part.indexOf(numberInformation) != part.size-1){
                                                Text(
                                                    "$numberInformation |",
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White,
                                                    fontSize = 19.sp
                                                )
                                            }else{
                                                Text(
                                                    "$numberInformation",
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White,
                                                    fontSize = 19.sp
                                                )
                                            }

                                        }
                                    }
                                }
                            }

                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(gray)
                            .padding(15.dp)
                            .align(Alignment.BottomCenter),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ){
                        Text(
                            "${sortViewModel.listToSort}",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Button(
                            onClick = {
                                sortViewModel.startSorting()
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = orange,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(CircleShape)
                        ){
                            Text(
                                "Start sort",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                    }
                }
            }
        }
    }
}
