package com.proenca.projectoprogamacaoavancada

import android.content.ContentValues

data class Alimento_Registo(var id_reg: Long,
                       var id_paciente: Long,
                       var id_alimento: Long) {

    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        valores.put(TabelaAlimento_Registo.REG_ID_REGISTO,id_reg)
        valores.put(TabelaAlimento_Registo.REG_ID_PACIENTE,id_paciente)
        valores.put(TabelaAlimento_Registo.REG_ID_ALIMENTO,id_alimento)
        return valores
    }
}