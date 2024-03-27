package com.example.foodiesapp.data.datasource.category

import com.example.foodiesapp.data.model.Category

class DummyCategoryDataSource : CategoryDataSource {
    override fun getCategories(): List<Category> {
        return listOf(
            Category(name = "Drink", imgUrl = "https://github.com/marcellaandriani/foodies-assets/blob/main/category_img/ic_drink.png?raw=true" ),
            Category(name = "Snack", imgUrl =  "https://github.com/marcellaandriani/foodies-assets/blob/main/category_img/ic_french_fries.png?raw=true"),
            Category(name = "Chicken", imgUrl =  "https://github.com/marcellaandriani/foodies-assets/blob/main/category_img/ic_fried_chicken.png?raw=true"),
            Category(name = "Pizza", imgUrl =  "https://github.com/marcellaandriani/foodies-assets/blob/main/category_img/ic_pizza.png?raw=true"),
            Category(name = "Noodle", imgUrl =  "https://github.com/marcellaandriani/foodies-assets/blob/main/category_img/ic_spaghetti.png?raw=true"),
            Category(name = "Sushi", imgUrl =  "https://github.com/marcellaandriani/foodies-assets/blob/main/category_img/ic_sushi.png?raw=true"),
        )
    }

}