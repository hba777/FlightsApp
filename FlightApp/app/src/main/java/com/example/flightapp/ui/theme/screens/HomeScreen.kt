package com.example.flightapp.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightapp.AppViewModelProvider
import com.example.flightapp.R
import com.example.flightapp.data.Flights
import com.example.flightapp.ui.theme.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

/**
 * Entry route for Home screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)


) {
//    val homeUiState by viewModel.homeUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiStateQuery = viewModel.uiStateSearch.collectAsState().value
    val focusManager = LocalFocusManager.current

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            FlightsTopAppBar(
                title = stringResource(id = R.string.app_name),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },

        bottomBar = {
            BottomAppBar {
                IconButton(onClick = {}, enabled = true,modifier=Modifier.weight(0.5f),  content = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_home_24),
                        contentDescription = null,
                        modifier= Modifier.fillMaxSize()
                    )
                })
                IconButton(onClick = navigateToItemEntry ,modifier= Modifier.weight(0.5f), content = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_favorite_24),
                        contentDescription = null,
                        modifier= Modifier.fillMaxSize()
                    )
                })
            }

        }

    ) { innerPadding ->

        HomeBody(
            flightsList = uiStateQuery.itemList,
//            onFlightsClick = {
//                coroutineScope.launch {
//                    viewModel.saveFavorite(it)
//                }
//            },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            viewModel=viewModel
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightsTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        }
    )
}
// onFlightsClick: (Flights) -> Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeBody(
    flightsList: List<Flights>, modifier: Modifier = Modifier,
    viewModel:HomeViewModel
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        val uiStateQuery = viewModel.uiStateSearch.collectAsState().value
        val searchResults by viewModel.getFlightsBySearchQuery(uiStateQuery.query).collectAsState(emptyList())

        OutlinedTextField(value = uiStateQuery.query, onValueChange = {viewModel.updateQuery(it)},
            singleLine = true, placeholder = { Text(text = "Search Flights")
            },
            modifier =Modifier.fillMaxWidth()
        )

        LazyColumn{
                    items(searchResults){item ->
                        InventoryFlights(flights = item, viewModel = viewModel )
                    }
                }
            }

        }




@Composable
private fun InventoryFlights(
    flights: Flights, modifier: Modifier = Modifier,viewModel:HomeViewModel) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)

    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row{
                Text(text = "Flight Name")
                Spacer(Modifier.weight(1f))
                Text(text = "Iata Code")

            }
            Row {

                Text(
                    text = flights.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                Text(text = flights.iataCode,
                    style = MaterialTheme.typography.bodyMedium,
                )


            }
            Row(modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End) {
                IconButton(onClick = {viewModel.saveFavorite(flights)}, content = {
                    Icon(painter = painterResource(id = R.drawable.baseline_favorite_24), contentDescription = null,modifier.size(36.dp) )
                })
            }

        }
    }
}
