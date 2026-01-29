package com.example.matkul.view

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.matkul.R
import com.example.matkul.room.ProgramStudi
import com.example.matkul.view.route.DestinasiProgramStudi
import com.example.matkul.viewmodel.ProgramStudiViewModel
import com.example.matkul.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgramStudiScreen(
    navigateToEditProgramStudi: (Int) -> Unit,
    navigateToTambahProgramStudi: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProgramStudiViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MatkulTopAppBar(
                title = DestinasiProgramStudi.tittleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToTambahProgramStudi,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Program Studi"
                )
            }
        },
    ){
            innerPadding ->
        val uiStateProgramStudi by viewModel.programStudiUiState.collectAsState()
        BodyProgramStudi(
            itemProgramStudi = uiStateProgramStudi.listProgramStudi,
            onEditClick = navigateToEditProgramStudi,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun BodyProgramStudi(
    itemProgramStudi: List<ProgramStudi>,
    onEditClick: (Int) -> Unit,
    modifier: Modifier=Modifier){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ){
        if (itemProgramStudi.isEmpty()) {
            Text(
                text = "Tidak ada data Program Studi. Tap + untuk menambah data",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            ListProgramStudi(
                itemProgramStudi = itemProgramStudi,
                //edit 4
                onEditClick = {onEditClick(it.id)},
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
fun ListProgramStudi(
    itemProgramStudi : List<ProgramStudi>,
    onEditClick: (ProgramStudi) -> Unit,
    modifier: Modifier=Modifier
){
    LazyColumn(modifier = Modifier){
        items(items = itemProgramStudi, key = {it.id}){
                data ->  DataProgramStudi(
            programStudi = data,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_small))
                .clickable { onEditClick(data) })
        }
    }
}

@Composable
fun DataProgramStudi(
    programStudi: ProgramStudi,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ){
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ){
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = programStudi.namaProgramStudi,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    }
}