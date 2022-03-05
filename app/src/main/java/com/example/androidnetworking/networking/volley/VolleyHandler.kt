package com.example.androidnetworking.networking.volley

interface VolleyHandler {
    fun onSuccess(response: String?)
    fun onError(error: String?)
}