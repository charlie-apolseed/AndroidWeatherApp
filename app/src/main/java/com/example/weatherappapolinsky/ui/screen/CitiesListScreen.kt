package com.example.weatherappapolinsky.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherappapolinsky.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesListScreen(
    modifier: Modifier = Modifier,
    viewModel: CitiesListModel = hiltViewModel(),
    onNavigateToDetails: (String) -> Unit
) {
    var displayAddLocationDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "Logo",
                                modifier = modifier
                                    .size(50.dp)
                                    .padding(end = 10.dp)
                            )
                            Text(
                                text = "| Rain Dodger",
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                        Text(
                            modifier = Modifier.padding(bottom = 10.dp),
                            style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                            text = "Accurate, Simple, and Fast. The way weather should be"
                        )
                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                ),
                actions = {
                    IconButton(
                        onClick = { displayAddLocationDialog = true }) {
                        Icon(Icons.Rounded.AddCircle, contentDescription = "Delete")
                    }
                }

            )
        }
    ) { innerPadding ->
        Column {
            if (viewModel.locations.isEmpty()) {
                Text(text = "No locations added")
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    items(viewModel.locations.size) { index ->
                        LocationCard(
                            location = viewModel.locations[index],
                            viewModel = viewModel,
                            onClick = { onNavigateToDetails(it) }
                        )
                    }
                }
            }
        }

        if (displayAddLocationDialog) {
            AddLocationDialog(
                viewModel,
                onCancel = {
                    displayAddLocationDialog = false
                },
            )
        }
    }
}

@Composable
fun AddLocationDialog(viewModel: CitiesListModel, onCancel: () -> Unit) {
    var newLocationTitle by rememberSaveable { mutableStateOf("") }
    var inputError by rememberSaveable { mutableStateOf(false) }

    Dialog(onDismissRequest = {
        onCancel()
    }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Add a Location",
                    style = MaterialTheme.typography.headlineSmall
                )
                OutlinedTextField(
                    value = newLocationTitle,
                    onValueChange = {
                        newLocationTitle = it
                        if (newLocationTitle.isNotBlank()) {
                            inputError = false
                        }
                    },
                    label = { Text("Location") },
                    modifier = Modifier
                        .fillMaxWidth(.9f),
                    isError = inputError,
                    supportingText = { Text("Location cannot be empty") },
                    trailingIcon = {
                        if (inputError)
                            Icon(
                                Icons.Filled.Warning, "error",
                                tint = MaterialTheme.colorScheme.error
                            )
                    }
                )
                Button(
                    onClick = {
                        if (newLocationTitle.isNotBlank()) {
                            viewModel.addLocation(newLocationTitle)
                            onCancel()
                        } else {
                            inputError = true
                        }
                    }
                ) {
                    Text(text = "Add Location")
                }
            }
        }
    }
}

@Composable
fun LocationCard(viewModel: CitiesListModel, location: String, onClick: (city: String) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ), shape = RoundedCornerShape(20.dp), elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ), modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clickable {
                onClick(location)
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = location,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(10.dp)
            )
            IconButton(
                onClick = { viewModel.deleteLocation(location) },
                modifier = Modifier.padding(10.dp)
            ) {
                Icon(
                    Icons.Filled.Delete, contentDescription = "Delete"
                )
            }
        }
    }
}
