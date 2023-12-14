package com.example.shoppolini.screens.order_history

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppolini.data.Order
import com.example.shoppolini.data.OrderLineItem
import kotlinx.coroutines.flow.Flow

@Composable
fun OrderHistoryScreen(
    viewModel: OrderHistoryViewModel = viewModel()
) {
    val orders by viewModel.orders.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        // Row for the title and the refresh button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Order History",
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(onClick = { viewModel.loadOrderHistory() }) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Space after the title row

        Divider()

        LazyColumn {
            items(orders) { order ->
                OrderItem(order = order, lineItemsFlow = viewModel.getLineItemsForOrder(order.id))
            }
        }
    }
}


@Composable
fun OrderItem(
    order: Order,
    lineItemsFlow: Flow<List<OrderLineItem>>
) {

    val lineItems by lineItemsFlow.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(10))
            .background(color = Color.LightGray),
    ) {
        Text(
            text = "Order ID: ${order.id}",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = "Total Price: ${order.totalPrice}",
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        lineItems.forEach { lineItem ->
            Divider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${lineItem.productTitle} x${lineItem.quantity}",
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Price: ${lineItem.price}",
                    color = Color.Black
                )
            }
        }
    }
}
