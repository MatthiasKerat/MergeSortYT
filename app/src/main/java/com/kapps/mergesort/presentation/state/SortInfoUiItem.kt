package com.kapps.mergesort.presentation.state

import androidx.compose.ui.graphics.Color
import com.kapps.mergesort.domain.model.SortState

data class SortInfoUiItem(
    val id:String,
    val depth:Int,
    val sortState: SortState,
    val sortParts:List<List<Int>>,
    val color: Color
)
