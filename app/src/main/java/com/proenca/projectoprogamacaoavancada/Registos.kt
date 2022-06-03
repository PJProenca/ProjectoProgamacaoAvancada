package com.proenca.projectoprogamacaoavancada

import android.content.ContentValues

data class Registos(var data_reg: Long,
                    var glicemia: Long,
                    var insulina: Long,
                    var id_reg:Long=-1) {
    
    fun toContentValues(): ContentValues{
        var valores = ContentValues()
        valores.put(TabelaRegistos.DATA_REG,data_reg)
        valores.put(TabelaRegistos.GLICEMIA,glicemia)
        valores.put(TabelaRegistos.INSULINA,insulina)
        return valores
    }

}