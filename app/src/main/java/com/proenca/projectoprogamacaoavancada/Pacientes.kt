package com.proenca.projectoprogamacaoavancada

import android.content.ContentValues
import java.util.*

data class Pacientes(var id:Long, var nome : String,var dataNasc: Date, var altura : Long){

    fun contentValues(): ContentValues{
        val valores =  ContentValues()
        valores.put(TabelaPacientes.C_NOME,nome)
        valores.put(TabelaPacientes.C_DATA_NASC,dataNasc.toString())
        valores.put(TabelaPacientes.C_ALTURA,altura)
        return valores
    }
}