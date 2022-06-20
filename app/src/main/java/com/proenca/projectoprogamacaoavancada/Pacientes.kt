package com.proenca.projectoprogamacaoavancada

import android.content.ContentValues
import java.util.*

data class Pacientes( var nome : String,var dataNasc: String, var altura : Long,var id:Long= -1){

    fun toContentValues(): ContentValues{
        val valores =  ContentValues()
        valores.put(TabelaPacientes.C_NOME,nome)
        valores.put(TabelaPacientes.C_DATA_NASC,dataNasc)
        valores.put(TabelaPacientes.C_ALTURA,altura)
        return valores
    }
}