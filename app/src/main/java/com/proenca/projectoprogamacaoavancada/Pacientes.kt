package com.proenca.projectoprogamacaoavancada

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class Pacientes( var nome : String,var dataNasc: String, var altura : Long,var id:Long= -1):Serializable{

    fun toContentValues(): ContentValues{
        val valores =  ContentValues()
        valores.put(TabelaPacientes.C_NOME,nome)
        valores.put(TabelaPacientes.C_DATA_NASC,dataNasc)

        valores.put(TabelaPacientes.C_ALTURA,altura)
        return valores
    }

    companion object{
        fun fromCursor(cursor: Cursor) : Pacientes{
            val id_Pos = cursor.getColumnIndex(BaseColumns._ID)
            val nome_pos = cursor.getColumnIndex(TabelaPacientes.C_NOME)
            val dNasc_pos = cursor.getColumnIndex(TabelaPacientes.C_DATA_NASC)
            val altura_pos = cursor.getColumnIndex(TabelaPacientes.C_ALTURA)

            val id = cursor.getLong(id_Pos)
            val nome = cursor.getString(nome_pos)
            val dataNasc = cursor.getString(dNasc_pos)
            val altura = cursor.getLong(altura_pos)
            return Pacientes(nome,dataNasc,altura,id)
        }
    }
}