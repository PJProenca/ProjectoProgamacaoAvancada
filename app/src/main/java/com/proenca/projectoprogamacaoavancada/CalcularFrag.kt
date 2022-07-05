package com.proenca.projectoprogamacaoavancada

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.proenca.projectoprogamacaoavancada.databinding.FragmentCalcularBinding


class CalcularFrag : Fragment(),LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentCalcularBinding?=null
    private val binding get() = _binding!!
    private var loader:CursorLoader?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LoaderManager.getInstance(this).initLoader(ID_LOADER_PACIENTES,null,this)
        LoaderManager.getInstance(this).initLoader(ID_LOADER_ALIMENTOS,null,this)

        val activity = requireActivity() as MainActivity
        activity.fragment = this
        activity.itemAtual = R.menu.calcular

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCalcularBinding.inflate(inflater,container,false)
        return binding.root
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean{
        return when (item.itemId){
            R.id.action_calc ->{
                true
            }
            else ->false
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        //TODO("Not yet implemented")
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        if(id == ID_LOADER_PACIENTES){
            loader= CursorLoader(requireContext(),myContentProvider.ENDERECO_PACIENTES,
            TabelaPacientes.TODAS_COLUNAS,
            null,
            null,
            TabelaPacientes.C_NOME)
        }else{
            loader= CursorLoader(requireContext(),myContentProvider.ENDERECO_ALIMENTOS,
                TabelaPacientes.TODAS_COLUNAS,
                null,
                null,
                TabelaAlimentos.C_NOME)
        }
        return loader as CursorLoader
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        //TODO("Not yet implemented")
    }

    companion object{
        const val ID_LOADER_PACIENTES = 0
        const val ID_LOADER_ALIMENTOS = 1
    }

}