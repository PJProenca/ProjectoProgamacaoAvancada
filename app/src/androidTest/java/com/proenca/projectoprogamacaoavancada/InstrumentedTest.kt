package com.proenca.projectoprogamacaoavancada

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
    @Test
    fun appContext() = InstrumentationRegistry.getInstrumentation().targetContext

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

}