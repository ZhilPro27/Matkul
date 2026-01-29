package com.example.matkul.view.uicontroller

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.matkul.view.EditProgramStudiScreen
import com.example.matkul.view.EntryProgramStudiScreen
import com.example.matkul.view.ProgramStudiScreen
import com.example.matkul.view.route.DestinasiEditProgramStudi
import com.example.matkul.view.route.DestinasiEditProgramStudi.itemIdArg
import com.example.matkul.view.route.DestinasiEntryProgramStudi
import com.example.matkul.view.route.DestinasiProgramStudi

@Composable
fun MatkulApp(navController: NavHostController = rememberNavController(),
              modifier: Modifier) {
    HostNavigasi(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(navController = navController,
        startDestination = DestinasiProgramStudi.route,
        modifier = Modifier) {
        composable(DestinasiProgramStudi.route){
            ProgramStudiScreen(
                navigateToTambahProgramStudi = {navController.navigate(DestinasiEntryProgramStudi.route)},
                navigateToEditProgramStudi = {
                    navController.navigate("${DestinasiEditProgramStudi.route}/${it}")
                }
            )
        }
        composable(DestinasiEntryProgramStudi.route){
            EntryProgramStudiScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = DestinasiEditProgramStudi.routeWithArgs,
            arguments = listOf(navArgument(itemIdArg) {
                type = NavType.IntType
            })
        ) {
            EditProgramStudiScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}