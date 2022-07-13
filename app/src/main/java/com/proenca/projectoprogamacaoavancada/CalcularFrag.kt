package com.proenca.projectoprogamacaoavancada

import android.app.AlertDialog
import android.content.DialogInterface
import android.database.Cursor
import android.os.Bundle
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.google.android.material.snackbar.Snackbar
import com.proenca.projectoprogamacaoavancada.databinding.FragmentCalcularBinding
import java.util.*
import kotlin.math.pow


class CalcularFrag : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentCalcularBinding? = null
    private val binding get() = _binding!!
    private var loader: CursorLoader? = null

    private var valorTotalAlimento: Int = 0
    private var valorCalculado: Int = 0
    private var nomePaciente: String? = ""
    private var altura: String? = ""
    private var pesoAlimento: String? = ""

    //private var currentDate:Long?=0L
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LoaderManager.getInstance(this).initLoader(ID_LOADER_PACIENTES, null, this)
        LoaderManager.getInstance(this).initLoader(ID_LOADER_ALIMENTOS, null, this)

        val activity = requireActivity() as MainActivity
        activity.fragment = this
        activity.itemAtual = R.menu.menu_calcular
        binding.calcAddAlimentoButton.setOnClickListener {

            valorCalculado = calcularValorHidratos()

        }

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
        _binding = FragmentCalcularBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_calc -> {
                mostraInsulinaAdministrar()
                true
            }
            else -> false
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        //TODO("Not yet implemented")
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        if (id == ID_LOADER_PACIENTES) {
            loader = CursorLoader(
                requireContext(), myContentProvider.ENDERECO_PACIENTES,
                TabelaPacientes.TODAS_COLUNAS,
                null,
                null,
                TabelaPacientes.C_NOME
            )
        } else if (id == ID_LOADER_ALIMENTOS) {
            loader = CursorLoader(
                requireContext(), myContentProvider.ENDERECO_ALIMENTOS,
                TabelaAlimentos.TODAS_COLUNAS,
                null,
                null,
                TabelaAlimentos.C_NOME
            )
        }
        return loader as CursorLoader
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {

        if (loader.id == ID_LOADER_PACIENTES) {
            binding.spinnerCalcularPaciente.adapter = SimpleCursorAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                data, arrayOf(TabelaPacientes.C_NOME),
                intArrayOf(android.R.id.text1), 0
            )
        } else if (loader.id == ID_LOADER_ALIMENTOS) {
            binding.spinnerCalcularAlimentos.adapter = SimpleCursorAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                data, arrayOf(TabelaAlimentos.C_NOME),
                intArrayOf(android.R.id.text1), 0
            )
        }
    }

    companion object {
        const val ID_LOADER_PACIENTES = 0
        const val ID_LOADER_ALIMENTOS = 1
    }

    private fun guardarRegisto(
        dataReg: Long,
        unidadeAdministrar: Long,
        pesoPaciente: Double,
        spinPacientesSelect: Long,
        glicemia: Long
    ) {

        val regAdicionado = adicionaRegisto(
            dataReg,
            glicemia,
            unidadeAdministrar,
            pesoPaciente,
            spinPacientesSelect
        )
        if (regAdicionado != null) {
            Toast.makeText(requireContext(), R.string.SucessRegAdd, Toast.LENGTH_LONG)
                .show()
        } else {
            Snackbar.make(
                binding.textSelecPaciente,
                R.string.ErrorRegAdd,
                Snackbar.LENGTH_INDEFINITE
            ).show()
        }
    }

    private fun mostraInsulinaAdministrar() {

        val currentDate = Calendar.getInstance()
        val dataReg = currentDate.timeInMillis
        val spinPacientesSelect = binding.spinnerCalcularPaciente.selectedItemId
        if (spinPacientesSelect.toString().isBlank()) {
            binding.textSelecPaciente.error = getString(R.string.campoObrigatorio)
            binding.spinnerCalcularPaciente.requestFocus()
            return
        }
        val pesoPaciente = binding.TextInputPeso.text.toString()
        if (pesoPaciente.isBlank()) {
            binding.TextInputPeso.error = getString(R.string.campoObrigatorio)
            binding.TextInputPeso.requestFocus()
            return
        }
        val glicemia = binding.TextInputGlicemia.text.toString()
        if (glicemia.isBlank()) {
            binding.TextInputGlicemia.error = getString(R.string.campoObrigatorio)
            binding.TextInputGlicemia.requestFocus()
            return
        }

        val pacientesValues = requireActivity().contentResolver.query(
            myContentProvider.ENDERECO_PACIENTES,
            TabelaPacientes.TODAS_COLUNAS,
            "${BaseColumns._ID} =" + spinPacientesSelect,
            null,
            null
        )
        if (pacientesValues != null && pacientesValues.moveToFirst()) {
            nomePaciente =
                pacientesValues.getString(pacientesValues.getColumnIndex(TabelaPacientes.C_NOME))
            altura =
                pacientesValues.getString(pacientesValues.getColumnIndex(TabelaPacientes.C_ALTURA))
        }

        val indiceSensibilidade =
            indiceDeSensibilidade(pesoPaciente.toDouble(), altura!!.toDouble())
        val unidadePorGlicemia = (glicemia.toLong() - 120) / indiceSensibilidade!!.toLong()
        val unidadeAdministrar = unidadePorGlicemia + valorCalculado

        val alert = AlertDialog.Builder(requireContext())
        alert.setTitle("UI Calculadas")
        alert.setMessage("Unidades a Administrar: " + unidadeAdministrar)
        alert.setPositiveButton("Confirmar",
            DialogInterface.OnClickListener { dialog, wich ->
                guardarRegisto(
                    dataReg,
                    unidadeAdministrar,
                    pesoPaciente.toDouble(),
                    spinPacientesSelect,
                    glicemia.toLong()
                )
            })
        alert.show()
    }

    private fun calcularValorHidratos(): Int {
        var valor: String? = ""

        val spinAlimentosSelect = binding.spinnerCalcularAlimentos.selectedItemId
        val alimentosValues = requireActivity().contentResolver.query(
            myContentProvider.ENDERECO_ALIMENTOS,
            TabelaAlimentos.TODAS_COLUNAS,
            "${BaseColumns._ID}=" + spinAlimentosSelect,
            null,
            null
        )
        if (alimentosValues != null && alimentosValues.moveToFirst()) {

            valor =
                alimentosValues.getString(alimentosValues.getColumnIndex(TabelaAlimentos.C_VALOR))

        }
        pesoAlimento = binding.TextInputPesoAlim.text.toString()

        var gramasHidratos = (valor!!.toDouble() * pesoAlimento!!.toDouble()) / 100
        var porcao = (gramasHidratos / 12) + 0.5
        valorTotalAlimento += porcao!!.toInt()


        return valorTotalAlimento!!
    }


    private fun indiceDeSensibilidade(peso: Double, altura: Double): Int? {
        val alturaEmMtrs = altura / 100
        val imcValue = peso / alturaEmMtrs.pow(2)
        return when ((Math.round(imcValue * 10.0) / 10.0)) {
            in 0.0..16.4 -> 100
            in 16.5..18.4 -> 80
            in 18.5..24.9 -> 65
            in 25.0..34.9 -> 50
            in 35.0..39.9 -> 35
            in 40.0..Double.MAX_VALUE -> 15
            else -> null
        }
    }


    private fun adicionaRegisto(
        data_reg: Long,
        glicemia: Long,
        insulina: Long,
        peso: Double,
        id_paciente: Long
    ): Boolean {
        val registo = Registos(data_reg, glicemia, insulina, peso, id_paciente)
        val endereco = requireActivity().contentResolver.insert(
            myContentProvider.ENDERECO_REGISTOS,
            registo.toContentValues()
        )
        return endereco != null
    }

}