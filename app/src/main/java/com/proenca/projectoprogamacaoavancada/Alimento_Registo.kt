package com.proenca.projectoprogamacaoavancada

import android.content.ContentValues

data class Alimento_Registo(var id_reg: Long=-1,
                       var id_paciente: Long=-1,
                       var id_alimento: Long=-1) {

    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        return valores
    }
}