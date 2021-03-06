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
import com.proenca.projectoprogamacaoavancada.databinding.FragmentPacientesOpcoesBinding


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class PacienteOpcoesFrag : Fragment(), LoaderManager.LoaderCallbacks<Cursor>{

    var pacienteSelec:Pacientes ?=null
    get() = field
    set(value) {
        if (value!=field){
            field=value
            (requireActivity() as MainActivity).atualizaOpcoes(field!=null)
        }
    }


    private var _binding: FragmentPacientesOpcoesBinding? = null
    private var adapterPacientes :AdapaterPacientes? =null


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
        adapterPacientes = AdapaterPacientes(this)
        binding.recyclerViewPacientes.adapter = adapterPacientes
        binding.recyclerViewPacientes.layoutManager = LinearLayoutManager(requireContext())

        val activity = requireActivity() as MainActivity
        activity.fragment = this
        activity.itemAtual = R.menu.menu_barra

    }




    fun processaOpcaoMenu(item: MenuItem): Boolean{
        return when(item.itemId){
            R.id.action_add -> {
                val acao = PacienteOpcoesFragDirections.actionPacienteOpcoesFragToAdicionaEditaPacientes()
                findNavController().navigate(acao)
                (activity as MainActivity).alterarTitulo(R.string.adicionaPacienteTitulo)
                true
            }
            R.id.action_edit ->{
                val acao = PacienteOpcoesFragDirections.actionPacienteOpcoesFragToAdicionaEditaPacientes(pacienteSelec)
                findNavController().navigate(acao)
                (activity as MainActivity).alterarTitulo(R.string.editarPacienteTitulo)
                true
            }
            R.id.action_delete ->{
                val acao = PacienteOpcoesFragDirections.actionPacienteOpcoesFragToApagarPacienteFrag(pacienteSelec!!)
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
            myContentProvider.ENDERECO_PACIENTES,
            TabelaPacientes.TODAS_COLUNAS,
            null,
            null,
            TabelaPacientes.C_NOME
        )



    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {

        adapterPacientes!!.cursor = data
//        binding.spinnerPacientes.adapter=SimpleCursorAdapter(requireContext(),
//        android.R.layout.simple_list_item_1,
//        data, arrayOf(TabelaPacientes.C_NOME),
//        intArrayOf(android.R.id.text1),0
//        )
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {

        if(_binding == null) return
        adapterPacientes!!.cursor = null
//        binding.spinnerPacientes.adapter=null

    }

    companion object{
        const val ID_LOADER_PACIENTES = 0

    }
}