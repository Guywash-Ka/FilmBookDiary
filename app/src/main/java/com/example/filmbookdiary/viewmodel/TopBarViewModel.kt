package com.example.filmbookdiary.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.filmbookdiary.data.WidgetState
import com.example.filmbookdiary.database.FilterState

class TopBarViewModel: ViewModel() {

    private val _searchWidgetState: MutableState<WidgetState> =
        mutableStateOf(value = WidgetState.CLOSED)
    val searchWidgetState: State<WidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    private val _filterWidgetState: MutableState<WidgetState> =
        mutableStateOf(value = WidgetState.CLOSED)
    val filterWidgetState: State<WidgetState> = _filterWidgetState

    private val _filterSelectState: MutableState<FilterState> =
        mutableStateOf(value = FilterState.NAME)
    val filterSelectState: State<FilterState> = _filterSelectState

    fun updateSearchWidgetState(newValue: WidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    fun updateFilterWidgetState(newValue: WidgetState) {
        _filterWidgetState.value = newValue
    }

    fun updateFilterSelectState(newValue: FilterState) {
        _filterSelectState.value = newValue
    }
}