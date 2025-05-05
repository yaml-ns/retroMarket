package com.example.retromarket.data.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retromarket.data.api.RetrofitClient
import com.example.retromarket.service.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewModel(private val context: Context) : ViewModel() {

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> = _cartItems

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val api = RetrofitClient.instance
    private val token = "Bearer ${SessionManager.fetchAuthToken(context)}"

    fun loadCart() {
        api.getCart(token).enqueue(object : Callback<CartResponse> {
            override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
                if (response.isSuccessful) {
                    _cartItems.value = response.body()?.cart ?: emptyList()
                } else {
                    _message.value = "Erreur chargement panier"
                }
            }

            override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                _message.value = "Échec de connexion"
            }
        })
    }

    fun clearCart() {
        api.clearCart(token).enqueue(object : Callback<CartResponse> {
            override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
                _message.value = response.body()?.message ?: "Panier vidé"
                loadCart()
            }

            override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                _message.value = "Erreur réseau"
            }
        })
    }

    fun checkout() {
        api.checkoutCart(token).enqueue(object : Callback<CartResponse> {
            override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
                _message.value = response.body()?.message ?: "Commande validée"
                loadCart()
            }

            override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                _message.value = "Erreur lors du paiement"
            }
        })
    }
}
