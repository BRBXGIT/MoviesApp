package com.example.feature.profile_screen.create_list_bottom_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.data.models.create_list_models.create_list_request.CreateListRequest
import com.example.core.ui.theme.mShapes
import com.example.core.ui.theme.mTypography
import com.example.feature.profile_screen.screen.ProfileScreenVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateListBottomSheet(
    onDismissRequest: () -> Unit,
) {
    val viewModel = hiltViewModel<ProfileScreenVM>()

    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
        tonalElevation = 0.dp,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        shape = mShapes.small
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Create list",
                style = mTypography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            var listName by rememberSaveable { mutableStateOf("") }
            OutlinedTextField(
                value = listName,
                onValueChange = { listName = it },
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Name") }
            )

            var listDescription by rememberSaveable { mutableStateOf("") }
            OutlinedTextField(
                value = listDescription,
                onValueChange = { listDescription = it },
                maxLines = 4,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Description") }
            )

            var isPrivate by rememberSaveable { mutableStateOf(false) }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Private: ",
                    )

                    Switch(
                        checked = isPrivate,
                        onCheckedChange = { isPrivate = it }
                    )
                }

                Button(
                    onClick = {
                        viewModel.createList(
                            CreateListRequest(
                                name = listName,
                                description = listDescription,
                                public = isPrivate
                            )
                        )

                        onDismissRequest()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = mShapes.small
                ) {
                    Text(text = "Create list")
                }
            }

            Spacer(modifier = Modifier.height(0.dp))
        }
    }
}