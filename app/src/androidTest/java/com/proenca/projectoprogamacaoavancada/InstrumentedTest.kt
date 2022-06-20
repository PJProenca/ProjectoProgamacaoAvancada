package com.proenca.projectoprogamacaoavancada

import android.database.sqlite.SQLiteDatabase
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class InstrumentedTest {
    private fun appContext() = InstrumentationRegistry.getInstrumentation().targetContext

    private fun getWritableDatabase(): SQLiteDatabase {
        val openHelper = BD_Open_Helper(appContext())
        return openHelper.writableDatabase
    }

    private fun inserirPacientes(db: SQLiteDatabase, pacientes: Pacientes) {
        pacientes.id = TabelaPacientes(db).insert(pacientes.toContentValues())
        assertNotEquals(-1, pacientes.id)
    }

    private fun inserirAlimentos(db: SQLiteDatabase, alimentos: Alimentos) {
        alimentos.id = TabelaAlimentos(db).insert(alimentos.toContentValues())
        assertNotEquals(-1, alimentos.id)
    }

    @Before

    fun apagarBD(){
        appContext().deleteDatabase(BD_Open_Helper.NOME)
    }

    @Test

    fun abrirBD(){
        val db = BD_Open_Helper(appContext()).readableDatabase
        assertTrue(db.isOpen)
        db.close()
    }

    @Test
    fun consegueInserirPaciente() {
        val db = getWritableDatabase()

        inserirPacientes(db, Pacientes("Paulo Proença","18-12-1985",180))
        inserirPacientes(db, Pacientes("Paulo Jorge","18-12-1985",180))

        db.close()
    }

    @Test

    fun consegueInserirAlimentos() {
        val db = getWritableDatabase()
        inserirAlimentos(db, Alimentos("Pão",133))
        db.close()
    }
}