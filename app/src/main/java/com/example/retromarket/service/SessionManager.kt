package com.example.retromarket.service
import android.content.Context
import android.content.SharedPreferences
import com.example.retromarket.data.model.User
import com.google.gson.Gson

object SessionManager {
    private const val PREF_NAME = "user_session"
    private const val KEY_TOKEN = "auth_token"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER = "user"

    fun saveAuthToken(context: Context, token: String?) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }


    fun fetchAuthToken(context: Context): String? {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(KEY_TOKEN, null)
    }

    fun saveUserId(context: Context, userId: Int) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit().putInt(KEY_USER_ID, userId).apply()
    }

    fun fetchUserId(context: Context): Int {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_USER_ID, -1)
    }

    fun saveUser(context: Context, user: User) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(user)
        editor.putString(KEY_USER, json)
        editor.apply()
    }

    fun fetchUser(context: Context): User? {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString(KEY_USER, null)
        return gson.fromJson(json, User::class.java)
    }
}