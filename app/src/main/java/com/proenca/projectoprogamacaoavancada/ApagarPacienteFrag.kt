package com.proenca.projectoprogamacaoavancada

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.proenca.projectoprogamacaoavancada.databinding.FragmentApagarPacienteBinding




/**
 * A simple [Fragment] subclass.
 * Use the [ApagarPacienteFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class ApagarPacienteFrag : Fragment() {
    private var _binding: FragmentApagarPacienteBinding? = null
    private val binding get() = _binding!!

    private lateinit var paciente : Pacientes

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        activity.fragment = this
        activity.itemAtual = R.menu.menu_apagar

        paciente = ApagarPacienteFragArgs.fromBundle(arguments!!).paciente
        binding.textViewNomePacienteApaga.text = paciente.nome
        binding.textViewAlturaApaga.text = paciente.altura.toString()
        binding.textViewDataNascApaga.text = paciente.dataNasc
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApagarPacienteBinding.inflate(inflater, container, false)
        return binding.root
    }


    fun processaOpcaoMenu(item: MenuItem): Boolean{
        return when (item.itemId){
            R.id.action_delete ->{
                true
            }
            R.id.action_cancel ->{
                true
            }
            else ->false
        }
    }

}