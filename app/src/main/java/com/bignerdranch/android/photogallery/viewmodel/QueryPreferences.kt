package com.bignerdranch.android.photogallery.viewmodel

import android.content.Context

private const val PREF_SEARCH_QUERY = "searchQuery"
private const val PREF_LAST_RESULT_ID = "lastResultId"


object QueryPreferences {

    fun getStoredQuery(context: Context): String {
        val prefs =    context.getSharedPreferences("MYSharedPrefnce",Context.MODE_PRIVATE)
        return prefs.getString(PREF_SEARCH_QUERY, "")!!
    }

    fun setStoredQuery(context: Context, query: String) {
        context.getSharedPreferences("MYSharedPrefnce",Context.MODE_PRIVATE)
            .edit()
            .putString(PREF_SEARCH_QUERY, query)
            .apply()
    }

    fun getLastResultId(context: Context): String {
        return  context.getSharedPreferences("MYSharedPrefnce",Context.MODE_PRIVATE)
                    .getString(PREF_LAST_RESULT_ID, "")!!
    }

    fun setLastResultId(context: Context, lastResultId: String) {
        context.getSharedPreferences("MYSharedPrefnce",Context.MODE_PRIVATE).edit().apply() {
            putString(PREF_LAST_RESULT_ID, lastResultId)
        }
    }


}