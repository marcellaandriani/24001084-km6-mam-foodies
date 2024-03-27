package com.example.foodiesapp.data.datasource.menu

import com.example.foodiesapp.data.model.Menu

class DummyMenuDataSource : MenuDataSource{
    override fun getMenus(): List<Menu> {
        return mutableListOf(
            Menu(
                name = "Coffee",
                imgUrl = "https://github.com/marcellaandriani/foodies-assets/blob/main/menu_img/img_coffee.jpg?raw=true",
                price = 15000.00,
                unitPrice = 15000,
                description = "Indulge in the rich aroma and robust flavor of our freshly brewed coffee. Sourced from the finest coffee beans around the globe and expertly prepared by our baristas, our coffee is the perfect pick-me-up any time of the day. Whether you prefer it black, creamy, or with a hint of flavor, we've got your caffeine fix covered.",
                address = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                mapsUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
            ),
            Menu(
                name = "Dumplings",
                imgUrl = "https://github.com/marcellaandriani/foodies-assets/blob/main/menu_img/img_dumplings.jpg?raw=true",
                price = 25000.00,
                unitPrice = 25000,
                description = "Treat yourself to a taste of authentic Chinese cuisine with our delectable dumplings. Handcrafted by our skilled chefs, each dumpling is a perfect blend of savory filling and delicate dough, served with a dipping sauce that adds an extra kick of flavor. Whether you're a dumpling connoisseur or trying them for the first time, our dumplings are sure to leave you craving for more.",
                address = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                mapsUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
            ),
            Menu(
                name = "French Fries",
                imgUrl = "https://github.com/marcellaandriani/foodies-assets/blob/main/menu_img/img_french_fries.jpg?raw=true",
                price = 10000.00,
                unitPrice = 10000,
                description = "Satisfy your cravings for something crispy and delicious with our golden-brown French fries. Hand-cut from premium potatoes and fried to perfection, our fries are crispy on the outside and fluffy on the inside, making them the ultimate comfort food. Pair them with your favorite dipping sauce or enjoy them on their own for a satisfying snack that never disappoints.",
                address = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                mapsUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
            ),
            Menu(
                name = "Fried Chicken",
                imgUrl = "https://github.com/marcellaandriani/foodies-assets/blob/main/menu_img/img_fried_chicken.jpg?raw=true",
                price = 30000.00,
                unitPrice = 30000,
                description = "Indulge in the mouthwatering goodness of our signature fried chicken. Marinated in a blend of herbs and spices, our chicken is fried to perfection to achieve that crispy outer layer while retaining its juicy tenderness inside. Whether you're enjoying it as a meal or a snack, our fried chicken is guaranteed to satisfy your cravings.",
                address = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                mapsUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
            ),
            Menu(
                name = "Pizza",
                imgUrl = "https://github.com/marcellaandriani/foodies-assets/blob/main/menu_img/img_pizza.jpg?raw=true",
                price = 95000.00,
                unitPrice = 95000,
                description = "Treat your taste buds to a slice of heaven with our mouthwatering pizzas. Crafted with a thin, crispy crust and topped with the finest ingredients, our pizzas are a delight for pizza lovers everywhere. Whether you're a fan of classic flavors like Margherita or prefer something more adventurous like BBQ chicken, our pizzas are sure to satisfy your cravings.",
                address = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                mapsUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
            ),
            Menu(
                name = "Spaghetti",
                imgUrl = "https://github.com/marcellaandriani/foodies-assets/blob/main/menu_img/img_spagheti.jpg?raw=true",
                price = 25000.00,
                unitPrice = 25000,
                description = "Transport yourself to Italy with our classic spaghetti dish. Made with al dente pasta and smothered in a rich, flavorful tomato sauce, our spaghetti is a timeless favorite for pasta enthusiasts. Served with a sprinkle of Parmesan cheese and fresh herbs, it's a dish that brings warmth and comfort with every bite.",
                address = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                mapsUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
            ),
            Menu(
                name = "Strawberry Milk",
                imgUrl = "https://github.com/marcellaandriani/foodies-assets/blob/main/menu_img/img_strawberry_milk.jpg?raw=true",
                price = 15000.00,
                unitPrice = 15000,
                description = "Quench your thirst and indulge your sweet tooth with our refreshing strawberry milk. Made with fresh, ripe strawberries blended with creamy milk, our strawberry milk is a delightful treat for all ages. Whether you're cooling off on a hot summer day or simply craving something sweet, our strawberry milk is the perfect choice.",
                address = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                mapsUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
            ),
            Menu(
                name = "Sushi",
                imgUrl = "https://github.com/marcellaandriani/foodies-assets/blob/main/menu_img/img_sushi.jpg?raw=true",
                price = 10000.00,
                unitPrice = 10000,
                description = "Embark on a culinary journey to Japan with our exquisite selection of fresh sushi rolls. Handcrafted by our skilled sushi chefs using the finest ingredients, each roll is a work of art that delights the senses. From classic favorites like California rolls to innovative creations, our sushi is sure to impress even the most discerning sushi aficionados.",
                address = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                mapsUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
            ),

        )
    }
}