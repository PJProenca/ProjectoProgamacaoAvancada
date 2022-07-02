package com.proenca.projectoprogamacaoavancada

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.proenca.projectoprogamacaoavancada.databinding.FragmentMenuPrincipalBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MenuPrincipalFrag : Fragment() {

    private var _binding: FragmentMenuPrincipalBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMenuPrincipalBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonPaciente.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        binding.buttonAlimentos.setOnClickListener {
            findNavController().navigate(R.id.action_MenuPrincipalFrag_to_adicionarPacientes)
        }

        (activity as MainActivity).itemAtual = R.menu.menu_main
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}