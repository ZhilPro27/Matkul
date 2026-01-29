package com.example.matkul.view.route

object DestinasiEditMataKuliah: DestinasiNavigasi {
    override val route = "edit_matakuliah"
    override val tittleRes = "Edit Mata Kuliah"
    const val itemIdArg = "idMataKuliah"
    val routeWithArgs = "$route/{$itemIdArg}"
}