package com.example.unit3_pathway2_grid.data

import com.example.unit3_pathway2_grid.R
import com.example.unit3_pathway2_grid.model.Topic

class DataSource {
    fun loadTopics(): List<Topic> {
        return listOf<Topic>(
            Topic(R.string.architecture, 58, R.drawable.architecture),
            Topic(R.string.automotive, 30, R.drawable.automotive),
            Topic(R.string.biology, 90, R.drawable.biology),
            Topic(R.string.crafts, 121, R.drawable.crafts),
            Topic(R.string.business, 78, R.drawable.business),
            Topic(R.string.culinary, 118, R.drawable.culinary),
            Topic(R.string.design, 423, R.drawable.design),
            Topic(R.string.ecology, 28, R.drawable.ecology),
            Topic(R.string.engineering, 67, R.drawable.engineering),
            Topic(R.string.fashion, 92, R.drawable.fashion),
            Topic(R.string.finance, 100, R.drawable.business),
            Topic(R.string.film, 165, R.drawable.photography),
            Topic(R.string.gaming, 37, R.drawable.crafts),
            Topic(R.string.geology, 290, R.drawable.ecology),
            Topic(R.string.drawing, 326, R.drawable.design),
            Topic(R.string.history, 189, R.drawable.architecture),
            Topic(R.string.journalism, 96, R.drawable.photography),
            Topic(R.string.law, 58, R.drawable.business),
            Topic(R.string.lifestyle, 305, R.drawable.fashion),
            Topic(R.string.music, 212, R.drawable.crafts),
            Topic(R.string.painting, 172, R.drawable.design),
            Topic(R.string.photography, 321, R.drawable.photography)
        )
    }
}