package com.proenca.projectoprogamacaoavancada

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaAlimento_Registo(db: SQLiteDatabase):TabelasBD(db,NOME) {

     override fun cria(){

        db.execSQL("CREATE TABLE $NOME ( $REG_ID_REGISTO INTEGER NOT NULL," +
                "$REG_ID_PACIENTE INTEGER NOT NULL,"+
               "$REG_ID_ALIMENTO INTEGER NOT NULL,"+
                " FOREIGN KEY ($REG_ID_REGISTO) REFERENCES ${TabelaRegistos.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT," +
               "FOREIGN KEY ($REG_ID_PACIENTE) REFERENCES ${TabelaPacientes.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT," +
               "FOREIGN KEY ($REG_ID_ALIMENTO) REFERENCES ${TabelaAlimentos.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    companion object{

        const val NOME= "Alimento_Registo"
        const val REG_ID_ALIMENTO="Registo_Id_Alimento"
        const val REG_ID_PACIENTE="Registo_Id_Paciente"
        const val REG_ID_REGISTO="Registo_Id_Registo"
    }
}