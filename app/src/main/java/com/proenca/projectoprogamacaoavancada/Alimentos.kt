package com.proenca.projectoprogamacaoavancada

import android.content.ContentValues

data class Alimentos(var nome: String,
                     var hidratos : String,
                     var id: Long=-1) {

    fun toContentValues(): ContentValues{
        var valores=ContentValues()
        valores.put(TabelaAlimentos.C_NOME,nome)
        valores.put(TabelaAlimentos.C_VALOR,hidratos)
        return valores
    }
}