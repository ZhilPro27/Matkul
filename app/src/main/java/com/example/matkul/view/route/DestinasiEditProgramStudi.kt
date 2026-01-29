package com.example.matkul.view.route

object DestinasiEditProgramStudi: DestinasiNavigasi {
    override val route = "edit_programStudi"
    override val tittleRes = "Edit Program Studi"
    const val itemIdArg = "idSiswa"
    val routeWithArgs = "$route/{$itemIdArg}"
}