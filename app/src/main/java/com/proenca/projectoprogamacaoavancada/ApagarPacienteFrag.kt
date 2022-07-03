package com.proenca.projectoprogamacaoavancada

import android.app.AlertDialog
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
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
                confirmaApagarCliente()
                true
            }
            R.id.action_cancel ->{
                voltarOpcoesPacientes()
                true
            }
            else ->false
        }
    }

    private fun confirmaApagarCliente(){
        val alert = AlertDialog.Builder(requireContext())
        alert.setTitle(R.string.ApagarPaciente)
        alert.setMessage(R.string.Confirmar_apagar_paciente)
        alert.setNegativeButton(R.string.Cancel_apagar,DialogInterface.OnClickListener { dialog, wich ->  })
        alert.setPositiveButton(R.string.apagar_confirmacao,DialogInterface.OnClickListener { dialog, wich -> apagarPaciente()  })
        alert.show()
    }

    private fun apagarPaciente() {
        val endereco= Uri.withAppendedPath(myContentProvider.ENDERECO_PACIENTES, "${paciente.id}")
        val pacienteApagado = requireActivity().contentResolver.delete(endereco,null,null)

        if(pacienteApagado == 1){
            Toast.makeText(requireContext(),R.string.delete_sucess_paciente,Toast.LENGTH_LONG).show()
            voltarOpcoesPacientes()
        }else{
            Snackbar.make(binding.textViewNomePacienteApaga,R.string.apagar_paciente_erro,Snackbar.LENGTH_INDEFINITE).show()
        }

    }

    private fun voltarOpcoesPacientes() {
        val acao = ApagarPacienteFragDirections.actionApagarPacienteFragToPacienteOpcoesFrag()
        findNavController().navigate(acao)
    }

}