package com.proenca.projectoprogamacaoavancada

import android.content.ContentValues

data class Registos(var data_reg:String,
                    var glicemia: Long,
                    var insulina: Long,
                    var peso: Double,
                    var id_paciente: Long,
                    var id:Long=-1) {

    fun toContentValues(): ContentValues{
        var valores = ContentValues()
        valores.put(TabelaRegistos.DATA_REG,data_reg)
        valores.put(TabelaRegistos.GLICEMIA,glicemia)
        valores.put(TabelaRegistos.INSULINA,insulina)
        valores.put(TabelaRegistos.PESO,peso)
        valores.put(TabelaRegistos.ID_PACIENTE,id_paciente)
        return valores
    }
}