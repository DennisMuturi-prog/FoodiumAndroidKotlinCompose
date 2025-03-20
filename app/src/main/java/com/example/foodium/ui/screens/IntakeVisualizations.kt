package com.example.foodium.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodium.models.KenyanRecipe
import com.example.foodium.models.WorldwideRecipe
import com.example.foodium.ui.components.DateRangeSelector
import com.example.foodium.ui.components.UserIntakeItem
import com.example.foodium.ui.components.UserKenyanRecipeIntakeItem
import com.example.foodium.ui.viewmodels.RecipeIntakeByDateState
import com.example.foodium.ui.viewmodels.RecipeIntakeViewModel
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
//@Preview(showBackground = true)
@Composable
fun IntakeVisualizations(modifier: Modifier = Modifier,recipeIntakeViewModel: RecipeIntakeViewModel,moveToRecipeInfoScreen:(WorldwideRecipe)->Unit,
                         moveToKenyanRecipeInfoScreen:(KenyanRecipe)->Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedOption by remember {
        mutableStateOf("1 week")
    }
    var dateRange by remember {
        mutableStateOf(Pair(ZonedDateTime.now(ZoneOffset.UTC).minusDays(7).toString(),ZonedDateTime.now(ZoneOffset.UTC).toString()))
    }
    val recipesIntakeByDate=recipeIntakeViewModel.recipeIntakeByDate.observeAsState()
    LaunchedEffect(selectedOption) {
        recipeIntakeViewModel.fetchRecipesByDate(dateRange.first,dateRange.second)
        Log.d("time ranges","${dateRange.first} ${dateRange.second}")
    }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


//        Text("${dateRange.first} : ${dateRange.second}")
        item {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                TextField(
                    value = selectedOption,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Choose time range") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                        .padding(16.dp)
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text("24hrs")
                        },
                        onClick = {
                            val now = ZonedDateTime.now(ZoneOffset.UTC)
                            val beginningOfToday = now.minusHours(24)
                            dateRange = Pair(
                                first = beginningOfToday.toString(),
                                second = now.toString()
                            )
                            selectedOption = "last 24 hours"
                            expanded = false
                        },

                        )
                    DropdownMenuItem(
                        text = {
                            Text("last 1 week")
                        },
                        onClick = {
                            val nowDate = ZonedDateTime.now(ZoneOffset.UTC)
                            val oneWeekEarlierDate = nowDate.minusWeeks(1)
                            dateRange = Pair(
                                first = oneWeekEarlierDate.toString(),
                                second = nowDate.toString()
                            )
                            selectedOption = "last 1 week"
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text("last 2 weeks")
                        },
                        onClick = {
                            val nowDate = ZonedDateTime.now(ZoneOffset.UTC)
                            val twoWeekEarlierDate = nowDate.minusWeeks(2)
                            dateRange = Pair(
                                first = twoWeekEarlierDate.toString(),
                                second = nowDate.toString()
                            )
                            selectedOption = "last 2 weeks"
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text("last 1 month")
                        },
                        onClick = {
                            val nowDate = ZonedDateTime.now(ZoneOffset.UTC)
                            val oneMonthEarlierDate = nowDate.minusMonths(1)
                            dateRange = Pair(
                                first = oneMonthEarlierDate.toString(),
                                second = nowDate.toString()
                            )

                            selectedOption = "last 1 month"
                            expanded=false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            DateRangeSelector(
                                onDateRangeSelected = {
                                    val userFriendlyDate1 =
                                        it.first?.let { it1 -> convertMillisToDate(it1) } ?: ""
                                    val userFriendlyDate2 =
                                        it.second?.let { it1 -> convertMillisToDate(it1) } ?: ""
                                    val date1 =
                                        it.first?.let { it1 -> convertLongToTimestamp(it1) } ?: ""
                                    val date2 =
                                        it.second?.let { it1 -> convertLongToTimestamp(it1) } ?: ""
                                    dateRange = Pair(
                                        first = date1,
                                        second = date2
                                    )
                                    selectedOption = "$userFriendlyDate1 - $userFriendlyDate2"
                                    expanded = false
                                },
                            )
                        },
                        onClick = {
                        }
                    )
                }

            }
        }
        when(val result=recipesIntakeByDate.value){
            is RecipeIntakeByDateState.Success->{
                stickyHeader {
                    Text("worldwide recipe intake", fontSize = 25.sp)
                }
                items(result.results.size){index->
                    UserIntakeItem(userIntake = result.results[index], moveToRecipeInfoScreen = {moveToRecipeInfoScreen(it)})
                }
                stickyHeader {
                    Text("kenyan recipe intake", fontSize = 25.sp)
                }
                items(result.kenyanResults.size){index->
                    UserKenyanRecipeIntakeItem (userIntake = result.kenyanResults[index], moveToKenyanRecipeInfoScreen = {moveToKenyanRecipeInfoScreen(it)})
                }


            }
            is RecipeIntakeByDateState.Loading->{
               item {  CircularProgressIndicator()}}
            is RecipeIntakeByDateState.Error->{
                item { Text(result.message) }
                }
            null->{}
            }
        }
}

fun convertLongToTimestamp(selectedDate: Long): String {
    val instant = Instant.ofEpochMilli(selectedDate)
    val selectedDateUtc = instant.atZone(ZoneOffset.UTC).toLocalDate()

    // Get today's date in UTC
    val todayUtc = LocalDate.now(ZoneOffset.UTC)

    // If selected date is today, set time to 23:59:59.999Z
    val adjustedInstant = if (selectedDateUtc == todayUtc) {
        selectedDateUtc.atTime(23, 59, 59, 999_000_000).toInstant(ZoneOffset.UTC)
    } else {
        instant
    }

    return adjustedInstant.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT)
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}
