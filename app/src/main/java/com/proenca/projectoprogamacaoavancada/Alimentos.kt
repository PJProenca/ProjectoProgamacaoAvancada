package com.proenca.projectoprogamacaoavancada

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Alimentos(var nome: String,
                     var hidratos : Long,
                     var id: Long=-1) {

    fun toContentValues(): ContentValues{
        val valores=ContentValues()
        valores.put(TabelaAlimentos.C_NOME,nome)
        valores.put(TabelaAlimentos.C_VALOR,hidratos)
        return valores
    }

    companion object{
        fun fromCursor(cursor: Cursor):Alimentos{
            val id_pos = cursor.getColumnIndex(BaseColumns._ID)
            val nome_pos = cursor.getColumnIndex(TabelaAlimentos.C_NOME)
            val valor_pos = cursor.getColumnIndex(TabelaAlimentos.C_VALOR)

            val id = cursor.getLong(id_pos)
            val nome = cursor.getString(nome_pos)
            val valor = cursor.getLong(valor_pos)
            return Alimentos(nome,valor,id)
        }
    }
}