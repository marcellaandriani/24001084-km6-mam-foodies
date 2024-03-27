package com.example.foodiesapp.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodiesapp.R
import com.example.foodiesapp.data.datasource.category.DummyCategoryDataSource
import com.example.foodiesapp.data.datasource.menu.DummyMenuDataSource
import com.example.foodiesapp.data.model.Category
import com.example.foodiesapp.data.model.Menu
import com.example.foodiesapp.data.repository.CategoryRepositoryImpl
import com.example.foodiesapp.data.repository.MenuRepositoryImpl
import com.example.foodiesapp.databinding.FragmentHomeBinding
import com.example.foodiesapp.presentation.detailfood.DetailFoodActivity
import com.example.foodiesapp.presentation.home.adapter.CategoryAdapter
import com.example.foodiesapp.presentation.home.adapter.MenuAdapter
import com.example.foodiesapp.utils.GenericViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var menuAdapter: MenuAdapter
    private var isUsingGridMode : Boolean = true // Set default mode to grid
    private var gridLayoutManager: GridLayoutManager? = null
    private val viewModel: HomeViewModel by viewModels {
        val menuDataSource = DummyMenuDataSource()
        val menuRepository = MenuRepositoryImpl(menuDataSource)
        val categoryDataSource = DummyCategoryDataSource()
        val categoryRepository = CategoryRepositoryImpl(categoryDataSource)
        GenericViewModelFactory.create(HomeViewModel(categoryRepository, menuRepository))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        setClickAction()
        bindCategory(viewModel.getCategories())
        bindMenu(viewModel.getMenus())
        setButtonImage(isUsingGridMode)
    }


    private fun setupRecyclerViews() {
        categoryAdapter = CategoryAdapter{category ->
            Toast.makeText(requireContext(), category.name, Toast.LENGTH_SHORT).show()}
        binding.layoutCategory.rvCategory.adapter = categoryAdapter

        menuAdapter = MenuAdapter(object : MenuAdapter.OnItemClickedListener<Menu> {
            override fun onItemClicked(item: Menu) {
                navigateToDetail(item)
            }
        }, if (isUsingGridMode) MenuAdapter.MODE_GRID else MenuAdapter.MODE_LIST)
        binding.layoutMenuHome.rvMenu.adapter = menuAdapter

        val columnCount = if (isUsingGridMode) 2 else 1
        gridLayoutManager = GridLayoutManager(requireContext(), columnCount)
        binding.layoutMenuHome.rvMenu.layoutManager = gridLayoutManager
    }

    private fun bindCategory(data: List<Category>) {
        categoryAdapter.submitData(data)
    }

    private fun bindMenu(data: List<Menu>) {
        menuAdapter.submitData(data)
    }

    private fun setClickAction() {
        binding.btnChangeListMode.setOnClickListener {
            isUsingGridMode = !isUsingGridMode
            setButtonImage(isUsingGridMode)
            val columnCount = if (isUsingGridMode) 2 else 1
            gridLayoutManager?.spanCount = columnCount
            bindMenu(viewModel.getMenus())
        }
    }

    private fun setButtonImage(usingGridMode: Boolean) {
        val iconResId = if (usingGridMode) R.drawable.ic_grid else R.drawable.ic_list
        binding.btnChangeListMode.setCompoundDrawablesWithIntrinsicBounds(iconResId, 0, 0, 0)
    }

    private fun navigateToDetail(item: Menu) {
        val intent = Intent(requireContext(), DetailFoodActivity::class.java).apply {
            putExtra(DetailFoodActivity.EXTRA_MENU, item)
        }
        startActivity(intent)
    }
}


