package com.example.matkul.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.matkul.R
import com.example.matkul.room.ProgramStudi
import com.example.matkul.view.route.DestinasiEntryMataKuliah
import com.example.matkul.viewmodel.DetailMataKuliah
import com.example.matkul.viewmodel.MataKuliahEntryViewModel
import com.example.matkul.viewmodel.UIStateMataKuliah
import com.example.matkul.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryMataKuliahScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MataKuliahEntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val listProdi by viewModel.dataProgramStudi.collectAsState()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MatkulTopAppBar(
                title = DestinasiEntryMataKuliah.tittleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior
            )
        }) { innerPadding ->
        EntryMataKuliahBody(
            uiStateMataKuliah = viewModel.uiStateMataKuliah,
            listProdi = listProdi,
            onMataKuliahValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveMataKuliah()
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
fun EntryMataKuliahBody(
    uiStateMataKuliah: UIStateMataKuliah,
    listProdi: List<ProgramStudi>,
    onMataKuliahValueChange: (DetailMataKuliah) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        FormInputMataKuliah(
            detailMataKuliah = uiStateMataKuliah.detailMataKuliah,
            listProdi = listProdi,
            onValueChange = onMataKuliahValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = uiStateMataKuliah.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputMataKuliah(
    detailMataKuliah: DetailMataKuliah,
    modifier: Modifier = Modifier,
    onValueChange: (DetailMataKuliah) -> Unit = {},
    enabled: Boolean = true,

    listProdi: List<ProgramStudi> = listOf()
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        // Nama Mata Kuliah
        OutlinedTextField(
            value = detailMataKuliah.namaMataKuliah,
            onValueChange = { onValueChange(detailMataKuliah.copy(namaMataKuliah = it)) },
            label = { Text("Nama Mata Kuliah*") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Jumlah SKS (Validasi Angka)
        OutlinedTextField(
            value = detailMataKuliah.jumlahSKS,
            onValueChange = {
                // Hanya izinkan input jika itu angka
                if (it.all { char -> char.isDigit() }) {
                    onValueChange(detailMataKuliah.copy(jumlahSKS = it))
                }
            },
            label = { Text("Jumlah SKS*") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Dosen Pengampu
        OutlinedTextField(
            value = detailMataKuliah.dosenPengampu,
            onValueChange = { onValueChange(detailMataKuliah.copy(dosenPengampu = it)) },
            label = { Text("Nama Dosen Pengampu") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Dropdown Program Studi
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            // Mencari nama prodi yang terpilih berdasarkan ID
            val selectedProdi = listProdi.find { it.id.toString() == detailMataKuliah.idProgramStudi }

            OutlinedTextField(
                value = selectedProdi?.namaProgramStudi ?: "Pilih Program Studi",
                onValueChange = {},
                readOnly = true, // User tidak mengetik, hanya memilih
                label = { Text("Program Studi*") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                enabled = enabled
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listProdi.forEach { prodi ->
                    DropdownMenuItem(
                        text = { Text(prodi.namaProgramStudi) },
                        onClick = {
                            onValueChange(detailMataKuliah.copy(idProgramStudi = prodi.id.toString()))
                            expanded = false
                        }
                    )
                }
            }
        }

        if (enabled) {
            Text(
                text = "* required fields",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}