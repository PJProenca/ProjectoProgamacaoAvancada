package com.proenca.projectoprogamacaoavancada

import android.content.ContentValues

data class Alimento_Registo(var id_reg: Long,
                       var id_paciente: Long,
                       var id_alimento: Long) {

    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        return valores
    }
}