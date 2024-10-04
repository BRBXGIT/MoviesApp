package com.example.feature.common.top_bar

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TopBarDropDownMenu(
    onDismissRequest: () -> Unit,
    expanded: Boolean,
    onAddFavoriteClick: () -> Unit,
    onAddToListClick: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismissRequest() }
    ) {
        DropdownMenuItem(
            text = {
                Text(text = "Add to favorite")
            },
            onClick = { onAddFavoriteClick() }
        )

        DropdownMenuItem(
            text = {
                Text(text = "Add to list")
            },
            onClick = { onAddToListClick() }
        )
    }
}