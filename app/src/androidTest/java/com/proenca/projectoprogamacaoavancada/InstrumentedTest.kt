package com.proenca.projectoprogamacaoavancada

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
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

    private fun inserirRegisto(db:SQLiteDatabase,registo : Registos){
        registo.id = TabelaRegistos(db).insert(registo.toContentValues())
        assertNotEquals(-1,registo.id)
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


        db.close()
    }

    @Test

    fun consegueInserirAlimentos() {
        val db = getWritableDatabase()
        inserirAlimentos(db, Alimentos("Pão",133))
        inserirAlimentos(db, Alimentos("leite",171))
        db.close()
    }
    @Test

    fun consegueInserirRegistos() {
        val db = getWritableDatabase()

        val paciente = Pacientes("Paulo J. M.","18-12-1985",180)
        inserirPacientes(db,paciente)

        inserirRegisto(db, Registos("20/06/2022",145,15,75.5,paciente.id))
        db.close()
    }


    @Test

    fun consegueAlterarPaciente(){
        val db = getWritableDatabase()
        val paciente = Pacientes("PJ","15/6/1991",185)
        inserirPacientes(db,paciente)

        paciente.nome="Paulo"
        val alterarPaciente=TabelaPacientes(db).update(paciente.toContentValues(),
            "${BaseColumns._ID}=?",
                arrayOf("${paciente.id}"))
        assertEquals(1,alterarPaciente)
        db.close()
    }
    @Test

    fun consegueAlterarAlimentos(){
        val db = getWritableDatabase()
        val alimento = Alimentos("Bolacha", 145)
        inserirAlimentos(db,alimento)

        alimento.nome="Arroz"
        val alterarAlimento=TabelaAlimentos(db).update(alimento.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf("${alimento.id}"))
        assertEquals(1,alterarAlimento)
        db.close()
    }


    @Test
    fun consegueEliminarPaciente() {
        val db = getWritableDatabase()

        val paciente = Pacientes("Paulo","15/06/1993",173)
        inserirPacientes(db, paciente)

        val registosEliminados = TabelaPacientes(db).delete(
            "${BaseColumns._ID}=?",
            arrayOf("${paciente.id}")
        )

        assertEquals(1, registosEliminados)

        db.close()
    }
    @Test
    fun consegueEliminarRegisto() {
        val db = getWritableDatabase()
        val paciente = Pacientes("Paulo J. M.","18-12-1985",180)
        inserirPacientes(db,paciente)
        val registo = Registos("23/06/2022",185,15,57.0,paciente.id)
        inserirRegisto(db, registo)

        val registosEliminados = TabelaRegistos(db).delete(
            "${BaseColumns._ID}=?",
            arrayOf("${registo.id}")
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueEliminarAlimentos() {
        val db = getWritableDatabase()
        val alimento = Alimentos("Feijão",180)
        inserirAlimentos(db,alimento)


        val registosEliminados = TabelaAlimentos(db).delete(
            "${BaseColumns._ID}=?",
            arrayOf("${alimento.id}")
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerPacientes() {
        val db = getWritableDatabase()

        val paciente = Pacientes("Paulo Proença","15/13/1989",177)
        inserirPacientes(db, paciente)

        val cursor = TabelaPacientes(db).query(
            TabelaPacientes.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf("${paciente.id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val pacienteBD = Pacientes.fromCursor(cursor)
        assertEquals(paciente, pacienteBD)

        db.close()
    }


    @Test
    fun consegueLerAlimentos() {
        val db = getWritableDatabase()

        val alimento = Alimentos("Massa",75)
        inserirAlimentos(db, alimento)

        val cursor = TabelaAlimentos(db).query(
            TabelaAlimentos.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf("${alimento.id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val alimentosBD = Alimentos.fromCursor(cursor)
        assertEquals(alimento, alimentosBD)

        db.close()
    }

    @Test
    fun consegueLerRegistos() {
        val db = getWritableDatabase()

        val paciente = Pacientes("Paulo J. M.","18-12-1985",180)
        inserirPacientes(db,paciente)

        val registo = Registos("23/06/2022",155,11,57.6,paciente.id)
        inserirRegisto(db, registo)

        val cursor = TabelaRegistos(db).query(
            TabelaRegistos.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf("${registo.id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        val registoBD = Registos.fromCursor(cursor)
        assertEquals(registo, registoBD)

        db.close()
    }
}