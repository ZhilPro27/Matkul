package com.example.matkul.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.matkul.view.route.DestinasiEditProgramStudi
import com.example.matkul.viewmodel.ProgramStudiEditViewModel
import com.example.matkul.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProgramStudiScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProgramStudiEditViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            MatkulTopAppBar(
                title = DestinasiEditProgramStudi.tittleRes,
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        val coroutineScope = rememberCoroutineScope()
        EntryProgramStudiBody(
            uiStateProgramStudi = viewModel.uiStateProgramStudi,
            onProgramStudiValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateProgramStudi()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}