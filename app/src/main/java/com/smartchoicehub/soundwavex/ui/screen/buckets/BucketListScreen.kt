package com.smartchoicehub.soundwavex.ui.screen.buckets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smartchoicehub.soundwavex.data.model.Bucket

@Composable
fun BucketListScreen(
    viewModel: BucketViewModel,
    onBucketClick: (Bucket) -> Unit
) {
    val buckets by viewModel.buckets.collectAsState()
    LazyColumn {
        items(buckets) { bucket ->
            ListItem(
                headlineContent = { Text(bucket.name) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onBucketClick(bucket) }
                    .padding(8.dp)
            )
            Divider()
        }
    }
}
