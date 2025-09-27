package com.example.unit3_pathway2_scrollablelist.data

import com.example.unit3_pathway2_scrollablelist.R
import com.example.unit3_pathway2_scrollablelist.model.Affirmation

class Datasource {
    fun loadAffirmations(): List<Affirmation> {
        return listOf<Affirmation>(
            Affirmation(R.string.affirmation1, R.drawable.nami),
            Affirmation(R.string.affirmation2, R.drawable.robin),
            Affirmation(R.string.affirmation3, R.drawable.koala),
            Affirmation(R.string.affirmation4, R.drawable.carrot),
            Affirmation(R.string.affirmation5, R.drawable.baby5),
            Affirmation(R.string.affirmation6, R.drawable.nami),
            Affirmation(R.string.affirmation7, R.drawable.robin),
            Affirmation(R.string.affirmation8, R.drawable.koala),
            Affirmation(R.string.affirmation9, R.drawable.carrot),
            Affirmation(R.string.affirmation10, R.drawable.baby5)
        )
    }
}