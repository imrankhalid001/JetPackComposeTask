package com.imidroid.jetpackcomposetask.presentation.screen.product.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.imidroid.jetpackcomposetask.domain.model.Product

@Composable
fun ProductCard(
    product: Product,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onPatch: () -> Unit,

) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

            AsyncImage(
                model = product.image,
                contentDescription = "image of product",
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Fit)

            Column(modifier = Modifier.weight(1f)) {
                Text(text = product.title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis)

                Text(text = product.price.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary)



//
//                Text(text = product.description,
//                    style = MaterialTheme.typography.titleLarge,
//                    color = MaterialTheme.colorScheme.secondary)
                Text(text = product.category,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, "Edit (PUT)", tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = onPatch) {
                    Icon(Icons.Default.Add, "Patch", tint = MaterialTheme.colorScheme.secondary)
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, "Delete", tint = MaterialTheme.colorScheme.error)
                }
            }



        }
    }


}

