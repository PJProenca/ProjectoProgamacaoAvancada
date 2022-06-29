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

import com.proenca.projectoprogamacaoavancada.databinding.FragmentPacientesOpcoesBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class PacienteOpcoesFrag : Fragment(), LoaderManager.LoaderCallbacks<Cursor>{

    private var _binding: FragmentPacientesOpcoesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPacientesOpcoesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LoaderManager.getInstance(this).initLoader(ID_LOADER_PACIENTES,null,this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(requireContext(),
        myContentProvider.ENDERECO_PACIENTES,
        TabelaPacientes.TODAS_COLUNAS,
        null,
        null,
        TabelaPacientes.C_NOME)



    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        TODO("Not yet implemented")
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("Not yet implemented")
    }

    companion object{
        const val ID_LOADER_PACIENTES = 0
    }
}