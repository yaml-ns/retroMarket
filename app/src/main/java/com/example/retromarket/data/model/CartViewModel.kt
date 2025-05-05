package com.example.retromarket.data.model

import android.content.Context
import android.util.Log
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
        val token = SessionManager.fetchAuthToken(context)
        if (token != null) {
            api.getCart("Bearer $token").enqueue(object : Callback<List<CartItem>> {
                override fun onResponse(
                    call: Call<List<CartItem>>,
                    response: Response<List<CartItem>>
                ) {
                    if (response.isSuccessful) {
                        _cartItems.value = response.body()
                    } else {
                        _message.value = "Erreur lors du chargement du panier"
                    }
                }

                override fun onFailure(call: Call<List<CartItem>>, t: Throwable) {
                    _message.value = "Erreur réseau : ${t.localizedMessage}"
                }
            })
        }
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
