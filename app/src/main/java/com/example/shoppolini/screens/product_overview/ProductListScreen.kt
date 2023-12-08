    package com.example.shoppolini.screens.product_overview

    import androidx.compose.foundation.background
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.lazy.items
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Refresh
    import androidx.compose.material.icons.filled.ShoppingCart
    import androidx.compose.material3.Divider
    import androidx.compose.material3.Icon
    import androidx.compose.material3.IconButton
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.collectAsState
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.shadow
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.layout.ContentScale
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.text.style.TextOverflow
    import androidx.compose.ui.unit.dp
    import com.example.shoppolini.data.Product
    import androidx.lifecycle.viewmodel.compose.viewModel
    import androidx.navigation.NavController
    import coil.compose.AsyncImage

    @Composable
    fun ProductListScreen(
        viewModel: ProductListViewModel = viewModel(),
        navController: NavController,
        onProductClick: (productId: Int) -> Unit = {}
    ) {
        val products = viewModel.products.collectAsState()
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Products",
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(
                    onClick = { viewModel.loadProducts() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh Products"
                    )
                }
            }

            Divider()

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(products.value) { product ->
                    ProductItem(
                        product = product,
                        onClick = { onProductClick(product.id) }
                    )
                }
            }
        }
    }

    @Composable
    fun ProductItem(
        product: Product,
        onBuyClick: () -> Unit = {},
        onClick: () -> Unit,
        viewModel: ProductListViewModel = viewModel()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(10))
                .background(color = Color.White)
                .clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(108.dp, 108.dp)
                    .background(color = Color.Gray),
                model = product.image,
                contentScale = ContentScale.Crop,
                contentDescription = "Image of ${product.title}"
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = product.title,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Price: ${product.price}",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = product.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Column(
                    modifier = Modifier.align(Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = {
                        viewModel.onBuyProduct(product.id, 1, product.title)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Buy"
                    )
                }
            }
        }
    }

    /**
     * Different states of the screen

    @Composable
    fun LoadingState() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Loading...", style = MaterialTheme.typography.titleLarge)
        }
    }

    @Composable
    fun ErrorState(message: String) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = message, color = Color.Red, style = MaterialTheme.typography.titleLarge)
        }
    }

    **/