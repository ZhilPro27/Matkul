package com.example.matkul.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.matkul.R
import com.example.matkul.view.route.DestinasiEntryProgramStudi
import com.example.matkul.viewmodel.DetailProgramStudi
import com.example.matkul.viewmodel.ProgramStudiEntryViewModel
import com.example.matkul.viewmodel.UIStateProgramStudi
import com.example.matkul.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryProgramStudiScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProgramStudiEntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MatkulTopAppBar(
                title = DestinasiEntryProgramStudi.tittleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior
            )
        }) { innerPadding ->
        EntryProgramStudiBody(
            uiStateProgramStudi = viewModel.uiStateProgramStudi,
            onProgramStudiValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveProgramStudi()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryProgramStudiBody(
    uiStateProgramStudi: UIStateProgramStudi,
    onProgramStudiValueChange: (DetailProgramStudi) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        FormInputProgramStudi(
            detailProgramStudi = uiStateProgramStudi.detailProgramStudi,
            onValueChange = onProgramStudiValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = uiStateProgramStudi.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputProgramStudi(
    detailProgramStudi: DetailProgramStudi,
    modifier: Modifier = Modifier,
    onValueChange: (DetailProgramStudi) -> Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ){
        OutlinedTextField(
            value = detailProgramStudi.namaProgramStudi,
            onValueChange = {onValueChange(detailProgramStudi.copy(namaProgramStudi = it)) },
            label = { Text("Nama Program Studi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        if (enabled) {
            Text(
                text = "* required fields",
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium)),
            thickness = dimensionResource(R.dimen.padding_small),
            color = Color.Blue
        )
    }
}