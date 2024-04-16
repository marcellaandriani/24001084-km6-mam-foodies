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
import com.example.foodiesapp.data.datasource.category.CategoryApiDataSource
import com.example.foodiesapp.data.datasource.category.CategoryDataSource
import com.example.foodiesapp.data.datasource.menu.MenuApiDataSource
import com.example.foodiesapp.data.datasource.menu.MenuDataSource
import com.example.foodiesapp.data.model.Category
import com.example.foodiesapp.data.model.Menu
import com.example.foodiesapp.data.repository.CategoryRepositoryImpl
import com.example.foodiesapp.data.repository.MenuRepositoryImpl
import com.example.foodiesapp.data.source.network.service.FoodiesApiService
import com.example.foodiesapp.databinding.FragmentHomeBinding
import com.example.foodiesapp.presentation.detailfood.DetailFoodActivity
import com.example.foodiesapp.presentation.home.adapter.CategoryAdapter
import com.example.foodiesapp.presentation.home.adapter.MenuAdapter
import com.example.foodiesapp.utils.GenericViewModelFactory
import com.example.foodiesapp.utils.proceedWhen

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var menuAdapter: MenuAdapter
    private var isUsingGridMode: Boolean = true
    private var gridLayoutManager: GridLayoutManager? = null

    private val viewModel: HomeViewModel by viewModels {
        val service = FoodiesApiService.invoke()
        val menuDataSource: MenuDataSource = MenuApiDataSource(service)
        val menuRepository = MenuRepositoryImpl(menuDataSource)
        val categoryDataSource: CategoryDataSource = CategoryApiDataSource(service)
        val categoryRepository = CategoryRepositoryImpl(categoryDataSource)
        GenericViewModelFactory.create(HomeViewModel(categoryRepository, menuRepository))
    }

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter {
            getMenuData(it.name)
        }
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
        getCategoryData()
        getMenuData(null)
        setButtonImage(isUsingGridMode)
    }

    private fun getCategoryData() {
        viewModel.getCategories().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data -> bindCategory(data) }
                }
            )
        }
    }

    private fun getMenuData(category: String? = null) {
        viewModel.getMenus(category).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data -> bindMenu(data) }
                }
            )
        }
    }

    private fun setupRecyclerViews() {
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
