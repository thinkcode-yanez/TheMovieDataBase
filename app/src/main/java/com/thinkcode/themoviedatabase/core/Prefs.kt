package com.thinkcode.themoviedatabase.core

import android.content.Context

class Prefs(val context:Context) {

    val SHARED_NAME="MySessionId"
    val SHARED_SESSION_ID="sessionId"
    val storage= context.getSharedPreferences(SHARED_NAME,0)

    fun saveSessionId(sessionid:String){
        storage.edit().putString(SHARED_SESSION_ID,sessionid).apply()
    }
    fun getSessionId():String{
        return storage.getString(SHARED_SESSION_ID,"")!!
    }
}