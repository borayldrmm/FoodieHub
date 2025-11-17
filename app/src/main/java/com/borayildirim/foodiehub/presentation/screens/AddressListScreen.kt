package com.borayildirim.foodiehub.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.borayildirim.foodiehub.R
import com.borayildirim.foodiehub.domain.model.Address
import com.borayildirim.foodiehub.presentation.ui.components.address.AddressCard
import com.borayildirim.foodiehub.presentation.ui.components.address.EmptyAddressState
import com.borayildirim.foodiehub.presentation.viewmodels.AddressListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressListScreen(
    onNavigateBack: () -> Unit,
    onNavigateToAddAddress: () -> Unit,
    onNavigateToEditAddress: (String) -> Unit,
    viewModel: AddressListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var addressToDelete by remember { mutableStateOf<Address?>(null) }

    // Error handling
    LaunchedEffect(uiState.error) {
        uiState.error?.let { error ->
            snackbarHostState.showSnackbar(
                message = error,
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.my_addresses),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_txt)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            if (uiState.addresses.isNotEmpty()) {
                FloatingActionButton(
                    onClick = onNavigateToAddAddress,
                    containerColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.add_address),
                        tint = Color.White
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.addresses.isEmpty() -> {
                    EmptyAddressState(
                        onAddAddressClick = onNavigateToAddAddress,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = uiState.addresses,
                            key = { it.id }
                        ) { address ->
                            AddressCard(
                                address = address,
                                onDeleteClick = {
                                    addressToDelete = address
                                    showDeleteDialog = true
                                },
                                onSetDefaultClick = {
                                    viewModel.setDefaultAddress(address.id)
                                },
                                onEditClick = {
                                    onNavigateToEditAddress(address.id)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    // Delete Confirmation Dialog
    if (showDeleteDialog && addressToDelete != null) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                addressToDelete = null
            },
            title = {
                Text(text = stringResource(R.string.delete_address))
            },
            text = {
                Text(text = stringResource(R.string.delete_address_confirmation))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        addressToDelete?.let { viewModel.deleteAddress(it) }
                        showDeleteDialog = false
                        addressToDelete = null
                    }
                ) {
                    Text(
                        text = stringResource(R.string.delete),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        addressToDelete = null
                    }
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
            }
        )
    }
}