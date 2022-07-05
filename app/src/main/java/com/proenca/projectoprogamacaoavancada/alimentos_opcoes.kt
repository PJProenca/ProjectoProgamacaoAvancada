package com.proenca.projectoprogamacaoavancada

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.proenca.projectoprogamacaoavancada.databinding.FragmentAlimentosOpcoesBinding


class alimentos_opcoes : Fragment() , LoaderManager.LoaderCallbacks<Cursor>{

    private var _binding: FragmentAlimentosOpcoesBinding?=null

    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LoaderManager.getInstance(this).initLoader(ID_LOADER_ALIMENTOS, null, this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAlimentosOpcoesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(requireContext(),
            myContentProvider.ENDERECO_ALIMENTOS,
            TabelaAlimentos.TODAS_COLUNAS,
            null,
            null,
            TabelaAlimentos.C_NOME)


    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        //TODO("Not yet implemented")
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        //TODO("Not yet implemented")
    }

    companion object{
        const val ID_LOADER_ALIMENTOS = 0
    }


}