package com.smartchoicehub.soundwavex.ui.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smartchoicehub.soundwavex.data.model.Bucket

@Composable
fun BucketSelector(
    buckets: List<Bucket>,
    onBucketSelected: (Long) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(buckets) { bucket ->
            Card(
                modifier = Modifier
                    .clickable { onBucketSelected(bucket.id) }
                    .padding(4.dp)
            ) {
                Text(
                    text = bucket.name,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
