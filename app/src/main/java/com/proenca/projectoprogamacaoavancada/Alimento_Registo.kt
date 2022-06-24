package com.proenca.projectoprogamacaoavancada

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

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

    companion object{
        fun fromCursor(cursor: Cursor):Alimento_Registo{
            val id_reg_pos = cursor.getColumnIndex(TabelaAlimento_Registo.REG_ID_ALIMENTO)
            val id_paciente_pos= cursor.getColumnIndex(TabelaAlimento_Registo.REG_ID_PACIENTE)
            val id_alimento_pos = cursor.getColumnIndex(TabelaAlimento_Registo.REG_ID_ALIMENTO)


            val id_reg = cursor.getLong(id_reg_pos)
            val id_paciente = cursor.getLong(id_paciente_pos)
            val id_alimento = cursor.getLong(id_alimento_pos)

            return Alimento_Registo(id_reg,id_paciente,id_alimento)
        }
    }
}