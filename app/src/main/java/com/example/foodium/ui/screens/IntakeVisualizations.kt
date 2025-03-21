package com.example.foodium.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.Point
import com.example.foodium.models.KenyanRecipe
import com.example.foodium.models.NutrientPoints
import com.example.foodium.models.UserIntake
import com.example.foodium.models.UserKenyanIntake
import com.example.foodium.models.WorldwideRecipe
import com.example.foodium.ui.components.DateRangeSelector
import com.example.foodium.ui.components.SingleLineChartWithGridLines
import com.example.foodium.ui.components.UserIntakeItem
import com.example.foodium.ui.components.UserKenyanRecipeIntakeItem
import com.example.foodium.ui.viewmodels.RecipeIntakeByDateState
import com.example.foodium.ui.viewmodels.RecipeIntakeViewModel
import java.text.SimpleDateFormat
import java.time.Duration
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
//        item {
//            ChartsVisualization()
//        }
        when(val result=recipesIntakeByDate.value){
            is RecipeIntakeByDateState.Success->{
                val nutrientPoints= convertIntakeToPoints(result.kenyanResults,result.results)
                item {
                    Text(
                        modifier=Modifier.padding(12.dp),
                        text = "carbs",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    SingleLineChartWithGridLines(
                       nutrientPoints.carbs
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Divider(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp))
                }
                item {
                    Text(
                        modifier=Modifier.padding(12.dp),
                        text = "proteins",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    SingleLineChartWithGridLines(
                        nutrientPoints.proteins
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Divider(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp))
                }
                item {
                    Text(
                        modifier=Modifier.padding(12.dp),
                        text = "energy",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    SingleLineChartWithGridLines(
                        nutrientPoints.energy
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Divider(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp))
                }
                item {
                    Text(
                        modifier=Modifier.padding(12.dp),
                        text = "fat",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    SingleLineChartWithGridLines(
                        nutrientPoints.fat
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Divider(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp))
                }
                item {
                    Text(
                        modifier=Modifier.padding(12.dp),
                        text = "fibre",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    SingleLineChartWithGridLines(
                        nutrientPoints.fibre
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Divider(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp))
                }
                item {
                    Text(
                        modifier=Modifier.padding(12.dp),
                        text = "vitamin A",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    SingleLineChartWithGridLines(
                        nutrientPoints.vitaminA
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Divider(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp))
                }
                item {
                    Text(
                        modifier=Modifier.padding(12.dp),
                        text = "iron",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    SingleLineChartWithGridLines(
                        nutrientPoints.iron
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Divider(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp))
                }
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
fun convertIntakeToPoints(kenyanRecipeIntake:List<UserKenyanIntake>,worldwideRecipeIntake:List<UserIntake>):NutrientPoints{
    val carbsPoint = mutableListOf<Point>()
    val proteinsPoint= mutableListOf<Point>()
    val energyPoints= mutableListOf<Point>()
    val fibrePoints= mutableListOf<Point>()
    val fatPoints  = mutableListOf<Point>()
    val vitaminPoints = mutableListOf<Point>()
    val ironPoints = mutableListOf<Point>()
    val kenyanLastItemIndex=kenyanRecipeIntake.size-1
    val worldwideLastItemIndex=worldwideRecipeIntake.size-1
    val nowDate=ZonedDateTime.now()

    for(nums in 0..kenyanLastItemIndex){
        carbsPoint.add(Point(x= findTimeDifferenceFromNow(kenyanRecipeIntake[nums].createdAt,nowDate), y = kenyanRecipeIntake[nums].carbohydratesg.toFloat()))
        proteinsPoint.add(Point(x= findTimeDifferenceFromNow(kenyanRecipeIntake[nums].createdAt,nowDate), y = kenyanRecipeIntake[nums].proteinsg.toFloat()))
        energyPoints.add(Point(x= findTimeDifferenceFromNow(kenyanRecipeIntake[nums].createdAt,nowDate), y = kenyanRecipeIntake[nums].energykcal.toFloat()))
        fibrePoints.add(Point(x= findTimeDifferenceFromNow(kenyanRecipeIntake[nums].createdAt,nowDate), y = kenyanRecipeIntake[nums].fibreg.toFloat()))
        fatPoints.add(Point(x= findTimeDifferenceFromNow(kenyanRecipeIntake[nums].createdAt,nowDate), y = kenyanRecipeIntake[nums].fatg.toFloat()))
        vitaminPoints.add(Point(x= findTimeDifferenceFromNow(kenyanRecipeIntake[nums].createdAt,nowDate), y = kenyanRecipeIntake[nums].vitaminAmcg.toFloat()))
        ironPoints.add(Point(x= findTimeDifferenceFromNow(kenyanRecipeIntake[nums].createdAt,nowDate), y = kenyanRecipeIntake[nums].ironmg.toFloat()))
    }
    for(nums in 0..worldwideLastItemIndex){
        carbsPoint.add(Point(x= findTimeDifferenceFromNow(worldwideRecipeIntake[nums].createdAt,nowDate), y = worldwideRecipeIntake[nums].carbohydrateBySummation.toFloat()))
        proteinsPoint.add(Point(x= findTimeDifferenceFromNow(worldwideRecipeIntake[nums].createdAt,nowDate), y = worldwideRecipeIntake[nums].protein.toFloat()))
        energyPoints.add(Point(x= findTimeDifferenceFromNow(worldwideRecipeIntake[nums].createdAt,nowDate), y = worldwideRecipeIntake[nums].energy.toFloat()))
        fibrePoints.add(Point(x= findTimeDifferenceFromNow(worldwideRecipeIntake[nums].createdAt,nowDate), y = worldwideRecipeIntake[nums].fiberTotalDietary.toFloat()))
        fatPoints.add(Point(x= findTimeDifferenceFromNow(worldwideRecipeIntake[nums].createdAt,nowDate), y = worldwideRecipeIntake[nums].totalFatNLEA.toFloat()))
        vitaminPoints.add(Point(x= findTimeDifferenceFromNow(worldwideRecipeIntake[nums].createdAt,nowDate), y = worldwideRecipeIntake[nums].vitaminARAE.toFloat()))
        ironPoints.add(Point(x= findTimeDifferenceFromNow(worldwideRecipeIntake[nums].createdAt,nowDate), y = worldwideRecipeIntake[nums].ironFe.toFloat()))
    }
    val sizeOfPoints=carbsPoint.size-1

    carbsPoint.sortByDescending {  it.x}
    proteinsPoint.sortByDescending {  it.x}
    fatPoints.sortByDescending {  it.x}
    energyPoints.sortByDescending {  it.x}
    fibrePoints.sortByDescending {  it.x}
    ironPoints.sortByDescending{  it.x}
    vitaminPoints.sortByDescending{  it.x}
    for(nums in 0..sizeOfPoints){
        carbsPoint[nums]=Point(nums.toFloat(),carbsPoint[nums].y)
        proteinsPoint[nums]=Point(nums.toFloat(),proteinsPoint[nums].y)
        energyPoints[nums]=Point(nums.toFloat(),energyPoints[nums].y)
        fatPoints[nums]=Point(nums.toFloat(),fatPoints[nums].y)
        fibrePoints[nums]=Point(nums.toFloat(),fibrePoints[nums].y)
        vitaminPoints[nums]=Point(nums.toFloat(),vitaminPoints[nums].y)
        ironPoints[nums]=Point(nums.toFloat(),ironPoints[nums].y)

    }
    val nutrientsPoints=NutrientPoints(
        carbs = carbsPoint,
        proteins = proteinsPoint,
        fibre = fibrePoints,
        fat = fatPoints,
        vitaminA = vitaminPoints,
        iron = ironPoints,
        energy = energyPoints
    )
    Log.d("nutrient carb points","${nutrientsPoints.carbs.size}")
    Log.d("nutrient protein points","${nutrientsPoints.proteins.size}")
    Log.d("nutrient fat points","${nutrientsPoints.fat.size}")
    Log.d("nutrient fibre points","${nutrientsPoints.energy.size}")
    Log.d("nutrient vitamin A points","${nutrientsPoints.vitaminA.size}")
    Log.d("nutrient iron points","${nutrientsPoints.iron.size}")
    Log.d("nutrient energy points","${nutrientsPoints.energy.size}")




    return  nutrientsPoints
}
fun convertIntakeToPoints2(kenyanRecipeIntake:List<UserKenyanIntake>,worldwideRecipeIntake:List<UserIntake>):NutrientPoints{
    val carbsPoint = mutableListOf<Point>()
    val proteinsPoint= mutableListOf<Point>()
    val energyPoints= mutableListOf<Point>()
    val fibrePoints= mutableListOf<Point>()
    val fatPoints  = mutableListOf<Point>()
    val vitaminPoints = mutableListOf<Point>()
    val ironPoints = mutableListOf<Point>()
    val kenyanLastItemIndex=kenyanRecipeIntake.size-1
    val kenyanSize=kenyanRecipeIntake.size
    val worldwideLastItemIndex=worldwideRecipeIntake.size-1
    val nowDate=ZonedDateTime.now()

    for(nums in 0..kenyanLastItemIndex){
        carbsPoint.add(Point(x= nums.toFloat(), y = kenyanRecipeIntake[nums].carbohydratesg.toFloat()))
        proteinsPoint.add(Point(x= nums.toFloat(), y = kenyanRecipeIntake[nums].proteinsg.toFloat()))
        energyPoints.add(Point(x= nums.toFloat(), y = kenyanRecipeIntake[nums].energykcal.toFloat()))
        fibrePoints.add(Point(x= nums.toFloat(), y = kenyanRecipeIntake[nums].fibreg.toFloat()))
        fatPoints.add(Point(x= nums.toFloat(), y = kenyanRecipeIntake[nums].fatg.toFloat()))
        vitaminPoints.add(Point(x= nums.toFloat(), y = kenyanRecipeIntake[nums].vitaminAmcg.toFloat()))
        ironPoints.add(Point(x= nums.toFloat(), y = kenyanRecipeIntake[nums].ironmg.toFloat()))
    }
    for(nums in 0..worldwideLastItemIndex){
        carbsPoint.add(Point(x= (nums+kenyanSize).toFloat(), y = worldwideRecipeIntake[nums].carbohydrateBySummation.toFloat()))
        proteinsPoint.add(Point(x= (nums+kenyanSize).toFloat(), y = worldwideRecipeIntake[nums].protein.toFloat()))
        energyPoints.add(Point(x= (nums+kenyanSize).toFloat(), y = worldwideRecipeIntake[nums].energy.toFloat()))
        fibrePoints.add(Point(x= (nums+kenyanSize).toFloat(), y = worldwideRecipeIntake[nums].fiberTotalDietary.toFloat()))
        fatPoints.add(Point(x= (nums+kenyanSize).toFloat(), y = worldwideRecipeIntake[nums].totalFatNLEA.toFloat()))
        vitaminPoints.add(Point(x= (nums+kenyanSize).toFloat(), y = worldwideRecipeIntake[nums].vitaminARAE.toFloat()))
        ironPoints.add(Point(x= (nums+kenyanSize).toFloat(), y = worldwideRecipeIntake[nums].ironFe.toFloat()))
    }
    val nutrientsPoints=NutrientPoints(
        carbs = carbsPoint,
        proteins = proteinsPoint,
        fibre = fibrePoints,
        fat = fatPoints,
        vitaminA = vitaminPoints,
        iron = ironPoints,
        energy = energyPoints
    )
    Log.d("nutrient carb points","${nutrientsPoints.carbs.size}")
    Log.d("nutrient protein points","${nutrientsPoints.proteins.size}")
    Log.d("nutrient fat points","${nutrientsPoints.fat.size}")
    Log.d("nutrient fibre points","${nutrientsPoints.energy.size}")
    Log.d("nutrient vitamin A points","${nutrientsPoints.vitaminA.size}")
    Log.d("nutrient iron points","${nutrientsPoints.iron.size}")
    Log.d("nutrient energy points","${nutrientsPoints.energy.size}")




    return  nutrientsPoints
}


fun findTimeDifferenceFromNow(date:String,now:ZonedDateTime):Float{
    val intakeDate=ZonedDateTime.parse(date)
    val timeDifference=Duration.between(intakeDate,now)
    return timeDifference.toMinutes().toFloat()

}
