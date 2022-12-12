package com.kapps.mergesort.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kapps.mergesort.domain.MergeSortUseCase
import com.kapps.mergesort.presentation.state.SortInfoUiItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.UUID

class SortViewModel(
    private val mergeSortUseCase: MergeSortUseCase = MergeSortUseCase()
) :ViewModel() {

    var listToSort = mutableListOf<Int>()

    var sortInfoUiItemList = mutableStateListOf<SortInfoUiItem>()

    init {
        for(i in 0 until 8){
            listToSort.add(
                (10..99).random()
            )
        }
    }

    fun startSorting(){
        sortInfoUiItemList.clear()
        subscribeToSortChanges()
        viewModelScope.launch {
            mergeSortUseCase(listToSort, 0)
        }
    }

    private var job: Job? = null
    private fun subscribeToSortChanges(){
        job?.cancel()
        job = viewModelScope.launch {
            mergeSortUseCase.sortFlow.collect { sortInfo ->
                val depthAlreadyExistListIndex = sortInfoUiItemList.indexOfFirst {
                    it.depth == sortInfo.depth && it.sortState == sortInfo.sortState
                }

                if(depthAlreadyExistListIndex == -1){
                    sortInfoUiItemList.add(
                        SortInfoUiItem(
                            id = UUID.randomUUID().toString(),
                            depth = sortInfo.depth,
                            sortState = sortInfo.sortState,
                            sortParts = listOf(sortInfo.sortParts),
                            color = Color(
                                (0..255).random(),
                                (0..200).random(),
                                (0..200).random(),
                                255)
                        )
                    )
                }else{
                    val currentPartList = sortInfoUiItemList[depthAlreadyExistListIndex].sortParts.toMutableList()
                    currentPartList.add(sortInfo.sortParts)
                    sortInfoUiItemList.set(
                        depthAlreadyExistListIndex,
                        sortInfoUiItemList[depthAlreadyExistListIndex].copy(sortParts = currentPartList)
                    )
                }

                sortInfoUiItemList.sortedWith(
                    compareBy(
                        {
                            it.sortState
                        },
                        {
                            it.depth
                        }
                    )
                )
            }
        }
    }

}