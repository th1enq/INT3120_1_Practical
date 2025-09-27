package com.example.unit3_pathway2_grid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unit3_pathway2_grid.data.DataSource
import com.example.unit3_pathway2_grid.model.Topic
import com.example.unit3_pathway2_grid.ui.theme.Unit3_Pathway2_GridTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Unit3_Pathway2_GridTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TopicsApp()
                }
            }
        }
    }
}

@Composable
fun TopicsApp() {
    TopicGrid(
        topicList = DataSource().loadTopics(),
        modifier = Modifier.padding(
            start = 8.dp,
            top = 8.dp,
            end = 8.dp,
        )
    )
}

@Composable
fun TopicGrid(topicList: List<Topic>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.padding(bottom = 16.dp)
    ) {
        items(topicList) { topic ->
            TopicCard(topic = topic)
        }
    }
}

@Composable
fun TopicCard(topic: Topic, modifier: Modifier = Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.height(120.dp) 
    ) {
        Row {
            Box {
                Image(
                    painter = painterResource(id = topic.imageResourceId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 100.dp, height = 100.dp) 
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
            }
            
            Column(
                modifier = Modifier
                    .padding(
                        start = 20.dp,
                        top = 20.dp,
                        end = 20.dp,
                        bottom = 12.dp
                    )
                    .fillMaxWidth()
            ) {
                Text(
                    text = LocalContext.current.getString(topic.stringResourceId),
                    style = MaterialTheme.typography.titleMedium, 
                    modifier = Modifier.padding(bottom = 12.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_grain),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(16.dp) 
                    )
                    Text(
                        text = topic.availableCourses.toString(),
                        style = MaterialTheme.typography.bodyMedium 
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TopicPreview() {
    Unit3_Pathway2_GridTheme {
        val topic = Topic(R.string.architecture, 58, R.drawable.architecture)
        TopicCard(topic = topic)
    }
}