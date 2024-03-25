package com.rudra.moengagenews.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.rudra.moengagenews.R
import com.rudra.moengagenews.ui.theme.HeadText

@Composable
fun FilterDialog(
    openDialog: MutableState<Boolean>,
    sortByOldToNew: MutableState<Boolean>,
    onCloseDialog: () -> Unit
) {
    if (openDialog.value) {
        Dialog(
            onDismissRequest = onCloseDialog,
            properties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = true
            )
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                sortByOldToNew.value = true
                                openDialog.value = false
                            },
                        text = stringResource(id = R.string.old_to_new),
                        color = HeadText,
                        fontSize = 21.sp,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                sortByOldToNew.value = false
                                openDialog.value = false
                            },
                        text = stringResource(id = R.string.new_to_old),
                        color = HeadText,
                        fontSize = 21.sp,
                    )
                }
            }
        }
    }
}
