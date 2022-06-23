package com.proenca.projectoprogamacaoavancada

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Registos(var data_reg:String,
                    var glicemia: Long,
                    var insulina: Long,
                    var peso: Double,
                    var id_paciente: Long,
                    var id:Long=-1) {

    fun toContentValues(): ContentValues{
        val valores = ContentValues()
        valores.put(TabelaRegistos.DATA_REG,data_reg)
        valores.put(TabelaRegistos.GLICEMIA,glicemia)
        valores.put(TabelaRegistos.INSULINA,insulina)
        valores.put(TabelaRegistos.PESO,peso)
        valores.put(TabelaRegistos.ID_PACIENTE,id_paciente)
        return valores
    }

    companion object{
        fun fromCursor(cursor: Cursor):Registos{
            val id_reg_pos = cursor.getColumnIndex(BaseColumns._ID)
            val id_paciente_pos= cursor.getColumnIndex(TabelaRegistos.ID_PACIENTE)
            val data_reg_pos = cursor.getColumnIndex(TabelaRegistos.DATA_REG)
            val glicemia_pos = cursor.getColumnIndex(TabelaRegistos.GLICEMIA)
            val insulina_pos = cursor.getColumnIndex(TabelaRegistos.INSULINA)
            val peso_pos = cursor.getColumnIndex(TabelaRegistos.PESO)

            val id_reg = cursor.getLong(id_reg_pos)
            val id_paciente = cursor.getLong(id_paciente_pos)
            val data_reg = cursor.getString(data_reg_pos)
            val glicemia = cursor.getLong(glicemia_pos)
            val insulina = cursor.getLong(insulina_pos)
            val peso = cursor.getDouble(peso_pos)
            return Registos(data_reg,glicemia,insulina,peso,id_paciente,id_reg)
        }
    }
}