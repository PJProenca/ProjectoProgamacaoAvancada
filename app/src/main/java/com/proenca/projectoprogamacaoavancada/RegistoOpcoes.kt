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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.proenca.projectoprogamacaoavancada.databinding.FragmentRegistoOpcoesBinding

class RegistoOpcoes : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    var registoSelec: Registos? = null

        get() = field
        set(value) {
            if (value != field) {
                field = value
                (requireActivity() as MainActivity).atualizaOpcoes(field != null)
            }
        }

    private var _binding: FragmentRegistoOpcoesBinding? = null
    private val binding get() = _binding!!
    private var adapterRegisto: AdapterRegisto? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistoOpcoesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LoaderManager.getInstance(this).initLoader(ID_LOADER_REGISTOS, null, this)
        adapterRegisto = AdapterRegisto(this)
        binding.recyclerViewRegistos.adapter = adapterRegisto
        binding.recyclerViewRegistos.layoutManager = LinearLayoutManager(requireContext())
        val activity = requireActivity() as MainActivity
        activity.fragment = this
        activity.itemAtual = R.menu.menu_barra

    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                val acao =
                    RegistoOpcoesDirections.actionRegistoOpcoesToApagarRegisto(registoSelec!!)
                findNavController().navigate(acao)
                true
            }
            R.id.action_add ->{
                val acao = RegistoOpcoesDirections.actionRegistoOpcoesToCalcularFrag()
                findNavController().navigate(acao)
                true
            }
            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(
            requireContext(),
            myContentProvider.ENDERECO_REGISTOS,
            TabelaRegistos.TODAS_COLUNAS,
            null,
            null,
            TabelaRegistos.CAMPO_ID
        )

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterRegisto!!.cursor=data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {

        if(_binding == null) return
        adapterRegisto!!.cursor = null
    }

    companion object {
        const val ID_LOADER_REGISTOS = 0
    }
}