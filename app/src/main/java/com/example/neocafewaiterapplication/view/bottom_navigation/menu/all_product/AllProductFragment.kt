package com.example.neocafewaiterapplication.view.bottom_navigation.menu.all_product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.FragmentAllProductBinding
import com.example.neocafewaiterapplication.view.root.BaseFragment
import com.example.neocafewaiterapplication.view.utils.gone
import com.example.neocafewaiterapplication.view.utils.navigate
import com.example.neocafewaiterapplication.view.utils.recycler_adapter.AllProductsRecyclerAdapter
import com.example.neocafewaiterapplication.viewModel.menu_vm.AllProductViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class AllProductFragment : BaseFragment<FragmentAllProductBinding>() {

    private val recyclerAdapter by lazy {AllProductsRecyclerAdapter(null)}
    private val args:AllProductFragmentArgs by navArgs()
    val viewModel: AllProductViewModel by viewModel()
    private val mapOfCategory = mutableMapOf(
        "Выпечка" to R.id.bakery, "Кофе" to R.id.coffee, "Чай" to R.id.tea,
        "Напитки" to R.id.drinks, "Десерты" to R.id.desserts
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        setUpAppBar()

        with(binding){
            val viewId = mapOfCategory[args.category] // срабатывает при передачи категории
            if (viewId != null) {
                binding.chipGroup.check(viewId) // enable данной категории
                recyclerSetList(viewId)
            }

            chipGroup.setOnCheckedChangeListener { _, checkedId ->
                recyclerSetList(checkedId) //слушатель изменений chip
            }
        }
    }

    private fun setUpRecycler() {
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerAdapter
        }
    }

    private fun recyclerSetList(checkedId: Int) {
        val checkedText = when (checkedId) { // по его id я достаю его имя
            R.id.bakery -> "Выпечка"
            R.id.coffee -> "Кофе"
            R.id.tea -> "Чай"
            R.id.drinks -> "Напитки"
            R.id.desserts -> "Десерты"
            else -> ""
        }
        viewModel.list.observe(viewLifecycleOwner, {
            recyclerAdapter.setList(viewModel.sort(checkedText, it), checkedText)
            binding.progress.gone()
        })
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentAllProductBinding {
        return FragmentAllProductBinding.inflate(inflater)
    }

    override fun setUpAppBar() {
        with(binding.include){
            notification.setOnClickListener { navigate(AllProductFragmentDirections.actionAllProductFragmentToNotificationFragment()) }
            user.setOnClickListener { navigate(AllProductFragmentDirections.actionAllProductFragmentToUserFragment3()) }
        }

    }


}