package com.proenca.projectoprogamacaoavancada

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.proenca.projectoprogamacaoavancada.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    var fragment : Fragment?=null
    var itemAtual = R.menu.menu_main
    get() = field
    set(value) {
        if (value != field){
            field=value
            invalidateOptionsMenu()
        }
    }

    private var menu : Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(itemAtual, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val opcaoProcessada : Boolean
        if(fragment is MenuPrincipalFrag){
            opcaoProcessada=(fragment as MenuPrincipalFrag).processaOpcaoMenu(item)
        }else if(fragment is PacienteOpcoesFrag){
            opcaoProcessada = (fragment as PacienteOpcoesFrag).processaOpcaoMenu(item)
        }else if(fragment is AdicionarPacientes){
            opcaoProcessada = (fragment as AdicionarPacientes).processaOpcaoMenu(item)
        }else{
            opcaoProcessada = false
        }

        return if (opcaoProcessada) {
            true
        } else {
            super.onOptionsItemSelected(item)
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun atualizaOpcoes(mostraAlterarEliminar: Boolean) {
        menu!!.findItem(R.id.action_edit).setVisible(mostraAlterarEliminar)
        menu!!.findItem(R.id.action_delete).setVisible(mostraAlterarEliminar)
    }
}