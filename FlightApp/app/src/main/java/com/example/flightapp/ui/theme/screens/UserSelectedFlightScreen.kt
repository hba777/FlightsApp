package com.example.flightapp.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightapp.AppViewModelProvider
import com.example.flightapp.R
import com.example.flightapp.data.Favorites
import com.example.flightapp.ui.theme.navigation.NavigationDestination

object UserSelectedFlightScreen : NavigationDestination {
    override val route = "Favorites"
    override val titleRes = R.string.app_name
    const val itemIdArg = "itemId"
}

/**
 * Entry route for Home screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoriteScreen(
    navigateToHome:() -> Unit,
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    modifier: Modifier = Modifier,
    viewModel: FavoritesVIewModel = viewModel(factory = AppViewModelProvider.Factory)


) {
    val favUiState by viewModel.favoriteUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            FavoritesTopAppBar(
                title = "FavoritesScreen",
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = navigateToItemEntry,
//                shape = MaterialTheme.shapes.medium,
//                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Add,
//                    contentDescription = stringResource(R.string.item_entry_title)
//                )
//            }
//        },
        bottomBar = {
            BottomAppBar {
                IconButton(onClick = navigateToHome, enabled = true,modifier= Modifier.weight(0.5f),  content = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_home_24),
                        contentDescription = null,
                        modifier= Modifier.fillMaxSize()
                    )
                })
                IconButton(onClick = {} ,modifier= Modifier.weight(0.5f), content = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_favorite_24),
                        contentDescription = null,
                        modifier= Modifier.fillMaxSize()
                    )
                })
            }

        }

    ) { innerPadding ->
        FavoritesBody(
            favoritesList = favUiState.itemList,
            viewModel=viewModel,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesTopAppBar(
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


@Composable
private fun FavoritesBody(
    favoritesList: List<Favorites>,viewModel: FavoritesVIewModel, modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (favoritesList.isEmpty()) {
            Text(
                text = "No favorite items Yet",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            InventoryList(
                favoritesList = favoritesList,
                viewModel=viewModel,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun InventoryList(
    favoritesList: List<Favorites>,viewModel: FavoritesVIewModel,  modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items = favoritesList, key = { it.id }) { Favorites ->
            InventoryFavorites(favorites = Favorites,
                viewModel=viewModel,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small)))
        }
    }
}

@Composable
private fun InventoryFavorites(
    favorites: Favorites,viewModel: FavoritesVIewModel, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row {
                Text(text = "Departure Code",
                    )
                Spacer(Modifier.weight(1f))
                Text(text = "Destination Code")
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = favorites.departureCode,
                    style = MaterialTheme.typography.titleLarge,

                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = favorites.destinationCode,
                    style = MaterialTheme.typography.titleLarge,)

            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End) {
                IconButton(onClick = {viewModel.deleteFavorite(favorites)}, content = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_favorite_24),
                        contentDescription = null,
                        modifier= Modifier.fillMaxSize()
                    )
                })
            }

        }
    }
}