package com.proenca.projectoprogamacaoavancada

import android.icu.text.SimpleDateFormat
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
import com.proenca.projectoprogamacaoavancada.databinding.FragmentAdicionaEditaPacientesBinding
import java.util.*


class AdicionaEditaPacientes : Fragment()  {
    private var _binding: FragmentAdicionaEditaPacientesBinding? = null

    private val binding get() = _binding!!

    private var paciente: Pacientes? = null
    var dataNasc: Long = 0L
    var data: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAdicionaEditaPacientesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as MainActivity
        activity.fragment = this
        activity.itemAtual = R.menu.menu_guardar


        if (arguments != null) {
            paciente = AdicionaEditaPacientesArgs.fromBundle(arguments!!).paciente
            if (paciente != null) {
                binding.editTextNomePaciente.setText(paciente!!.nome)
                binding.editTextAltura.setText(paciente!!.altura.toString())
                val getData = paciente!!.dataNasc
                val dateFormat = SimpleDateFormat("dd-MM-yyy")
                val dataFormatada = dateFormat.format(getData)
                val dateSplit = dataFormatada.split("-")
                val ano = dateSplit[2]
                val mes = dateSplit[1]
                val dia = dateSplit[0]
                val currentDate = Calendar.getInstance()
                binding.datePicker.init(
                    ano.toInt(),
                    mes.toInt() - 1,
                    dia.toInt()
                ) { view, ano, mes, dia ->
                    currentDate.set(ano, mes, dia)
                    dataNasc = currentDate.timeInMillis
                }
            } else {
                val picker = binding.datePicker
                val currentDate = Calendar.getInstance()
                picker.init(
                    currentDate.get(Calendar.YEAR),
                    currentDate.get(Calendar.MONTH),
                    currentDate.get(Calendar.DAY_OF_MONTH)

                ) { view, ano, mes, dia ->
                    //val mes = mes
                    currentDate.set(ano, mes, dia)
                    dataNasc = currentDate.timeInMillis
                }
            }
        }
    }


    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                guardarPaciente()
                true
            }
            R.id.action_cancel -> {
                voltarOpcoesPacientes()
                true
            }
            else -> false
        }
    }

    private fun voltarOpcoesPacientes() {
        findNavController().navigate(R.id.action_AdicionaEditaPacientes_to_PacienteOpcoesFrag)
    }

    private fun guardarPaciente() {
        val nome = binding.editTextNomePaciente.text.toString()
        if (nome.isBlank()) {
            binding.editTextNomePaciente.error = getString(R.string.campoObrigatorio)
            binding.editTextNomePaciente.requestFocus()
            return
        }

        val altura = binding.editTextAltura.text.toString()
        if (altura.isBlank()) {
            binding.editTextAltura.error = getString(R.string.campoObrigatorio)
            binding.editTextAltura.requestFocus()
            return
        }


        val pacienteAdicionado =
            if (paciente == null) {
                adicionaPaciente(nome, dataNasc, altura)
            } else {
                editaPaciente(nome, dataNasc, altura)
            }

//

        if (pacienteAdicionado != null) {
            Toast.makeText(
                requireContext(),
                getString(R.string.PacienteaAddSucesso),
                Toast.LENGTH_LONG
            ).show()
            voltarOpcoesPacientes()
        } else {
            Snackbar.make(
                binding.editTextNomePaciente,
                getString(R.string.PacienteAddErro),
                Snackbar.LENGTH_INDEFINITE
            ).show()
        }

    }

    private fun adicionaPaciente(nome: String, data: Long, altura: String): Boolean {
        val paciente = Pacientes(nome, data, altura.toLong())

        val endereco = requireActivity().contentResolver.insert(
            myContentProvider.ENDERECO_PACIENTES,
            paciente.toContentValues()
        )
        return endereco != null
    }

    private fun editaPaciente(nome: String, data: Long, altura: String): Any {
        val paciente = Pacientes(nome, data, altura.toLong())
        val enderecoPaciente =
            Uri.withAppendedPath(myContentProvider.ENDERECO_PACIENTES, "${this.paciente!!.id}")
        val endereco = requireActivity().contentResolver.update(
            enderecoPaciente,
            paciente.toContentValues(),
            null,
            null
        )
        return endereco == 1
    }
}